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
 * Gets a substring of the original text
 *
 * @since 0.1.0
 */
public class PsiSubstringExpression extends PsiElement<Text> {

    /**
     * The relation of the start index to the text
     */
    private PositionRelation relation;

    /**
     * The text to substring
     */
    private PsiElement<?> text;

    /**
     * The indices of the substring
     */
    private PsiElement<?> startIndex, endIndex;

    /**
     * Creates a new element with the given line number
     *
     * @param relation the relation of the indices to the text
     * @param text the text to substring
     * @param startIndex the starting index of the substring
     * @param endIndex the end index of the substring
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiSubstringExpression(@NotNull PositionRelation relation, @NotNull PsiElement<?> text,
        @Nullable PsiElement<?> startIndex, @Nullable PsiElement<?> endIndex, int lineNumber) {
        super(lineNumber);

        this.relation = relation;
        this.text = text;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Nullable
    @Override
    protected Text executeImpl(@Nullable Context context) {
        String text = this.text.execute(context, Text.class).toString();

        int startIndex = -1;

        if (this.startIndex == null) {
            startIndex = 1;
        } else {
            int start = this.startIndex.execute(context, Number.class).intValue();

            if (relation == PositionRelation.STATIC) {
                startIndex = start;
            } else if (relation == PositionRelation.RELATIVE) {
                startIndex = text.length() - start + 1;
            }
        }

        int endIndex = this.endIndex == null ? text.length() : this.endIndex.execute(context, Number.class).intValue();

        return Text.parse(text.substring(startIndex - 1, endIndex));
    }

    /**
     * A factory for creating {@link PsiSubstringExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiSubstringExpression}s
         */
        @NotNull
        private SkriptPattern pattern = SkriptPattern.parse(
            "[the] (part|sub[ ](text|string)) of %texts% (between|from) (ind(ex|ices)|character[s]) %number% [(and|to) (index|character)] %number%"
        );

        /**
         * The patterns for matching the first characters for {@link PsiSubstringExpression}
         */
        @NotNull
        private SkriptPattern[] patternsFirst = SkriptPattern.parse(
            "[the] first [%number%] character[s] of %texts%",
            "[the] %number% first characters of %texts%"
        );

        /**
         * The patterns for matching the last characters for {@link PsiSubstringExpression}
         */
        @NotNull
        private SkriptPattern[] patternsLast = SkriptPattern.parse(
            "[the] last [%number%] character[s] of %texts%",
            "[the] %number% last characters of %texts%"
        );

        /**
         * Parses the {@link #pattern} and invokes this method with its types if the match succeeds
         *
         * @param text the text to substring
         * @param startIndex the starting index of the substring
         * @param endIndex the end index of the substring
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("pattern")
        public PsiSubstringExpression parse(@NotNull PsiElement<?> text, @Nullable PsiElement<?> startIndex,
            @Nullable PsiElement<?> endIndex, int lineNumber) {
            return create(PositionRelation.STATIC, text, startIndex, endIndex, lineNumber);
        }

        /**
         * Parses the {@link #patternsFirst} and invokes this method with its types if the match succeeds
         *
         * @param endIndex the end index of the substring
         * @param text the text to substring
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("patternsFirst")
        public PsiSubstringExpression parseFirst(@Nullable PsiElement<?> endIndex, @NotNull PsiElement<?> text,
            int lineNumber) {
            return create(PositionRelation.STATIC, text, null, endIndex, lineNumber);
        }
        /**
         * Parses the {@link #patternsLast} and invokes this method with its types if the match succeeds
         *
         * @param startIndex the start index of the substring
         * @param text the text to substring
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("patternsLast")
        public PsiSubstringExpression parseLast(@Nullable PsiElement<?> startIndex, @NotNull PsiElement<?> text,
            int lineNumber) {
            return create(PositionRelation.RELATIVE, text, startIndex, null, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param text the text to sub text
         * @param startIndex the starting index of the substring
         * @param endIndex the end index of the substring
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiSubstringExpression create(@NotNull PositionRelation relation, @NotNull PsiElement<?> text,
            @Nullable PsiElement<?> startIndex, @Nullable PsiElement<?> endIndex, int lineNumber) {
            return new PsiSubstringExpression(relation, text, startIndex, endIndex, lineNumber);
        }
    }

    /**
     * How the provided indices should be interpreted with regards to the entire text
     *
     * @since 0.1.0
     */
    private enum PositionRelation {

        /**
         * The positions are interpreted as being relative to the text's length
         *
         * @since 0.1.0
         */
        RELATIVE,

        /**
         * The positions are interpreted as being static, with no relation to the text's length
         *
         * @since 0.1.0
         */
        STATIC
    }
}
