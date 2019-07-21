package com.github.stefvanschie.quickskript.core.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

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
    @Nullable
    private final X x;

    /**
     * The second value
     */
    @Nullable
    private final Y y;

    /**
     * Creates a new pair
     *
     * @param x the x value
     * @param y the y value
     * @since 0.1.0
     */
    public Pair(@Nullable X x, @Nullable Y y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the first value
     *
     * @return the first value
     * @since 0.1.0
     */
    @Nullable
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
    @Nullable
    @Contract(pure = true)
    public Y getY() {
        return y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pair)) {
            return false;
        }

        Pair<?, ?> other = (Pair<?, ?>) obj;
        return Objects.equals(getX(), other.getX()) && Objects.equals(getY(), other.getY());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;

        result = prime * result + ((getX() == null) ? 0 : getX().hashCode());
        result = prime * result + ((getY() == null) ? 0 : getY().hashCode());

        return result;
    }
}
