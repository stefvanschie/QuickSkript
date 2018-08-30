package com.github.stefvanschie.quickskript.event;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An abstract factory interface for parsing abstract events. Every event needs at least one factory, but may have more.
 *
 * @param <T> the type of abstract event this will return
 * @since 0.1.0
 */
public interface AbstractEventFactory<T extends AbstractEvent> {

    /**
     * Tries to parse the given text for this specified factory.
     *
     * @param text the text to be parsed
     * @return the event created, or null if the event could not be created
     * @since 0.1.0
     */
    @Nullable
    T tryParse(@NotNull String text);
}
