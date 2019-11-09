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
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Gets a random element from a collection
 *
 * @since 0.1.0
 */
public class PsiRandomExpression extends PsiElement<Object> {

    /**
     * The collection to get an element from
     */
    @NotNull
    private PsiElement<?> collection;

    /**
     * Creates a new element with the given line number
     *
     * @param collection the collection to get an element from
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiRandomExpression(@NotNull PsiElement<?> collection, int lineNumber) {
        super(lineNumber);

        this.collection = collection;
    }

    @Nullable
    @Contract(pure = true)
    @Override
    protected Object executeImpl(@Nullable Context context) {
        List<?> list = PsiCollection.toStreamForgiving(collection.execute(context))
            .collect(Collectors.toUnmodifiableList());

        if (list.size() == 0) {
            return null;
        }

        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }

    /**
     * A factory for creating {@link PsiRandomExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiRandomExpression}s
         */
        @NotNull
        private SkriptPattern pattern = SkriptPattern.parse("[a] random %*type% [out] of %objects%");

        /**
         * Parses the {@link #pattern} and invokes this method with its types if the match succeeds
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("pattern")
        public PsiRandomExpression parse(@NotNull String type, @NotNull PsiElement<?> collection, int lineNumber) {
            return create(collection, lineNumber);
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
        public PsiRandomExpression create(@NotNull PsiElement<?> collection, int lineNumber) {
            return new PsiRandomExpression(collection, lineNumber);
        }
    }
}
