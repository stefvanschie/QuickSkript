package com.github.stefvanschie.quickskript.event;

import com.github.stefvanschie.quickskript.skript.SkriptEvent;
import org.bukkit.plugin.EventExecutor;
import org.jetbrains.annotations.NotNull;

/**
 * An interface for all event parsers. This will parse an event name and return the class of the parsed event.
 *
 * @since 0.1.0
 */
public abstract class AbstractEvent implements EventExecutor {

    /**
     * The skript event this event is chained to.
     */
    protected SkriptEvent event;

    /**
     * Registers this event and chains the execution it to the specified skript event
     *
     * @param event the event to call
     * @since 0.1.0
     */
    public abstract void register(@NotNull SkriptEvent event);
}
