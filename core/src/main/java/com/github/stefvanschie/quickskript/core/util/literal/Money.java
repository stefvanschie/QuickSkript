package com.github.stefvanschie.quickskript.core.util.literal;

/**
 * Represents a certain amount of money. This class is immutable.
 *
 * @since 0.1.0
 */
public class Money {

    /**
     * The amount of money this object represents
     */
    private double amount;

    /**
     * Creates a new wrapper object for the given amount.
     *
     * @param amount The amount of money this object holds
     * @since 0.1.0
     */
    public Money(double amount) {
        this.amount = amount;
    }
}
