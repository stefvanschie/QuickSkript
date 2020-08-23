package com.github.stefvanschie.quickskript.core.util.literal;

/**
 * Represents time in a Minecraft world
 *
 * @since 0.1.0
 */
public class Time {

    /**
     * The amount of ticks in this Minecraft world. This is always between [0,24000). 0 ticks represents 06:00 and not
     * midnight, like other time systems.
     */
    private final int ticks;

    /**
     * Creates a new time with the given amount of ticks. If this is greater or equal to 24000, this will be changed to
     * fall within the range [0, 24000). If this is below zero, an {@link IllegalArgumentException} will be thrown.
     *
     * @param ticks the amount of ticks
     * @since 0.1.0
     * @throws IllegalArgumentException when the amount of ticks is below zero
     */
    public Time(int ticks) {
        if (ticks < 0) {
            throw new IllegalArgumentException("Amount of ticks below zero");
        }

        this.ticks = ticks % 24000;
    }
}
