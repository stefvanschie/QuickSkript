package com.github.stefvanschie.quickskript.core.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Holds pairs of values
 *
 * @param <X> the first value's type
 * @param <Y> the second value's type
 * @since 0.1.0
 */
public class Pair<X, Y> {

    /**
     * The first value
     */
    @NotNull
    private final X x;

    /**
     * The second value
     */
    @NotNull
    private final Y y;

    /**
     * Creates a new pair
     *
     * @param x the x value
     * @param y the y value
     * @since 0.1.0
     */
    public Pair(@NotNull X x, @NotNull Y y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the first value
     *
     * @return the first value
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public X getX() {
        return x;
    }

    /**
     * Gets the second value
     *
     * @return the second value
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public Y getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pair)) {
            return false;
        }

        Pair<?, ?> other = (Pair<?, ?>) obj;
        return Objects.equals(getX(), other.getX()) && Objects.equals(getY(), other.getY());
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;

        result = prime * result + getX().hashCode();
        result = prime * result + getY().hashCode();

        return result;
    }
}
