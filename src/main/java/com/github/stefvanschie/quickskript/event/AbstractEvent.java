package com.github.stefvanschie.quickskript.event;

import com.github.stefvanschie.quickskript.QuickSkript;
import com.github.stefvanschie.quickskript.skript.SkriptEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
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

    private static final Listener EMPTY_LISTENER = new Listener() {};

    /**
     * Registers this event and chains the execution it to the specified skript event
     *
     * @param event the event to call
     * @since 0.1.0
     */
    public void register(@NotNull SkriptEvent event) {
        this.event = event;

        Bukkit.getPluginManager().registerEvent(getEventClass(), EMPTY_LISTENER,
                EventPriority.NORMAL, this, QuickSkript.getPlugin(QuickSkript.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(Listener listener, Event event) {
        this.event.execute(event);
    }

    /**
     * Gets the class of the {@link Event} this {@link AbstractEvent} is handling.
     *
     * @return the {@link Event} class which is handled by this instance
     */
    protected abstract Class<? extends Event> getEventClass();
}
