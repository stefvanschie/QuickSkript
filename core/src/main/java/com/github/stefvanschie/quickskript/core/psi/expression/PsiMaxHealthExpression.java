package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.expression.util.Addable;
import com.github.stefvanschie.quickskript.core.psi.expression.util.Removable;
import com.github.stefvanschie.quickskript.core.psi.expression.util.Resettable;
import com.github.stefvanschie.quickskript.core.psi.expression.util.Settable;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Gets the maximum amount of health a living entity may have. This cannot be pre-computed, since this may change during
 * gameplay.
 *
 * @since 0.1.0
 */
public class PsiMaxHealthExpression extends PsiElement<Double> implements Addable, Removable, Resettable, Settable {

    /**
     * The entity to get the maximum health of
     */
    @NotNull
    protected final PsiElement<?> livingEntity;

    /**
     * Creates a new element with the given line number
     *
     * @param livingEntity the entity to get the maximum health of, see {@link #livingEntity}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiMaxHealthExpression(@NotNull PsiElement<?> livingEntity, int lineNumber) {
        super(lineNumber);

        this.livingEntity = livingEntity;
    }

    /**
     * A factory for creating {@link PsiMaxHealthExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The patterns used for matching {@link PsiMaxHealthExpression}s
         */
        @NotNull
        private final SkriptPattern[] patterns = SkriptPattern.parse(
            "[the] max[imum] health of %living entities%",
            "%living entities%'[s] max[imum] health"
        );

        /**
         * Parses the {@link #patterns} and invokes this method with its types if the match succeeds
         *
         * @param livingEntity the entity to get the maximum health of
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("patterns")
        public PsiMaxHealthExpression parse(@NotNull PsiElement<?> livingEntity, int lineNumber) {
            return create(livingEntity, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param livingEntity the entity to get the maximum health of
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiMaxHealthExpression create(@NotNull PsiElement<?> livingEntity, int lineNumber) {
            return new PsiMaxHealthExpression(livingEntity, lineNumber);
        }
    }
}
