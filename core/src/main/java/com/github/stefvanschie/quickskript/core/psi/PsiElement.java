package com.github.stefvanschie.quickskript.core.psi;

import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.context.Context;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
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
     * The line number this element belongs to
     */
    protected final int lineNumber;

    /**
     * The parent of this element, or null if this element is the top-level element
     */
    @Nullable
    private PsiElement<?> parent;

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiElement(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    /**
     * Returns a pre computed value if {@link #isPreComputed()} returns true otherwise executes this element.
     *
     * @param context the context this code is being executed in, may be null if the code is expected to be pre computed
     * @return the computed value which is null if the computation returned null
     * @since 0.1.0
     */
    @Nullable
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
    @NotNull
    public <R> R execute(@Nullable Context context, @NotNull Class<R> forcedResult) {
        T result = execute(context);

        if (forcedResult.isInstance(result)) {
            return forcedResult.cast(result);
        }

        throw new ExecutionException("Result of " + getClass().getSimpleName() +
            " should be " + forcedResult.getSimpleName() + ", but it was " +
            (result == null ? "null" : result.getClass().getSimpleName()), lineNumber);
    }

    /**
     * Executes this element
     *
     * @param context the context this code is being executed in, or null during pre computation
     * @return the computed value
     * @since 0.1.0
     */
    @Nullable
    protected T executeImpl(@Nullable Context context) {
        throw new UnsupportedOperationException("Cannot execute expression without implementation.");
    }

    /**
     * Sets the parent of this element
     *
     * @param parent this element's parent
     * @since 0.1.0
     */
    public void setParent(@NotNull PsiElement<?> parent) {
        if (this.parent != null)
            throw new AssertionError("This element's parent has already been set, its line number: " + parent.lineNumber);
        this.parent = parent;
    }

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

    /**
     * Gets the parent of this element, or null if this element is the top-level element
     *
     * @return the parent
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public PsiElement<?> getParent() {
        return parent;
    }
}
