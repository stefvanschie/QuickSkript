package com.github.stefvanschie.quickskript.core.context;

import com.github.stefvanschie.quickskript.core.skript.Skript;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A context interface. Contexts are used to tell the executors how this code is being executed (through a command,
 * an event, etc.), so the code can react and adopt to that.
 *
 * @since 0.1.0
 */
public interface Context {

    /**
     * Gets the skript that is executing this
     *
     * @return the skript
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    Skript getSkript();
}
