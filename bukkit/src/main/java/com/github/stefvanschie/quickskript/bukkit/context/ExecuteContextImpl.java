package com.github.stefvanschie.quickskript.bukkit.context;

import com.github.stefvanschie.quickskript.core.context.ExecuteContext;
import com.github.stefvanschie.quickskript.core.skript.SingleLineSkript;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExecuteContextImpl implements ContextImpl, ExecuteContext {

    @NotNull
    private final SingleLineSkript skript;

    @Nullable
    private final CommandSender executor;

    public ExecuteContextImpl(@NotNull SingleLineSkript skript, @Nullable CommandSender executor) {
        this.skript = skript;
        this.executor = executor;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Contract(pure = true)
    @Override
    public SingleLineSkript getSkript() {
        return skript;
    }

    @Nullable
    @Contract(pure = true)
    @Override
    public CommandSender getCommandSender() {
        return executor;
    }
}
