package com.github.stefvanschie.quickskript.core.util.literal;

import org.jetbrains.annotations.Contract;

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

    /**
     * Gets the amount of ticks this time span lasts. If the amount of ticks is not a whole number, the number returned
     * by this method is rounded up (towards positive infinity). If the amount of ticks exceeds
     * {@link Integer#MAX_VALUE}, this method will return {@link Integer#MAX_VALUE}.
     *
     * @return the time span in ticks
     * @since 0.1.0
     */
    @Contract(pure = true)
    public int getTicks() {
        double ticks = Math.ceil(this.millis / 50.0);

        if (ticks >= Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }

        return (int) ticks;
    }
}
