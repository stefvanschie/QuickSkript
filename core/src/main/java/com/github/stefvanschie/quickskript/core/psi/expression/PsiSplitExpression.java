package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Splits a message into multiple parts by a given delimiter
 *
 * @since 0.1.0
 */
public class PsiSplitExpression extends PsiElement<String[]> {

    /**
     * The text to split
     */
    private PsiElement<?> text;

    /**
     * The delimiter
     */
    private PsiElement<?> delimiter;

    /**
     * Creates a new element with the given line number
     *
     * @param text the text to split
     * @param delimiter the delimiter
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiSplitExpression(@NotNull PsiElement<?> text, @NotNull PsiElement<?> delimiter, int lineNumber) {
        super(lineNumber);

        this.text = text;
        this.delimiter = delimiter;

        if (text.isPreComputed() && delimiter.isPreComputed()) {
            preComputed = executeImpl(null, null);

            this.text = null;
            this.delimiter = null;
        }
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected String[] executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        String splitPattern = java.util.regex.Pattern.quote(delimiter.execute(environment, context, String.class));

        return text.execute(environment, context, String.class).split(splitPattern);
    }

    /**
     * A factory for creating {@link PsiSplitExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the patterns and invokes this method with its types if the match succeeds
         *
         * @param text the text to split
         * @param delimiter the delimiter
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("split %text% (at|using|by) [[the] delimiter] %text%")
        @Pattern("%text% split (at|using|by) [[the] delimiter] %text%")
        public PsiSplitExpression parse(@NotNull PsiElement<?> text, @NotNull PsiElement<?> delimiter, int lineNumber) {
            return create(text, delimiter, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param text the text to split
         * @param delimiter the delimiter
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiSplitExpression create(@NotNull PsiElement<?> text, @NotNull PsiElement<?> delimiter,
            int lineNumber) {
            return new PsiSplitExpression(text, delimiter, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.TEXTS;
        }
    }
}
