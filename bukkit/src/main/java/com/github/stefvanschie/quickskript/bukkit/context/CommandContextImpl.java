package com.github.stefvanschie.quickskript.bukkit.context;

import com.github.stefvanschie.quickskript.core.context.CommandContext;
import com.github.stefvanschie.quickskript.core.skript.Skript;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A command context to indicate this code is being ran from a command and to provide information about said command.
 *
 * @since 0.1.0
 */
public class CommandContextImpl implements ContextImpl, CommandContext {

    /**
     * The skript that is executing this code
     */
    @NotNull
    private final Skript skript;

    /**
     * The executor of the command
     */
    @NotNull
    private final CommandSender sender;

    /**
     * Constructs a new command context.
     *
     * @param skript the skript file this context belongs to
     * @param sender the executor of the command
     * @since 0.1.0
     */
    public CommandContextImpl(@NotNull Skript skript, @NotNull CommandSender sender) {
        this.skript = skript;
        this.sender = sender;
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public CommandSender getCommandSender() {
        return sender;
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public Skript getSkript() {
        return skript;
    }
}
