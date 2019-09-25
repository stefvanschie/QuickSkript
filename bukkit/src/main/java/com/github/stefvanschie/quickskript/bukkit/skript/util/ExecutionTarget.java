package com.github.stefvanschie.quickskript.bukkit.skript.util;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An enum for holding possible 'executable by' values for commands.
 *
 * @since 0.1.0
 */
public enum ExecutionTarget {

    /**
     * Command can only be used by players
     *
     * @since 0.1.0
     */
    PLAYERS("players"),

    /**
     * Command can only be used by the console
     *
     * @since 0.1.0
     */
    CONSOLE("console"),


    /**
     * Command can only be used by players and the console
     *
     * @since 0.1.0
     */
    CONSOLE_AND_PLAYERS("console and players");

    /**
     * The name of this execution target as to be used in scripts.
     */
    @NotNull
    private final String name;

    /**
     * Constructs a new execution target
     *
     * @param name {@link #name}
     * @since 0.1.0
     */
    ExecutionTarget(@NotNull String name) {
        this.name = name;
    }

    /**
     * Returns whether the specified sender fits the criteria of this instance
     *
     * @param sender the sender to check
     * @return whether the sender meets the criteria
     * @since 0.1.0
     */
    @Contract(pure = true)
    public boolean matches(@NotNull CommandSender sender) {
        return (sender instanceof Player && (this == PLAYERS || this == CONSOLE_AND_PLAYERS)) ||
                (sender instanceof ConsoleCommandSender && (this == CONSOLE || this == CONSOLE_AND_PLAYERS));
    }

    /**
     * Gets the execution target from the specified input, returns null if no appropriate target was found.
     *
     * @param input the input to find the target by
     * @return the execution target found, or null if no target was found
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public static ExecutionTarget parse(@NotNull String input) {
        input = input.toLowerCase();

        for (ExecutionTarget value : ExecutionTarget.values()) {
            if (value.name.equals(input)) {
                return value;
            }
        }

        return null;
    }
}
