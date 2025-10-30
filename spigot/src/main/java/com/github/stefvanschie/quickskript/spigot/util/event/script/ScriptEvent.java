package com.github.stefvanschie.quickskript.spigot.util.event.script;

import com.github.stefvanschie.quickskript.core.skript.Skript;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

/**
 * A base class for script related events.
 *
 * @since 0.1.0
 */
public abstract class ScriptEvent extends Event {

    /**
     * The script this event responded to.
     */
    @NotNull
    private final Skript script;

    /**
     * Creates a new script event.
     *
     * @param script the script this event is fired for
     * @since 0.1.0
     */
    public ScriptEvent(@NotNull Skript script) {
        this.script = script;
    }

    /**
     * Gets the script associated with this event.
     *
     * @return the script
     * @since 0.1.0
     */
    @NotNull
    public Skript getScript() {
        return this.script;
    }
}
