package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.expression.util.Resettable;
import com.github.stefvanschie.quickskript.core.psi.expression.util.Settable;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Gets whether an entity is glowing or not. This cannot be pre computed, since entities may change glowing state during
 * game play.
 *
 * @since 0.1.0
 */
public class PsiGlowingExpression extends PsiElement<Boolean> implements Resettable, Settable {

    /**
     * The entity to get the glowing state of
     */
    @NotNull
    protected final PsiElement<?> entity;

    /**
     * Creates a new element with the given line number
     *
     * @param entity the entity to get the glowing state of, see {@link #entity}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiGlowingExpression(@NotNull PsiElement<?> entity, int lineNumber) {
        super(lineNumber);

        this.entity = entity;
    }

    /**
     * A factory for creating {@link PsiGlowingExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The patterns for creating {@link PsiGlowingExpression}s
         */
        @NotNull
        private final SkriptPattern[] patterns = SkriptPattern.parse(
            "[the] glowing of %entities%",
            "%entities%'[s] glowing"
        );

        /**
         * Parses the {@link #patterns} and invokes this method with its types if the match succeeds
         *
         * @param entity the entity to get the glowing state of
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("patterns")
        public PsiGlowingExpression parse(@NotNull PsiElement<?> entity, int lineNumber) {
            return create(entity, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param entity the entity to get the glowing state of
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiGlowingExpression create(@NotNull PsiElement<?> entity, int lineNumber) {
            return new PsiGlowingExpression(entity, lineNumber);
        }
    }
}
