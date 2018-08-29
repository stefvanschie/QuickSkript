package com.github.stefvanschie.quickskript.context;

import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

/**
 * An event context to indicate the code is being ran from an event.
 *
 * @since 0.1.0
 */
public class EventContext implements Context {

    /**
     * The event that was being executed
     */
    @NotNull
    private Event event;

    /**
     * Constructs a new event context
     *
     * @param event the event that was executed
     * @since 0.1.0
     */
    public EventContext(@NotNull Event event) {
        this.event = event;
    }

    /**
     * Gets the {@link #event}
     *
     * @return the event
     * @since 0.1.0
     */
    @NotNull
    public Event getEvent() {
        return event;
    }
}