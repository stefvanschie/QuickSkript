package com.github.stefvanschie.quickskript.core.util.literal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents all possible reasons as to why teleportation occured.
 *
 * @since 0.1.0
 */
public enum TeleportCause {

    /**
     * When an entity is teleported due to eating chorus fruit
     *
     * @since 0.1.0
     */
    CHORUS_FRUIT("chorus", "chorus fruit"),

    /**
     * When an entity is teleported due to a command being executed
     *
     * @since 0.1.0
     */
    COMMAND("command"),

    /**
     * When an entity is teleported due to going through an end gateway
     *
     * @since 0.1.0
     */
    END_GATEWAY("gateway", "end gateway", "ender gateway"),

    /**
     * When an entity is teleported due to going through an end portal
     *
     * @since 0.1.0
     */
    END_PORTAL("end portal", "ender portal"),

    /**
     * When an entity is teleported due to a thrown ender pearl colliding
     *
     * @since 0.1.0
     */
    ENDER_PEARL("ender pearl"),

    /**
     * When an entity is teleported due to going through a nether portal
     *
     * @since 0.1.0
     */
    NETHER_PORTAL("nether portal"),

    /**
     * When an entity is teleported by a plugin
     *
     * @since 0.1.0
     */
    PLUGIN("plugin"),

    /**
     * When an entity is being teleported because of entering an entity in spectator mode
     *
     * @since 0.1.0
     */
    SPECTATE("spectate", "spectator"),

    /**
     * When an entity is being teleproted due to an unknown reason
     *
     * @since 0.1.0
     */
    UNKNOWN("unknown");

    /**
     * The aliases for this teleport cause
     */
    @NotNull
    private final String[] aliases;

    /**
     * All teleport causes by name
     */
    @NotNull
    private static final Map<String, TeleportCause> ENTRIES = new HashMap<>();

    /**
     * Creates a teleport cause with the given aliases
     *
     * @param aliases the aliases
     * @since 0.1.0
     */
    TeleportCause(@NotNull String... aliases) {
        this.aliases = aliases;
    }

    /**
     * Gets an array of aliases associated with this teleport cause. Modifications in the returned array will not occur
     * internally.
     *
     * @return the aliases
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public String[] getAliases() {
        return Arrays.copyOf(aliases, aliases.length);
    }

    /**
     * Gets a teleport cause by the given name or null if no such teleport cause exists
     *
     * @param name the name of the teleport cause
     * @return the teleport cause or null
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public static TeleportCause byName(@NotNull String name) {
        return ENTRIES.get(name);
    }

    static {
        for (TeleportCause teleportCause : TeleportCause.values()) {
            for (String alias : teleportCause.getAliases()) {
                ENTRIES.put(alias, teleportCause);
            }
        }
    }
}
