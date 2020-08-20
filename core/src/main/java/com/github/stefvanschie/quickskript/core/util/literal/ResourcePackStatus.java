package com.github.stefvanschie.quickskript.core.util.literal;

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
    SUCCESSFULLY_LOAD
}
