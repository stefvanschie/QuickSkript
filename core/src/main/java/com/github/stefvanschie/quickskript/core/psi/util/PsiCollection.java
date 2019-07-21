package com.github.stefvanschie.quickskript.core.psi.util;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A psi element which stores a {@link List} of the {@link PsiElement}s specified in the constructor.
 * It is considered pre computed only if all of its stored elements are also pre computed.
 * Please note that no {@link Collection} cloning is done internally: modifications of
 * the constructor parameter and returned {@link Collection} have visible consequences.
 *
 * @since 0.1.0
 */
public class PsiCollection<T> extends PsiElement<Collection<PsiElement<T>>> {

    /**
     * The stored elements.
     */
    private final Collection<PsiElement<T>> elements;

    /**
     * Creates a new psi element which stores the specified {@link Collection}.
     *
     * @param elements the element this psi should store
     * @param lineNumber the line number of this element
     * @since 0.1.0
     */
    private PsiCollection(@NotNull Collection<PsiElement<T>> elements, int lineNumber) {
        super(lineNumber);

        if (elements.stream().allMatch(PsiElement::isPreComputed)) {
            preComputed = elements;

            this.elements = null;
        } else {
            this.elements = elements;
        }
    }

    /**
     * Creates a new psi element which stores the specified ones.
     * The {@link Stream} is internally collected into a
     * {@link List} which is a terminal operation.
     *
     * @param elements the element this psi should store
     * @param lineNumber the line number of this element
     * @since 0.1.0
     */
    public PsiCollection(@NotNull Stream<PsiElement<T>> elements, int lineNumber) {
        this(elements.collect(Collectors.toList()), lineNumber);
    }

    /**
     * Creates a new psi element which stores the specified ones.
     * The array is internally converted into a {@link List}.
     *
     * @param lineNumber the line number of this element
     * @param elements the element this psi should store
     * @since 0.1.0
     */
    @SafeVarargs
    public PsiCollection(int lineNumber, @NotNull PsiElement<T>... elements) {
        this(Arrays.asList(elements), lineNumber);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Contract(pure = true)
    @Override
    protected Collection<PsiElement<T>> executeImpl(@Nullable Context context) {
        return elements;
    }
}
