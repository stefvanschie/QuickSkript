package com.github.stefvanschie.quickskript.core.util.literal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents different input keys.
 *
 * @since 0.1.0
 */
public enum InputKey {

    /**
     * The input key to move backward.
     *
     * @since 0.1.0
     */
    BACKWARD("backward movement key", "backward key"),

    /**
     * The input key to move forward.
     *
     * @since 0.1.0
     */
    FORWARD("forward movement key", "forward key"),

    /**
     * The input key to jump.
     *
     * @since 0.1.0
     */
    JUMP("jumping key", "jump key"),

    /**
     * The input key to move left.
     *
     * @since 0.1.0
     */
    LEFT("left movement key", "left key"),

    /**
     * The input key to move right.
     *
     * @since 0.1.0
     */
    RIGHT("right movement key", "right key"),

    /**
     * The input key to sneak.
     *
     * @since 0.1.0
     */
    SNEAK("sneaking key", "sneak key"),

    /**
     * The input key to sprint.
     *
     * @since 0.1.0
     */
    SPRINT("sprinting key", "sprint key");

    /**
     * The aliases for the provided input key.
     *
     * @since 0.1.0
     */
    @NotNull
    private final String @NotNull [] aliases;

    /**
     * All input keys by name
     */
    @NotNull
    private static final Map<@NotNull String, @NotNull InputKey> ENTRIES = new HashMap<>();

    /**
     * Creates a spawn reason with the provided aliases
     *
     * @param aliases the aliases
     * @since 0.1.0
     */
    InputKey(@NotNull String @NotNull ... aliases) {
        this.aliases = aliases;
    }

    /**
     * Returns a modifiable copy of the aliases of this input key.
     *
     * @return the aliases
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    private String @NotNull [] getAliases() {
        return Arrays.copyOf(aliases, aliases.length);
    }

    /**
     * Gets the input key by the given name or null if no such input key exists.
     *
     * @param name the name of the input key
     * @return the input key or null
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public static InputKey byName(@NotNull String name) {
        return ENTRIES.get(name);
    }

    static {
        for (InputKey inputKey : InputKey.values()) {
            for (String alias : inputKey.getAliases()) {
                ENTRIES.put(alias, inputKey);
            }
        }
    }
}
