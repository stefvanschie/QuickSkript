package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.expression.util.*;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Gets the passengers of an entity
 *
 * @since 0.1.0
 */
public class PsiPassengersExpression extends PsiElement<List<?>> implements Addable, Deletable, Removable,
    RemoveAllable, Resettable, Settable {

    /**
     * The entity to get the passengers of
     */
    @NotNull
    protected PsiElement<?> entity;

    /**
     * Creates a new element with the given line number
     *
     * @param entity the entity to get the passengers of
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiPassengersExpression(@NotNull PsiElement<?> entity, int lineNumber) {
        super(lineNumber);

        this.entity = entity;
    }

    /**
     * A factory for creating {@link PsiPassengersExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiPassengersExpression}s
         */
        @NotNull
        private SkriptPattern[] patterns = SkriptPattern.parse(
            "[the] passenger[s] of %entities%",
            "%entities%'[s] passenger[s]"
        );

        /**
         * Parses the {@link #patterns} and invokes this method with its types if the match succeeds
         *
         * @param entity the entity to get the passengers of
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("patterns")
        public PsiPassengersExpression parse(@NotNull PsiElement<?> entity, int lineNumber) {
            return create(entity, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param entity the entity to get the passengers of
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiPassengersExpression create(@NotNull PsiElement<?> entity, int lineNumber) {
            return new PsiPassengersExpression(entity, lineNumber);
        }
    }
}
