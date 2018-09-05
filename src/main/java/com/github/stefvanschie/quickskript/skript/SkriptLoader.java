package com.github.stefvanschie.quickskript.skript;

import com.github.stefvanschie.quickskript.QuickSkript;
import com.github.stefvanschie.quickskript.event.ComplexEventProxyFactory;
import com.github.stefvanschie.quickskript.event.EventProxyFactory;
import com.github.stefvanschie.quickskript.event.SimpleEventProxyFactory;
import com.github.stefvanschie.quickskript.psi.PsiConverter;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.psi.effect.PsiCancelEventEffect;
import com.github.stefvanschie.quickskript.psi.effect.PsiMessageEffect;
import com.github.stefvanschie.quickskript.psi.exception.ParseException;
import com.github.stefvanschie.quickskript.psi.expression.PsiConsoleSenderExpression;
import com.github.stefvanschie.quickskript.psi.expression.PsiParseExpression;
import com.github.stefvanschie.quickskript.psi.expression.PsiRandomNumberExpression;
import com.github.stefvanschie.quickskript.psi.function.*;
import com.github.stefvanschie.quickskript.psi.literal.PsiNumberLiteral;
import com.github.stefvanschie.quickskript.psi.literal.PsiStringLiteral;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Instances of this class contain everything necessary for loading Skript files.
 * This allows addons to be easily loaded, as well as dropping all data after
 * all Skripts have been loaded (in order to save memory).
 * This means that the static modifier should only be used when the data is required
 * to also be present when the Skripts are being ran, not only when they are being loaded.
 *
 * @since 0.1.0
 */
public class SkriptLoader implements AutoCloseable {

    /**
     * The current loader instance or null if there are none present.
     */
    @Nullable
    private static SkriptLoader instance;

    /**
     * Gets the current loader instance. Returns null if there are none present.
     *
     * @return the current instance
     * @since 0.1.0
     */
    @Contract(pure = true)
    public static SkriptLoader get() {
        return instance;
    }


    /**
     * A list of all psi element factories.
     */
    @NotNull
    private final List<PsiElementFactory<?>> elements = new ArrayList<>();

    /**
     * A map indexing converters by their name.
     */
    @NotNull
    private final Map<String, PsiConverter<?>> converters = new HashMap<>();

    /**
     * A list of all event factories.
     */
    @NotNull
    private final List<EventProxyFactory> events = new ArrayList<>();

    /**
     * The constructor of {@link PluginCommand} wrapped into a {@link Function}.
     */
    @NotNull
    private final Function<String, PluginCommand> commandConstructor;

    /**
     * The internally used instance of the {@link CommandMap}.
     */
    @NotNull
    private final CommandMap commandMap;

    /**
     * Create a new instance, initializing it with all default (non-addon) data.
     *
     * @since 0.1.0
     */
    public SkriptLoader() {
        Validate.isTrue(instance == null, "A SkriptLoader is already present, can't create another one.");

        registerDefaultElements();
        registerDefaultConverters();
        registerDefaultEvents();

        try {
            Constructor<PluginCommand> rawConstructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            rawConstructor.setAccessible(true);

            commandConstructor = name -> {
                try {
                    return rawConstructor.newInstance(name, QuickSkript.getInstance());
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    throw new AssertionError("Error while constructing a PluginCommand instance:", e);
                }
            };

            Field commandMapField = SimplePluginManager.class.getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            commandMap = (CommandMap) commandMapField.get(Bukkit.getPluginManager());

        } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException e) {
            throw new AssertionError("Error while getting the CommandMap:", e);
        }

