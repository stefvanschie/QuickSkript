package com.github.stefvanschie.quickskript.core.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Function;

/**
 * A function that accepts three arguments and returns a result.
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @param <V> the type of the third argument to the function
 * @param <R> the type of the result of the function
 * @since 0.1.0
 */
@FunctionalInterface
public interface TriFunction<T, U, V, R> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @param v the third function argument
     * @return the function result
     * @since 0.1.0
     */
    R apply(T t, U u, V v);

    /**
     * Returns a function that applies this function and after that the other specified function.
     *
     * @param <W> the type of output of the {@code after} function
     * @param after the function to apply after this function is applied
     * @return a function that first applies this function and then applies the {@code after} function
     */
    @NotNull
    @Contract(pure = true)
    default <W> TriFunction<T, U, V, W> andThen(@NotNull Function<? super R, ? extends W> after) {
        Objects.requireNonNull(after);
        return (t, u, v) -> after.apply(apply(t, u, v));
    }
}
