package com.github.stefvanschie.quickskript.bukkit.context;

import com.github.stefvanschie.quickskript.core.context.ExecuteContext;
import com.github.stefvanschie.quickskript.core.skript.Skript;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This context indicates that the Skript is being ran from an execute command.
 *
 * @since 0.1.0
 */
public class ExecuteContextImpl implements ContextImpl, ExecuteContext {

    /**
     * The skript that is executing this code
     */
    @NotNull
    private final Skript skript;

    /**
     * The executor of the command
     */
    @Nullable
    private final CommandSender executor;

    /**
     * Constructs a new execute context
     *
     * @param skript the skript file this context belongs to
     * @param executor the executor of the command
     * @since 0.1.0
     */
    public ExecuteContextImpl(@NotNull Skript skript, @Nullable CommandSender executor) {
        this.skript = skript;
        this.executor = executor;
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public Skript getSkript() {
        return skript;
    }

    @Nullable
    @Contract(pure = true)
    @Override
    public CommandSender getCommandSender() {
        return executor;
    }
}
