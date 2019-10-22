package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the index of a piece of text in a different text
 *
 * @since 0.1.0
 */
public class PsiIndexOfExpression extends PsiElement<Integer> {

    /**
     * The haystack and the needle
     */
    private PsiElement<?> haystack, needle;

    /**
     * The search position
     */
    private SearchPosition searchPosition;

    /**
     * Creates a new element with the given line number
     *
     * @param needle the text to look for
     * @param haystack the text in which to look
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIndexOfExpression(@NotNull PsiElement<?> needle, @NotNull PsiElement<?> haystack,
        @NotNull SearchPosition searchPos, int lineNumber) {
        super(lineNumber);

        this.needle = needle;
        this.haystack = haystack;
        this.searchPosition = searchPos;

        if (needle.isPreComputed() && haystack.isPreComputed()) {
            preComputed = executeImpl(null);

            this.haystack = null;
            this.needle = null;
            this.searchPosition = null;
        }
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Integer executeImpl(@Nullable Context context) {
        String needle = this.needle.execute(context, Text.class).toString();
        String haystack = this.haystack.execute(context, Text.class).toString();
        int index;

        if (searchPosition == SearchPosition.FIRST) {
            index = haystack.indexOf(needle);
        } else if (searchPosition == SearchPosition.LAST) {
            index = haystack.lastIndexOf(needle);
        } else {
            var innerException = new UnsupportedOperationException("Encountered unknown search position");

            throw new ExecutionException(innerException, lineNumber);
        }

        return index == -1 ? -1 : index + 1;
    }

    /**
     * An enum to specify the search position of this element
     *
     * @since 0.1.0
     */
    private enum SearchPosition {
        FIRST,
        LAST
    }

    /**
     * A factory for creating {@link PsiIndexOfExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for parsing first {@link PsiIndexOfExpression}s
         */
        @NotNull
        private final SkriptPattern firstPattern = SkriptPattern.parse("[the] [first] index of %text% in %text%");

        /**
         * The pattern for parsing last {@link PsiIndexOfExpression}s
         */
        @NotNull
        private final SkriptPattern lastPattern = SkriptPattern.parse("[the] last index of %text% in %text%");

        /**
         * Parses the {@link #firstPattern} and invokes this method with its types if the match succeeds
         *
         * @param needle the text to search for
         * @param haystack the text to search in
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("firstPattern")
        public PsiIndexOfExpression parseFirst(@NotNull PsiElement<?> needle, @NotNull PsiElement<?> haystack,
            int lineNumber) {
            return create(needle, haystack, SearchPosition.FIRST, lineNumber);
        }

        /**
         * Parses the {@link #lastPattern} and invokes this method with its types if the match succeeds
         *
         * @param needle the text to search for
         * @param haystack the text to search in
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("lastPattern")
        public PsiIndexOfExpression parseLast(@NotNull PsiElement<?> needle, @NotNull PsiElement<?> haystack,
            int lineNumber) {
            return create(needle, haystack, SearchPosition.LAST, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param needle the text to search for
         * @param haystack the text to search in
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiIndexOfExpression create(@NotNull PsiElement<?> needle, @NotNull PsiElement<?> haystack,
            @NotNull SearchPosition searchPos, int lineNumber) {
            return new PsiIndexOfExpression(needle, haystack, searchPos, lineNumber);
        }
    }
}
