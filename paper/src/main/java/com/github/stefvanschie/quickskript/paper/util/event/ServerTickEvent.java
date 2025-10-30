package com.github.stefvanschie.quickskript.paper.util.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * An event indicating that the server has ticked.
 *
 * @since 0.1.0
 */
public class ServerTickEvent extends Event {

    /**
     * A HandlerList to please Bukkit
     */
    @NotNull
    private static final HandlerList HANDLER_LIST = new HandlerList();

    /**
     * Gets the handler list of this event.
     *
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    /**
     * A static method to please Bukkit
     *
     * @return {@link #HANDLER_LIST}
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
