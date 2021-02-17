package com.github.stefvanschie.quickskript.core.util.literal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * A resource pack status to indicate a player's status regarding the installation of a resource pack.
 *
 * @since 0.1.0
 */
public enum ResourcePackStatus {

    /**
     * The player has accepted the resource pack
     *
     * @since 0.1.0
     */
    ACCEPT,

    /**
     * The player declined the resource pack
     *
     * @since 0.1.0
     */
    DECLINE,

    /**
     * The player failed to download the resource pack
     *
     * @since 0.1.0
     */
    DOWNLOAD_FAIL,

    /**
     * The player successfully loaded the resource pack
     *
     * @since 0.1.0
     */
    SUCCESSFULLY_LOAD;

    /**
     * All resource pack statuses by name
     */
    @NotNull
    private static final Map<String, ResourcePackStatus> ENTRIES = new HashMap<>();

    /**
     * Gets the resource pack status by name or null if no such resource pack status exists.
     *
     * @param name the name of the resource pack status
     * @return the resource pack status or null
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public static ResourcePackStatus byName(@NotNull String name) {
        return ENTRIES.get(name);
    }

    static {
        for (ResourcePackStatus resourcePackStatus : ResourcePackStatus.values()) {
            String name = resourcePackStatus.name().replace('_', ' ').toLowerCase();

            ENTRIES.put(name, resourcePackStatus);
        }
    }
}