        instance = this;
    }


    /**
     * Parses text into psi elements.
     * Returns null if no element was found.
     *
     * @param input the text to be parsed
     * @return the parsed psi element, or null if none were found
     * @since 0.1.0
     */
    @Nullable
    @Contract("null -> fail")
    public PsiElement<?> tryParseElement(@NotNull String input) {
        input = input.trim();

        for (PsiElementFactory<?> factory : elements) {
            PsiElement<?> element = factory.tryParse(input);

            if (element != null)
                return element;
        }

        return null;
    }

    /**
     * Parses text into psi elements.
     * Throws a {@link ParseException} if no element was found.
     *
     * @param input the text to be parsed
     * @return the parsed psi element
     * @since 0.1.0
     */
    @NotNull
    public PsiElement<?> forceParseElement(@NotNull String input) {
        PsiElement<?> result = tryParseElement(input);
        if (result != null)
            return result;

        throw new ParseException("Unable to find an expression named: " + input);
    }


    /**
     * Returns a converter based on the specified name.
     * Returns null if no converter was found.
     *
     * @param name the name of the converter
     * @return the converter
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public PsiConverter<?> tryGetConverter(@NotNull String name) {
        return converters.get(name);
    }

    /**
     * Returns a converter based on the specified name.
     * Throws a {@link ParseException} if no converter was found.
     *
     * @param name the name of the converter
     * @return the converter
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public PsiConverter<?> forceGetConverter(@NotNull String name) {
        PsiConverter<?> result = tryGetConverter(name);
        if (result != null)
            return result;

        throw new ParseException("Unable to find a converter named: " + name);
    }


    /**
     * Parses the inputted text and returns whether an event was registered.
     *
     * @param input the input to parse
     * @param toRegisterSupplier a {@link Supplier} which can create one {@link SkriptEvent} instance to register
     * @return whether a registration took place
     * @since 0.1.0
     */
    public boolean tryRegisterEvent(@NotNull String input, @NotNull Supplier<SkriptEvent> toRegisterSupplier) {
        input = input.trim();

        for (EventProxyFactory factory : events) {
            if (factory.tryRegister(input, toRegisterSupplier))
                return true;
        }

        return false;
    }


    /**
     * Registers the specified factory.
     *
     * @param factory the element factory to register
     * @since 0.1.0
     */
    public void registerElement(@NotNull PsiElementFactory<?> factory) {
        elements.add(factory);
    }

    /**
     * Registers the specified converter.
     *
     * @param name the name of the converter
     * @param converter the converter to register
     * @since 0.1.0
     */
    public void registerConverter(@NotNull String name, @NotNull PsiConverter<?> converter) {
        Validate.isTrue(converters.put(name, converter) == null, "A PsiConverter with the same name has already been registered.");
    }

    /**
     * Registers the specified factory.
     *
     * @param factory the event factory to register
     * @since 0.1.0
     */
    public void registerEvent(@NotNull EventProxyFactory factory) {
        events.add(factory);
    }


    /**
     * Creates a command and registers it in the internal {@link CommandMap}
     * after running the specified initializer.
     *
     * @param name the name of the command
     * @param initializer the initializer of the new command instance
     * @since 0.1.0
     */
    public void registerCommand(String name, Consumer<PluginCommand> initializer) {
        PluginCommand command = commandConstructor.apply(name);
        initializer.accept(command);
        commandMap.register("quickskript", command);
    }


    /**
     * Deletes the current loader instance.
     * Normally should only be called if you are the one who created it.
     *
     * @since 0.1.0
     */
    @Override
    public void close() {
        Validate.notNull(instance, "No SkriptLoader is present, can't close it.");
        instance = null;
    }


    private void registerDefaultElements() {
        //effects
        registerElement(new PsiCancelEventEffect.Factory());
        registerElement(new PsiMessageEffect.Factory());

        //expressions
        registerElement(new PsiConsoleSenderExpression.Factory());
        registerElement(new PsiParseExpression.Factory());
        registerElement(new PsiRandomNumberExpression.Factory());

        //functions
        registerElement(new PsiAbsoluteValueFunction.Factory());
        registerElement(new PsiAtan2Function.Factory());
        registerElement(new PsiCalculateExperienceFunction.Factory());
        registerElement(new PsiCeilFunction.Factory());
        registerElement(new PsiCosineFunction.Factory());
        registerElement(new PsiDateFunction.Factory());
        registerElement(new PsiExponentialFunction.Factory());
        registerElement(new PsiFloorFunction.Factory());
        registerElement(new PsiInverseCosineFunction.Factory());
        registerElement(new PsiInverseSineFunction.Factory());
        registerElement(new PsiInverseTangentFunction.Factory());
        registerElement(new PsiLocationFunction.Factory());
        registerElement(new PsiLogarithmFunction.Factory());
        registerElement(new PsiMaximumFunction.Factory());
        registerElement(new PsiMinimumFunction.Factory());
        registerElement(new PsiModuloFunction.Factory());
        registerElement(new PsiNaturalLogarithmFunction.Factory());
        registerElement(new PsiProductFunction.Factory());
        registerElement(new PsiRoundFunction.Factory());
        registerElement(new PsiSineFunction.Factory());
        registerElement(new PsiSquareRootFunction.Factory());
        registerElement(new PsiSumFunction.Factory());
        registerElement(new PsiTangentFunction.Factory());
        registerElement(new PsiVectorFunction.Factory());
        registerElement(new PsiWorldFunction.Factory());

        //literals
        registerElement(new PsiNumberLiteral.Factory());
        registerElement(new PsiStringLiteral.Factory());
    }

    private void registerDefaultConverters() {
        registerConverter("number", new PsiNumberLiteral.Converter());
        registerConverter("text", new PsiStringLiteral.Converter());
    }

    @SuppressWarnings("HardcodedFileSeparator")
    private void registerDefaultEvents() {
        registerEvent(new SimpleEventProxyFactory()
                .registerEvent(EntityExplodeEvent.class, "on explo(?:(?:d(?:e|ing))|(?:sion))")
                .registerEvent(PlayerCommandPreprocessEvent.class, "on command")
        );

        registerEvent(new ComplexEventProxyFactory()
                .registerEvent(PlayerCommandPreprocessEvent.class, "on command \"([\\s\\S]+)\"", matcher -> {
                    String command = matcher.group(1); //TODO the regex of this group is probably incorrect
                    String finalCommand = command.startsWith("/") ? command.substring(1) : command;

                    return event -> {
                        String message = ((PlayerCommandPreprocessEvent) event).getMessage();
                        return message.startsWith(finalCommand, message.startsWith("/") ? 1 : 0);
                    };
                })
        );
    }
}
