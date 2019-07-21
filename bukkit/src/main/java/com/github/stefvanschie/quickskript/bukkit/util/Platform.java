package com.github.stefvanschie.quickskript.bukkit.util;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

/**
 * An enum constant holding different platforms for platform specific implementations.
 *
 * @since 0.1.0
 */
public enum Platform {

    /**
     * The Bukkit platform
     *
     * @since 0.1.0
     */
    BUKKIT("Bukkit"),

    /**
     * The Spigot platform
     *
     * @since 0.1.0
     */
    SPIGOT("Spigot", BUKKIT),

    /**
     * The Paper platform
     *
     * @since 0.1.0
     */
    PAPER("Paper", SPIGOT, BUKKIT);

    /**
     * The name of the platform
     */
    private final String name;

    /**
     * Other platforms that also exist on this platform
     */
    private final Platform[] alsoAvailable;

    /**
     * Creates a new platform this plugin can run on
     *
     * @param name the server name of this platform
     * @param alsoAvailable other platforms that also exist on this platform
     * @since 0.1.0
     */
    Platform(String name, Platform... alsoAvailable) {
        this.name = name;
        this.alsoAvailable = alsoAvailable;
    }

    /**
     * Checks whether the specified platform is available on this platform.
     *
     * @param platform the platform we want to check if it is available
     * @return true if the platform is available, false otherwise
     * @since 0.1.0
     */
    @Contract(pure = true)
    public boolean isAvailable(@NotNull Platform platform) {
        return this == platform || Stream.of(alsoAvailable).anyMatch(p -> p == platform);
    }

    /**
     * Gets the platform this server is currently running on, or null if the platform could not be detected. When the
     * parameter passed is null, a default of {@link #getLowestPlatform()} will be assumed.
     *
     * @return the platform of this server
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public static Platform getPlatform() {
        String version = Bukkit.getVersion();

        //the version may not be available in testing environments
        //noinspection ConstantConditions
        if (version == null) {
            return getLowestPlatform();
        }

        Platform platform = fromName(version.substring(4).split("-")[0]);

        if (platform == null) {
            return getLowestPlatform();
        }

        return platform;
    }

    /**
     * Gets the platform by the given name, or null if the platform couldn't be found. This uses the names as specified
     * in the constructor to detect the different platforms.
     *
     * @param name the name of the platform
     * @return the platform by its name, or null if the platform couldn't be found
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    private static Platform fromName(@NotNull String name) {
        return Stream.of(Platform.values()).filter(platform -> platform.name.equals(name)).findAny().orElse(null);
    }

    /**
     * Gets the lowest platform currently available to be used as a default for places in which we have
     * platform-independent code. This currently is the Bukkit platform.
     *
     * @return {@link #BUKKIT}
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    private static Platform getLowestPlatform() {
        return BUKKIT;
    }
}
