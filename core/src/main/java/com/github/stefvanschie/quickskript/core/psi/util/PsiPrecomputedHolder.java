package com.github.stefvanschie.quickskript.core.psi.util;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
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
     * @param lineNumber the line number of this element
     * @since 0.1.0
     */
    public PsiPrecomputedHolder(@NotNull T value, int lineNumber) {
        super(lineNumber);

        preComputed = value;
    }

    @Override
    @Contract("_ -> fail")
    protected final T executeImpl(@Nullable Context context) {
        throw new AssertionError("Since the preComputed variable is always set, this method should never get called");
    }
}
