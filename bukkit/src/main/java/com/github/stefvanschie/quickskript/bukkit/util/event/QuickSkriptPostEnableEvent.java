package com.github.stefvanschie.quickskript.bukkit.util.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * This event is called when QuickSkript has been fully enabled, including all the scripts.
 *
 * @since 0.1.0
 */
public class QuickSkriptPostEnableEvent extends Event {

    /**
     * A HandlerList to please Bukkit
     */
    private static final HandlerList HANDLER_LIST = new HandlerList();

    /**
     *
     *
     * @since 0.1.0
     */
    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    /**
     * A static method to please Bukkit
     *
     * @return {@link #HANDLER_LIST}
     */
    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
