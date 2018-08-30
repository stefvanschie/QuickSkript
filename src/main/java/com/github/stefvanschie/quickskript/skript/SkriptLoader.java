package com.github.stefvanschie.quickskript.skript;

import com.github.stefvanschie.quickskript.event.ComplexEventProxyFactory;
import com.github.stefvanschie.quickskript.event.EventProxyFactory;
import com.github.stefvanschie.quickskript.event.SimpleEventProxyFactory;
import com.github.stefvanschie.quickskript.psi.PsiConverter;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.psi.effect.PsiCancelEventEffect;
import com.github.stefvanschie.quickskript.psi.effect.PsiMessageEffect;
import com.github.stefvanschie.quickskript.psi.expression.PsiConsoleSenderExpression;
import com.github.stefvanschie.quickskript.psi.expression.PsiParseExpression;
import com.github.stefvanschie.quickskript.psi.function.*;
import com.github.stefvanschie.quickskript.psi.literal.PsiNumberLiteral;
import com.github.stefvanschie.quickskript.psi.literal.PsiStringLiteral;
import org.apache.commons.lang3.Validate;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
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
    //TODO alternative to static singleton: dependency inject into each PsiElementFactory + some other stuff

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
     * Create a new instance, initializing it with all default (non-addon) data.
     *
     * @since 0.1.0
     */
    public SkriptLoader() {
        Validate.isTrue(instance == null, "A SkriptLoader is already present, can't create another one.");

        registerDefaultElements();
        registerDefaultConverters();
        registerDefaultEvents();

        instance = this;
    }


    /**
     * Parses text into psi elements. Returns null if no element was found.
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
     * Returns a converter based on the specified name.
     *
     * @param name the name of the converter
     * @return the converter
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public PsiConverter<?> getConverter(@NotNull String name) {
        return converters.get(name);
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
     * Deletes the current loader instance.
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
                    String command = matcher.group(1);
                    String finalCommand = command.startsWith("/") ? command.substring(1) : command;

                    return event -> {
                        String message = ((PlayerCommandPreprocessEvent) event).getMessage();
                        return message.startsWith(finalCommand, message.startsWith("/") ? 1 : 0);
                    };
                })
        );
    }
}
