package com.github.stefvanschie.quickskript.psi.literal;

import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import org.jetbrains.annotations.Nullable;

/**
 * A psi element which holds the value specified in the constructor.
 *
 * @since 0.1.0
 */
public class PsiPrecomputedHolder<T> extends PsiElement<T> {

    /**
     * Creates a new psi element which holds a precomputed value
     *
     * @param value the value this psi is wrapping
     */
    public PsiPrecomputedHolder(T value) {
        preComputed = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final T executeImpl(@Nullable Context context) {
        throw new AssertionError("Since the preComputed variable is always set, this method should never get called");
    }
}
