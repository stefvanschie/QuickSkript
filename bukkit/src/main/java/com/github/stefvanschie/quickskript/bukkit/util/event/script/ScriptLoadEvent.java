package com.github.stefvanschie.quickskript.bukkit.util.event.script;

import com.github.stefvanschie.quickskript.core.skript.Skript;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Fired whenever a script has completed loading.
 *
 * @since 0.1.0
 */
public class ScriptLoadEvent extends ScriptEvent {

    /**
     * Creates a new event for a script having finished loading.
     *
     * @param script the script that has finished loading
     * @since 0.1.0
     */
    public ScriptLoadEvent(@NotNull Skript script) {
        super(script);
    }

    /**
     * A HandlerList to please Bukkit
     */
    @NotNull
    private static final HandlerList HANDLER_LIST = new HandlerList();

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
     */
    @NotNull
    @Contract(pure = true)
    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
