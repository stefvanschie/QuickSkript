package com.github.stefvanschie.quickskript.psi;

import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.psi.exception.ExecutionException;
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
     * Returns a pre computed value if {@link #isPreComputed()} returns true otherwise executes this element.
     *
     * @param context the context this code is being executed in, may be null if the code is expected to be pre computed
     * @return the computed value which is null if the computation returned null
     * @since 0.1.0
     */
    public final T execute(@Nullable Context context) {
        return isPreComputed() ? preComputed : executeImpl(context);
    }

    /**
     * Returns a pre computed value if {@link #isPreComputed()} returns true otherwise executes this element.
     * If the result is not an instance of the specified {@link Class} an {@link ExecutionException} is thrown.
     *
     * @param context the context this code is being executed in, may be null if the code is expected to be pre computed
     * @param forcedResult the {@link Class} this method must return an instance of
     * @param <R> the type this method must return an instance of
     * @return the computed value which is null if the computation returned null
     * @since 0.1.0
     */
    public <R> R execute(@Nullable Context context, Class<R> forcedResult) {
        T result = execute(context);
        if (forcedResult.isInstance(result))
            return forcedResult.cast(result);

        throw new ExecutionException("Result of " + getClass().getSimpleName() +
                " should be a " + forcedResult.getSimpleName() + ", but it wasn't");
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
