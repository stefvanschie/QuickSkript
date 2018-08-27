package com.github.stefvanschie.quickskript.psi;

import com.github.stefvanschie.quickskript.context.Context;
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
     * @param context the context this code is being executed in, may be null if the code is expected to be pre computed
     * @return the computed value which is null if the computation returned null
     * @since 0.1.0
     */
    public final T execute(@Nullable Context context) {
        return isPreComputed() ? preComputed : executeImpl(context);
    }

    /**
     * Executes this element
     *
     * @param context the context this code is being executed in, may be null if the code is expected to be pre computed
     * @return the computed value which is null if the computation returned null
     * @since 0.1.0
     */
    protected abstract T executeImpl(@Nullable Context context);

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
