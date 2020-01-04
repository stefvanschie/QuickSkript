package com.github.stefvanschie.quickskript.core.util;

import java.io.IOException;
import java.util.Properties;

/**
 * A utility class that provides information about the application.
 *
 * @since 0.1.0
 */
public final class ApplicationInfo {

    /**
     * The version of this application. This is retrieved from the app.properties file.
     */
    private static final String VERSION;

    /**
     * Gets the version this application is currently on.
     *
     * @return the version of this application.
     * @since 0.1.0
     */
    public static String getVersion() {
        return VERSION;
    }

    static {
        Properties properties = new Properties();

        try {
            properties.load(ApplicationInfo.class.getResourceAsStream("/application-info/app.properties"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        VERSION = properties.getProperty("version");
    }
}
