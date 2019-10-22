package com.github.stefvanschie.quickskript.core.skript;

import org.jetbrains.annotations.NotNull;

/**
 * A class for storing skript code
 */
public interface Skript {

    /**
     * Returns the name of this skript.
     *
     * @return the name of this skript
     * @since 0.1.0
     */
    @NotNull
    String getName();
}
