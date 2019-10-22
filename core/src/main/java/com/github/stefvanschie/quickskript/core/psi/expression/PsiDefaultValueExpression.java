package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Returns the value if it exists, otherwise it returns the default value as specified.
 *
 * @since 0.1.0
 */
public class PsiDefaultValueExpression extends PsiElement<Object> {

    /**
     * The value that will be returned if this exists
     */
    private PsiElement<?> value;

    /**
     * The value that will be returned if {@link #value} doesn't exist
     */
    private PsiElement<?> defaultValue;

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiDefaultValueExpression(@NotNull PsiElement<?> value, @NotNull PsiElement<?> defaultValue,
        int lineNumber) {
        super(lineNumber);

        this.value = value;
        this.defaultValue = defaultValue;

        if (this.value.isPreComputed() && this.defaultValue.isPreComputed()) {
            preComputed = executeImpl(null);

            this.value = null;
            this.defaultValue = null;
        }
    }

    @Nullable
    @Contract(pure = true)
    @Override
    protected Object executeImpl(@Nullable Context context) {
        Object value = this.value.execute(context);

        if (value == null) {
            return defaultValue.execute(context);
        }

        return value;
    }

    /**
     * A factory for creating {@link PsiDefaultValueExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for creating {@link PsiDefaultValueExpression}s
         */
        @NotNull
        private final SkriptPattern pattern = SkriptPattern.parse("%objects% (otherwise|?) %objects%");

        /**
         * Parses the {@link #pattern} and invokes this method with its types if the match succeeds
         *
         * @param value the value to return if this exists
         * @param defaultValue the value to return if the given value doesn't exist
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("pattern")
        public PsiDefaultValueExpression parse(@NotNull PsiElement<?> value, @NotNull PsiElement<?> defaultValue,
            int lineNumber) {
            return create(value, defaultValue, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param value the value to return if this exists
         * @param defaultValue the value to return if the given value doesn't exist
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiDefaultValueExpression create(@NotNull PsiElement<?> value, @NotNull PsiElement<?> defaultValue,
            int lineNumber) {
            return new PsiDefaultValueExpression(value, defaultValue, lineNumber);
        }
    }
}
