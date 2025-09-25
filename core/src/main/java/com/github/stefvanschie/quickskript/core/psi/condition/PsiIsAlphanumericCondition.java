package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks whether a given text is alphanumeric.
 *
 * @since 0.1.0
 */
public class PsiIsAlphanumericCondition extends PsiElement<Boolean> {

    /**
     * The text to see if it's alphanumeric.
     */
    private PsiElement<?> text;

    /**
     * False if the result of the text should be inverted.
     */
    private final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param text the text to see if it's alphanumeric
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsAlphanumericCondition(PsiElement<?> text, boolean positive, int lineNumber) {
        super(lineNumber);

        this.text = text;
        this.positive = positive;

        if (this.text.isPreComputed()) {
            super.preComputed = executeImpl(null, null);

            this.text = null;
        }
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        String text = this.text.execute(environment, context, String.class);

        if (text.isEmpty()) {
            return !this.positive;
        }

        int length = text.codePointCount(0, text.length());

        for (int index = 0; index < length; index++) {
            if (!Character.isLetterOrDigit(text.codePointAt(index))) {
                return !this.positive;
            }
        }

        return this.positive;
    }

    /**
     * A factory for creating {@link PsiIsAlphanumericCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param text the txt to see if it's alphanumeric
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%texts% (is|are) alphanumeric")
        public PsiIsAlphanumericCondition parsePositive(@NotNull PsiElement<?> text, int lineNumber) {
            return create(text, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param text the text to see if it's alphanumeric
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%texts% (isn't|is not|aren't|are not) alphanumeric")
        public PsiIsAlphanumericCondition parseNegative(@NotNull PsiElement<?> text, int lineNumber) {
            return create(text, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        private PsiIsAlphanumericCondition create(@NotNull PsiElement<?> text, boolean positive, int lineNumber) {
            return new PsiIsAlphanumericCondition(text, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
