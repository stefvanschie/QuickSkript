package com.github.stefvanschie.quickskript.bukkit.util;

import com.github.stefvanschie.quickskript.core.util.literal.ResourcePackStatus;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * A utility for resource pack statuses.
 *
 * @since 0.1.0
 */
public class ResourcePackStatusUtil {

    /**
     * A mapping between QuickSkript's resource pack status and Bukkit's resource pack status
     */
    private static final Map<ResourcePackStatus, PlayerResourcePackStatusEvent.Status> MAPPINGS = new HashMap<>();

    /**
     * Converts a QuickSkript resource pack status to a Bukkit resource pack status. This throws an
     * {@link IllegalStateException} when the provided status does not have a mapping.
     *
     * @param quickSkriptStatus the status to convert
     * @return the corresponding Bukkit status
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public static PlayerResourcePackStatusEvent.Status convert(@NotNull ResourcePackStatus quickSkriptStatus) {
        PlayerResourcePackStatusEvent.Status bukkitStatus = MAPPINGS.get(quickSkriptStatus);

        if (bukkitStatus == null) {
            throw new IllegalStateException("Resource pack status doesn't have a mapping");
        }

        return bukkitStatus;
    }

    static {
        MAPPINGS.put(ResourcePackStatus.ACCEPT, PlayerResourcePackStatusEvent.Status.ACCEPTED);
        MAPPINGS.put(ResourcePackStatus.DECLINE, PlayerResourcePackStatusEvent.Status.DECLINED);
        MAPPINGS.put(ResourcePackStatus.DOWNLOAD_FAIL, PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD);
        MAPPINGS.put(ResourcePackStatus.SUCCESSFULLY_LOAD, PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED);
    }
}
