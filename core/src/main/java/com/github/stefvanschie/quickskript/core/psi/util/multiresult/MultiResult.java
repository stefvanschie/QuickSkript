package com.github.stefvanschie.quickskript.core.psi.util.multiresult;

import com.github.stefvanschie.quickskript.core.psi.util.multiresult.connective.Conjunction;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.connective.Connective;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
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
    @NotNull
    private final List<T> elements;

    /**
     * The connective between the elements of this list.
     */
    @NotNull
    private final Connective connective;

    /**
     * Creates a new, empty multi result.
     *
     * @since 0.1.0
     */
    public MultiResult() {
        this.elements = Collections.emptyList();
        this.connective = Conjunction.INSTANCE;
    }

    /**
     * Creates a new multi result from a single element.
     *
     * @param element the element
     * @since 0.1.0
     */
    public MultiResult(@Nullable T element) {
        this.elements = Collections.singletonList(element);
        this.connective = Conjunction.INSTANCE;
    }

    /**
     * Creates a new multi result from an array of elements.
     *
     * @param elements the elements
     * @param connective the connective between the elements
     * @since 0.1.0
     */
    public MultiResult(@Nullable T[] elements, @NotNull Connective connective) {
        this.elements = Arrays.asList(elements);
        this.connective = connective;
    }

    /**
     * Creates a new multi result from the given collection.
     *
     * @param elements the elements
     * @param connective the connective
     * @since 0.1.0
     */
    private MultiResult(@NotNull Collection<T> elements, @NotNull Connective connective) {
        this.elements = new ArrayList<>(elements);
        this.connective = connective;
    }

    /**
     * Zips the current {@link MultiResult} with the provided {@link MultiResult}, combining the elements according to
     * the provided mapping function. The mapping will be performed in the same ordering as the ordering of this and the
     * provided {@link MultiResult}. If one {@link MultiResult} has exhausted all its element, while the other has not,
     * the mapping function will be called with the appropriate value from the unexhausted {@link MultiResult}, while
     * the other argument will be {@literal null}. The mapping function may also have {@literal null} arguments if one
     * or both of the multi results contain {@literal null} values. The mapping function may return {@literal null}
     * values, in which case these become part of the resulting object. The returned object itself will never be
     * {@literal null}. The returned {@link MultiResult} will have the same connective as the provided
     * {@link MultiResult}.
     *
     * @param multiResult the multi result to zip this multi result with
     * @param mapping the mapping function to apply to the pair of elements
     * @return a new multi result with the results of the mapping function applied to the pairs of elements
     * @param <U> the type of the provided multi result
     * @param <V> the resulting type of the new multi result
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public <U, V> MultiResult<V> zip(@NotNull MultiResult<U> multiResult,
                                     @NotNull BiFunction<? super T, ? super U, ? extends V> mapping) {
        int maxSize = Math.max(getSize(), multiResult.getSize());
        Collection<V> elements = new ArrayList<>(maxSize);

        for (int index = 0; index < maxSize; index++) {
            T t = null;
            U u = null;

            if (index < getSize()) {
                t = this.elements.get(index);
            }

            if (index < multiResult.getSize()) {
                u = multiResult.elements.get(index);
            }

            elements.add(mapping.apply(t, u));
        }

        return new MultiResult<>(elements, multiResult.connective);
    }

    /**
     * Maps the elements from this multi result to a new multi result. The elements will be mapped in the same order as
     * this multi result was constructed and the result will have the same ordering, if an ordering existed. The
     * connective type in the new multi result is identical to this type.
     *
     * @param mapping the mapping to apply to all elements
     * @param <U> the type of the result
     * @return a new multi result with the mapped elements
     * @since 0.1.0
     */
    @NotNull
    public <U> MultiResult<U> map(@NotNull Function<T, U> mapping) {
        Collection<U> elements = new ArrayList<>(this.elements.size());

        for (T element : this.elements) {
            elements.add(mapping.apply(element));
        }

        return new MultiResult<>(elements, this.connective);
    }

    /**
     * Tests whether the elements in this result pass the predicate according to the connective attached to this result.
     * This method short-circuits. The elements will be tested in the same order as this multi result was constructed,
     * if an ordering existed.
     *
     * @param predicate the predicate the elements need to pass
     * @return true if all elements pass the predicate
     * @since 0.1.0
     */
    public boolean test(@NotNull Predicate<T> predicate) {
        return this.connective.test(this.elements, predicate);
    }

    /**
     * Checks whether this result returned an element that equals the provided element.
     *
     * @param element the element to find
     * @return true if the element is contained in this result, false otherwise
     * @since 0.1.0
     */
    @Contract(pure = true)
    public boolean contains(@NotNull T element) {
        return this.elements.contains(element);
    }

    /**
     * Gets the amount of elements in this result.
     *
     * @return the size of this result
     * @since 0.1.0
     */
    @Contract(pure = true)
    public int getSize() {
        return this.elements.size();
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public Iterator<T> iterator() {
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
