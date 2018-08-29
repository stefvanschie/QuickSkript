package com.github.stefvanschie.quickskript.event;

import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

/**
 * A utility class for parsing abstract events.
 *
 * @since 0.1.0
 */
public class AbstractEventUtil {

    /**
     * A set containing all registered abstract event factories.
     */
    private static final Set<AbstractEventFactory<? extends AbstractEvent>> FACTORIES = new HashSet<>();

    /**
     * Parses the inputted text and returns the abstract event parsed from it, or null if none was found.
     *
     * @param input the input to parse
     * @return the event to be returned
     * @since 0.1.0
     */
    @Nullable
    public static AbstractEvent parseText(@NotNull String input) {
        input = input.trim();

        for (AbstractEventFactory<? extends AbstractEvent> factory : FACTORIES) {
            AbstractEvent result = factory.parse(input);

            if (result != null)
                return result;
        }

        return null;
    }

    /**
     * Registers a factory.
     *
     * @param factory the factory to register
     * @since 0.1.0
     */
    private static void registerFactory(@NotNull AbstractEventFactory<? extends AbstractEvent> factory) {
        Validate.isTrue(FACTORIES.add(factory), "The specified AbstractEventFactory has already been registered.");
    }

    static {
        registerFactory(new AbstractEntityExplodeEvent.Factory());
    }
}
