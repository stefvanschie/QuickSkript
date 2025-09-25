package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Gets the entity who's currently leashing the entity. This cannot be pre-computed, since this may change during
 * gameplay.
 *
 * @since 0.1.0
 */
public class PsiLeashHolderExpression extends PsiElement<Object> {

    /**
     * The leashed entity to get the holder from
     */
    @NotNull
    protected final PsiElement<?> entity;

    /**
     * Creates a new element with the given line number
     *
     * @param entity the leashed entity to get the holder from
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiLeashHolderExpression(@NotNull PsiElement<?> entity, int lineNumber) {
        super(lineNumber);

        this.entity = entity;
    }

    /**
     * A factory for creating {@link PsiLeashHolderExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the patterns and invokes this method with its types if the match succeeds
         *
         * @param entity the leashed entity to get the holder from
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("[the] leash holder[s] of %living entities%")
        @Pattern("%living entities%'[s] leash holder[s]")
        public PsiLeashHolderExpression parse(@NotNull PsiElement<?> entity, int lineNumber) {
            return create(entity, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param entity the leashed entity to get the holder from
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiLeashHolderExpression create(@NotNull PsiElement<?> entity, int lineNumber) {
            return new PsiLeashHolderExpression(entity, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.OBJECT;
        }
    }
}
