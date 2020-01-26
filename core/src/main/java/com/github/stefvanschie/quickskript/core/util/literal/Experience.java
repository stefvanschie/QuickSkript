package com.github.stefvanschie.quickskript.core.util.literal;

import org.jetbrains.annotations.Contract;

/**
 * Denotes an amount of experience. This class is immutable.
 *
 * @since 0.1.0
 */
public class Experience {

    /**
     * The amount of experience
     */
    private int amount;

    /**
     * Creates a new instance of this class.
     *
     * @param amount the amount of experience
     * @since 0.1.0
     */
    public Experience(int amount) {
        this.amount = amount;
    }

    /**
     * Gets the amount of experience this represents
     *
     * @return the amount of experience
     * @since 0.1.0
     */
    @Contract(pure = true)
    public int getAmount() {
        return amount;
    }
}
