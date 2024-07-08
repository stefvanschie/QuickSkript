package com.github.stefvanschie.quickskript.core.psi.util.multiresult.connective;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * Indicates how the elements of the list interact.
 *
 * @since 0.1.0
 */
public interface Connective {

    /**
     * Tests whether the elements in the provided collection pass the predicate according to the connective. This method
     * short-circuits.
     *
     * @param elements the elements to test
     * @param predicate the predicate the elements need to pass
     * @return true if all elements pass the predicate
     * @param <T> the type of the elements
     * @since 0.1.0
     */
    <T> boolean test(@NotNull Collection<T> elements, @NotNull Predicate<T> predicate);
}
