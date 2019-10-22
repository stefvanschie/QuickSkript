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
 * Splits a message into multiple parts by a given delimiter
 *
 * @since 0.1.0
 */
public class PsiSplitExpression extends PsiElement<Text[]> {

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
            preComputed = executeImpl(null);

            this.text = null;
            this.delimiter = null;
        }
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Text[] executeImpl(@Nullable Context context) {
        String splitPattern = java.util.regex.Pattern.quote(delimiter.execute(context, Text.class).toString());
        String[] splits = text.execute(context, Text.class).toString().split(splitPattern);
        Text[] texts = new Text[splits.length];

        for (int i = 0; i < splits.length; i++) {
            texts[i] = Text.parse(splits[i]);
        }

        return texts;
    }

    /**
     * A factory for creating {@link PsiSplitExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The patterns for matching {@link PsiSplitExpression}s
         */
        @NotNull
        private final SkriptPattern[] patterns = SkriptPattern.parse(
            "split %text% (at|using|by) [[the] delimiter] %text%",
            "%text% split (at|using|by) [[the] delimiter] %text%"
        );

        /**
         * Parses the {@link #patterns} and invokes this method with its types if the match succeeds
         *
         * @param text the text to split
         * @param delimiter the delimiter
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("patterns")
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
    }
}
