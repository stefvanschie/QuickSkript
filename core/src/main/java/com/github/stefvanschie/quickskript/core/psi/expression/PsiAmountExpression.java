package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.Collection;

/**
 * The size of a collection or the length of an array
 *
 * @since 0.1.0
 */
public class PsiAmountExpression extends PsiElement<Number> {

    /**
     * The collection or array to get the size/length from
     */
    @NotNull
    private final PsiElement<?> collection;

    /**
     * Creates a new element with the given line number
     *
     * @param collection the collection to get the count from, see {@link #collection}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiAmountExpression(@NotNull PsiElement<?> collection, int lineNumber) {
        super(lineNumber);

        this.collection = collection;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Contract(pure = true)
    @Override
    protected Number executeImpl(@Nullable Context context) {
        Object object = collection.execute(context);

        if (object == null) {
            throw new ExecutionException("Object is null", lineNumber);
        }

        if (object instanceof Collection) {
            return ((Collection<?>) object).size();
        }

        if (object instanceof Object[]) {
            return ((Object[]) object).length;
        }

        if (object.getClass().isArray()) {
            return Array.getLength(object);
        }

        throw new ExecutionException("Element was not a collection or array", lineNumber);
    }

    /**
     * A factory for creating {@link PsiAmountExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiAmountExpression}s
         */
        @NotNull
        private final SkriptPattern pattern = SkriptPattern.parse("(amount|number|size) of %objects%");

        /**
         * This gets called upon parsing
         *
         * @param collection the collection to get the amount from
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("pattern")
        public PsiAmountExpression parse(@NotNull PsiElement<?> collection, int lineNumber) {
            return new PsiAmountExpression(collection, lineNumber);
        }
    }
}
