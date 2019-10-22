package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the length of a piece of text
 *
 * @since 0.1.0
 */
public class PsiLengthExpression extends PsiElement<Integer> {

    /**
     * The text to get the length of
     */
    private PsiElement<?> text;

    /**
     * Creates a new element with the given line number
     *
     * @param text the text to get the length of
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiLengthExpression(@NotNull PsiElement<?> text, int lineNumber) {
        super(lineNumber);

        this.text = text;

        if (text.isPreComputed()) {
            preComputed = executeImpl(null);

            this.text = null;
        }
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Integer executeImpl(@Nullable Context context) {
        return text.execute(context, Text.class).toString().length();
    }

    /**
     * A factory for creating {@link PsiLengthExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The patterns for matching {@link PsiLengthExpression}s
         */
        @NotNull
        private final SkriptPattern[] patterns = SkriptPattern.parse(
            "[the] length of %texts%",
            "%texts%'[s] length"
        );

        /**
         * Parses the {@link #patterns} and invokes this method with its types if the match succeeds
         *
         * @param text the text to get the length of
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("patterns")
        public PsiLengthExpression parse(@NotNull PsiElement<?> text, int lineNumber) {
            return create(text, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param text the text to get the length of
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiLengthExpression create(@NotNull PsiElement<?> text, int lineNumber) {
            return new PsiLengthExpression(text, lineNumber);
        }
    }
}
