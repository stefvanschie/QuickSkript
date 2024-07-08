package com.github.stefvanschie.quickskript.core.psi.util.multiresult.connective;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * The elements in the list form a disjunction.
 *
 * @since 0.1.0
 */
public class Disjunction implements Connective {

    /**
     * An instance of this class.
     */
    @NotNull
    public final static Disjunction INSTANCE = new Disjunction();

    @Override
    public <T> boolean test(@NotNull Collection<T> elements, @NotNull Predicate<T> predicate) {
        for (T element : elements) {
            if (predicate.test(element)) {
                return true;
            }
        }

        return false;
    }
}
