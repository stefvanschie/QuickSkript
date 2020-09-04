package com.github.stefvanschie.quickskript.core.util.literal;

/**
 * Represents a specific time duration
 *
 * @since 0.1.0
 */
public class TimeSpan {

    /**
     * The amount of milliseconds this time span spans
     */
    private long millis;

    /**
     * Creates a new time span based on the provided amount of milliseconds
     *
     * @param millis the amount of milliseconds this time span spans
     * @since 0.1.0
     */
    public TimeSpan(long millis) {
        this.millis = millis;
    }
}
