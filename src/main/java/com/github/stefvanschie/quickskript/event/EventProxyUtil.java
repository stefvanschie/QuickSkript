package com.github.stefvanschie.quickskript.event;

import com.github.stefvanschie.quickskript.skript.SkriptEvent;
import org.apache.commons.lang3.Validate;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

/**
 * A utility class for registering {@link SkriptEvent}s.
 *
 * @since 0.1.0
 */
@SuppressWarnings("HardcodedFileSeparator")
public class EventProxyUtil {

    /**
     * A set containing all registered abstract event factories.
     */
    private static final Set<EventProxyFactory> FACTORIES = new HashSet<>();

    /**
     * Parses the inputted text and returns the abstract event parsed from it, or null if none was found.
     *
     * @param input the input to parse
     * @param toRegisterSupplier a {@link Supplier} which can create one {@link SkriptEvent} instance to register
     * @return whether a registration took place
     * @since 0.1.0
     */
    public static boolean tryRegisterText(@NotNull String input, Supplier<SkriptEvent> toRegisterSupplier) {
        input = input.trim();

        for (EventProxyFactory factory : FACTORIES) {
            if (factory.tryRegister(input, toRegisterSupplier))
                return true;
        }

        return false;
    }

    /**
     * Registers a factory.
     *
     * @param factory the factory to register
     * @since 0.1.0
     */
    private static void registerFactory(@NotNull EventProxyFactory factory) {
        Validate.isTrue(FACTORIES.add(factory), "The specified EventProxyFactory has already been registered.");
    }

    static {
        registerFactory(new SimpleEventProxyFactory()
                .registerEvent(EntityExplodeEvent.class, "on explo(?:(?:d(?:e|ing))|(?:sion))")
                .registerEvent(PlayerCommandPreprocessEvent.class, "on command")
        );

        registerFactory(new ComplexEventProxyFactory()
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
