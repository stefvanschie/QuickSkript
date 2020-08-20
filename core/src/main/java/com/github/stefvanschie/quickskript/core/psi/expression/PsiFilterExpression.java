package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.pattern.group.RegexGroup;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiCollection;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.util.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Filters a collection or array based on the given predicate.
 *
 * @since 0.1.0
 */
public class PsiFilterExpression extends PsiElement<Object> {

    /**
     * The collection to test against
     */
    private final PsiElement<?> collection;

    /**
     * The predicate to match all elements from {@link #collection} against
     */
    private final PsiElement<?> predicate;

    /**
     * The element that is currently being checked
     */
    @Nullable
    private Object currentLoopingElement;

    /**
     * Creates a new element with the given line number
     *
     * @param collection the collection of elements to filter
     * @param predicate the predicate to match the elements against
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiFilterExpression(@NotNull PsiElement<?> collection, @NotNull PsiElement<?> predicate, int lineNumber) {
        super(lineNumber);

        this.collection = collection;
        this.predicate = predicate;

        predicate.setParent(this);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Object executeImpl(@Nullable Context context) {
        Object object = collection.execute(context);

        List<Object> list = new ArrayList<>();
        PsiCollection.forEach(object, e -> {
            currentLoopingElement = e;
            if (predicate.execute(context, Boolean.class)) {
                list.add(e);
            }
        }, null);

        currentLoopingElement = null;
        return list;
    }

    /**
     * Gets the element we're currently testing
     *
     * @return the current element
     */
    @NotNull
    @Contract(pure = true)
    public Object getCurrentLoopingElement() {
        if (currentLoopingElement == null) {
            throw new IllegalStateException("Looping element can only be retrieved when this is being looped over");
        }

        return currentLoopingElement;
    }

    /**
     * A factory for creating {@link PsiFilterExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiFilterExpression}s
         */
        @SuppressWarnings("HardcodedFileSeparator")
        @NotNull
        private final SkriptPattern pattern = SkriptPattern.parse("%objects% (where|that match) \\[<.+>\\]");

        /**
         * Parses the {@link #pattern} and invokes this method with its types if the match succeeds
         *
         * @param skriptLoader the skript loader to parse with
         * @param result the pattern match result
         * @param objects the objects to filter
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("pattern")
        public PsiFilterExpression parse(@NotNull SkriptLoader skriptLoader, @NotNull SkriptMatchResult result,
            @NotNull PsiElement<?> objects, int lineNumber) {
            PsiElement<?> predicate = skriptLoader.forceParseElement(result.getMatchedGroups().stream()
                .filter(entry -> entry.getX() instanceof RegexGroup)
                .map(Pair::getY)
                .findAny()
                .orElseThrow(), lineNumber);

            return create(objects, predicate, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param objects to objects to filter
         * @param predicate the predicate to test the elements against
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiFilterExpression create(@NotNull PsiElement<?> objects, @NotNull PsiElement<?> predicate,
            int lineNumber) {
            return new PsiFilterExpression(objects, predicate, lineNumber);
        }
    }
}
