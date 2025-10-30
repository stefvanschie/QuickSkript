package com.github.stefvanschie.quickskript.spigot.util.event.script;

import com.github.stefvanschie.quickskript.core.skript.Skript;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Fired whenever a script has completed unloading.
 *
 * @since 0.1.0
 */
public class ScriptUnloadEvent extends ScriptEvent {

    /**
     * Creates a new event for a script having finished unloading.
     *
     * @param script the script that has finished unloading
     * @since 0.1.0
     */
    public ScriptUnloadEvent(@NotNull Skript script) {
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
