package com.github.stefvanschie.quickskript.core.psi.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Stream;

/**
 * Represents a result from execution that possibly yielded more than one return value.
 *
 * @param <T> the type of return value
 * @since 0.1.0
 */
public class MultiResult<T> implements Iterable<T> {

    /**
     * The elements in this result.
     */
    @Nullable
    private final Collection<T> elements;

    /**
     * Creates a new multi result from a single element.
     *
     * @param element the element
     * @since 0.1.0
     */
    public MultiResult(@Nullable T element) {
        this.elements = Collections.singleton(element);
    }

    /**
     * Creates a new multi result from an array of elements.
     *
     * @param elements the elements
     * @since 0.1.0
     */
    public MultiResult(@Nullable T[] elements) {
        this.elements = Arrays.asList(elements);
    }

    /**
     * Gets the amount of elements in this result.
     *
     * @return the size of this result
     * @since 0.1.0
     */
    @Contract(pure = true)
    public int getSize() {
        if (this.elements == null) {
            throw new IllegalStateException("this.elements is null");
        }

        return this.elements.size();
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public Iterator<T> iterator() {
        if (this.elements == null) {
            throw new IllegalStateException("this.elements is null");
        }

        return this.elements.iterator();
    }

    /**
     * Returns a stream of the elements in the result.
     *
     * @return a stream of elements
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public Stream<T> stream() {
        if (this.elements == null) {
            throw new IllegalStateException("this.elements is null");
        }

        return this.elements.stream();
    }

    /**
     * Returns a list of the elements in the result. If the original elements were ordered, the list returned from this
     * method will be in the same order. The returned list is unmodifiable.
     *
     * @return a list of elements
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public List<T> toList() {
        return stream().toList();
    }
}
