package com.github.stefvanschie.quickskript.psi.literal;

import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * A psi element which stores a {@link Collection} of the {@link PsiElement}s specified in the constructor.
 * It is considered pre computed only if all of its stored elements are also pre computed.
 *
 * @since 0.1.0
 */
public class PsiCollection<T> extends PsiElement<Collection<PsiElement<T>>> {

    /**
     * The stored elements.
     */
    private Collection<PsiElement<T>> elements;

    /**
     * Creates a new psi element which stores the specified ones
     *
     * @param elements the element this psi stores
     */
    public PsiCollection(Collection<PsiElement<T>> elements) {
        this.elements = elements;

        if (this.elements.stream().allMatch(PsiElement::isPreComputed)) {
            preComputed = this.elements;
            this.elements = null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final Collection<PsiElement<T>> executeImpl(@Nullable Context context) {
        return elements;
    }
}
