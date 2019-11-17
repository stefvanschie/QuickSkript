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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Shuffles a collection. This cannot be pre-computed, since the list should be shuffled differently every time.
 *
 * @since 0.1.0
 */
public class PsiShuffleExpression extends PsiElement<List<?>> {

    /**
     * The collection to shuffle
     */
    @NotNull
    private PsiElement<?> collection;

    /**
     * Creates a new element with the given line number
     *
     * @param collection the collection to shuffle
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiShuffleExpression(@NotNull PsiElement<?> collection, int lineNumber) {
        super(lineNumber);

        this.collection = collection;
    }

    @Nullable
    @Override
    protected List<?> executeImpl(@Nullable Context context) {
        List<?> list = PsiCollection.toStreamForgiving(collection.execute(context)).collect(Collectors.toList());

        Collections.shuffle(list);

        return list;
    }

    /**
     * A factory for creating {@link PsiShuffleExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiShuffleExpression}s
         */
        @NotNull
        private SkriptPattern pattern = SkriptPattern.parse("shuffled %objects%");

        /**
         * Parses the {@link #pattern} and invokes this method with its types if the match succeeds
         *
         * @param collection the collection to shuffle
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("pattern")
        public PsiShuffleExpression parse(@NotNull PsiElement<?> collection, int lineNumber) {
            return create(collection, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param collection the collection to shuffle
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiShuffleExpression create(@NotNull PsiElement<?> collection, int lineNumber) {
            return new PsiShuffleExpression(collection, lineNumber);
        }
    }
}
