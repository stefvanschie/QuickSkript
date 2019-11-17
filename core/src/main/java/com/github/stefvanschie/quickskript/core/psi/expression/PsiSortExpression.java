package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiCollection;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Sorts a collection using a default comparator.
 *
 * @since 0.1.0
 */
public class PsiSortExpression extends PsiElement<List<?>> {

    /**
     * The collection to be sorted
     */
    private PsiElement<?> collection;

    /**
     * Creates a new element with the given line number
     *
     * @param collection the collection to be sorted
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiSortExpression(@NotNull PsiElement<?> collection, int lineNumber) {
        super(lineNumber);

        this.collection = collection;

        if (collection.isPreComputed()) {
            preComputed = executeImpl(null);

            this.collection = null;
        }
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected List<?> executeImpl(@Nullable Context context) {
        return PsiCollection.toStreamForgiving(collection.execute(context)).sorted().collect(Collectors.toList());
    }

    /**
     * A factory for creating {@link PsiSortExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiSortExpression}s
         */
        @NotNull
        private SkriptPattern pattern = SkriptPattern.parse("sorted %objects%");

        /**
         * Parses the {@link #pattern} and invokes this method with its types if the match succeeds
         *
         * @param collection the collection to be sorted
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("pattern")
        public PsiSortExpression parse(@NotNull PsiElement<?> collection, int lineNumber) {
            return create(collection, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param collection the collection to be sorted
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiSortExpression create(@NotNull PsiElement<?> collection, int lineNumber) {
            return new PsiSortExpression(collection, lineNumber);
        }
    }
}
