package com.github.stefvanschie.quickskript.psi;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

/**
 * An abstract representation of an element in a skript file.
 *
 * @param <T> the object type that will be returned by executing this element. Void if nothing will be returned.
 * @since 0.1.0
 */
public abstract class PsiElement<T> {

    /**
     * Contains the value computed when loading, if this was possible
     */
    @Nullable
    protected T preComputed;

    /**
     * Returns a pre computed value if {@link #isPreComputed()} returns true otherwise executes this element
     *
     * @return the computed value which is null if the computation returned null
     * @since 0.1.0
     */
    public final T execute() {
        return isPreComputed() ? preComputed : executeImpl();
    }

    /**
     * Executes this element
     *
     * @return the computed value which is null if the computation returned null
     * @since 0.1.0
     */
    protected abstract T executeImpl();

    /**
     * Gets whether this element was pre computed when loading
     *
     * @return true hen the value was computed when loading, false otherwise
     * @since 0.1.0
     */
    @Contract(pure = true)
    public boolean isPreComputed() {
        return preComputed != null;
    }
}
