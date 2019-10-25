package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.exception.ParseException;
import com.github.stefvanschie.quickskript.core.psi.util.PsiCollection;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;

/**
 * Gets a certain element from a collection of elements. This cannot be pre computed, since the {@link #indexFunction}
 * may return a different index for the same amount of items.
 *
 * @since 0.1.0
 */
public class PsiElementOfExpression extends PsiElement<Object> {

    /**
     * Gives the index of the element we should retrieve, based on the size of the collection
     */
    private BiFunction<@NotNull Integer, @Nullable Context, @NotNull Integer> indexFunction;

    /**
     * The elements to choose from
     */
    private PsiElement<?> elements;

    /**
     * Creates a new element with the given line number
     *
     * @param indexFunction supplies the index based on the collection's size
     * @param indexPreComputed whether the indexFunction's value is precomputed
     * @param elements the elements to pick from
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiElementOfExpression(
            @NotNull BiFunction<@NotNull Integer, @Nullable Context, @NotNull Integer> indexFunction,
            boolean indexPreComputed,
            @NotNull PsiElement<?> elements, int lineNumber) {
        super(lineNumber);

        this.indexFunction = indexFunction;
        this.elements = elements;

        if (indexPreComputed && elements.isPreComputed()) {
            preComputed = executeImpl(null);
            this.indexFunction = null;
            this.elements = null;
        }
    }

    @Nullable
    @Contract(pure = true)
    @Override
    protected Object executeImpl(@Nullable Context context) {
        Object object = this.elements.execute(context);

        if (object == null) {
            throw new ExecutionException("Can't get element from nothing", lineNumber);
        }

        List<Object> elements = new ArrayList<>();
        PsiCollection.forEach(object, elements::add, e -> {
            throw new ExecutionException("Can't get element from non-collection", lineNumber);
        });

        int index = indexFunction.apply(elements.size(), context);

        if (index < 0 || index >= elements.size()) {
            return null;
        }

        return elements.get(index);
    }

    /**
     * A factory for creating {@link PsiElementOfExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiElementOfExpression}s
         */
        @NotNull
        private final SkriptPattern pattern = SkriptPattern.parse(
                "(0\u00A6[the] first|1\u00A6[the] last|2\u00A6[a] random|3\u00A6%number%(st|nd|rd|th)) element [out] of %objects%"
        );

        /**
         * Parses the {@link #pattern} and invokes this method with its types if the match succeeds
         *
         * @param index the index to get from the elements if specified
         * @param elements the elements to get one from
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("pattern")
        public PsiElementOfExpression parse(@NotNull SkriptMatchResult result, @Nullable PsiElement<?> index,
                @NotNull PsiElement<?> elements, int lineNumber) {
            int parseMark = result.getParseMark();
            BiFunction<@NotNull Integer, @Nullable Context, @Nullable Integer> indexFunction;

            if (parseMark == 0) {
                return create((size, context) -> 0, true, elements, lineNumber);
            } else if (parseMark == 1) {
                return create((size, context) -> size - 1, true, elements, lineNumber);
            } else if (parseMark == 2) {
                return create((size, context) -> ThreadLocalRandom.current().nextInt(size), false, elements, lineNumber);
            } else if (parseMark == 3) {
                Objects.requireNonNull(index);
                return create((size, context) -> index.execute(context, Number.class).intValue(),
                        index.isPreComputed(), elements, lineNumber);
            } else {
                throw new ParseException("Unknown parse mark found while parsing", lineNumber);
            }
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param indexFunction a {@link BiFunction} for generating the wanted index
         * @param indexPreComputed whether the indexFunction's value is precomputed
         * @param elements the elements tog et an element from
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiElementOfExpression create(
                @NotNull BiFunction<@NotNull Integer, @Nullable Context, @NotNull Integer> indexFunction,
                boolean indexPreComputed,
                @NotNull PsiElement<?> elements, int lineNumber) {
            return new PsiElementOfExpression(indexFunction, indexPreComputed, elements, lineNumber);
        }
    }
}