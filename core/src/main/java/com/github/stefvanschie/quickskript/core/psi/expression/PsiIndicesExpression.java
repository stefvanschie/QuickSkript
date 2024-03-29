package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.IntStream;

/**
 * Returns an array of the indices of a collection
 *
 * @since 0.1.0
 */
public class PsiIndicesExpression extends PsiElement<int[]> {

    /**
     * The collection to get the indices from
     */
    private PsiElement<?> collection;

    /**
     * Creates a new element with the given line number
     *
     * @param collection the collection to get the indices from
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIndicesExpression(@NotNull PsiElement<?> collection, int lineNumber) {
        super(lineNumber);

        this.collection = collection;

        if (collection.isPreComputed()) {
            preComputed = executeImpl(null, null);

            this.collection = null;
        }
    }

    @Contract(pure = true)
    @Override
    protected int @NotNull [] executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return IntStream.rangeClosed(1, this.collection.executeMulti(environment, context).getSize()).toArray();
    }

    /**
     * A factory for creating {@link PsiIndicesExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The patterns for matching {@link PsiIndicesExpression}s
         */
        @NotNull
        private final SkriptPattern[] patterns = SkriptPattern.parse(
            "[the] (indexes|indices) of %objects%",
            "(all of the|all the|all) (indices|indexes) of %objects%"
        );

        /**
         * Parses the {@link #patterns} and invokes this method with its types if the match succeeds
         *
         * @param collection the collection to get the indices from
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("patterns")
        public PsiIndicesExpression parse(@NotNull PsiElement<?> collection, int lineNumber) {
            return create(collection, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param collection the collection to get the indices from
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiIndicesExpression create(@NotNull PsiElement<?> collection, int lineNumber) {
            return new PsiIndicesExpression(collection, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.NUMBERS;
        }
    }
}
