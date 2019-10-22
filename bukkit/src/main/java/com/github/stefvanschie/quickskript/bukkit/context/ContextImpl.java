package com.github.stefvanschie.quickskript.bukkit.context;

import com.github.stefvanschie.quickskript.core.context.Context;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

/**
 * A context interface. Contexts are used to tell the executors how this code is being executed (through a command,
 * an event, etc.), so the code can react and adopt to that.
 *
 * @since 0.1.0
 */
public interface ContextImpl extends Context {

    /**
     * Gets a command sender this context has, or null if this context doesn't have a command sender.
     *
     * @return the command sender, or null
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    CommandSender getCommandSender();
}
