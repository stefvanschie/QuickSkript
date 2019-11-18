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
 * Gets the gliding state of an entity
 *
 * @since 0.1.0
 */
public class PsiGlidingStateExpression extends PsiElement<Boolean> implements Resettable, Settable {

    /**
     * The living entity to get the gliding state from
     */
    @NotNull
    protected final PsiElement<?> livingEntity;

    /**
     * Creates a new element with the given line number
     *
     * @param livingEntity the living entity to get the gliding state from
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiGlidingStateExpression(@NotNull PsiElement<?> livingEntity, int lineNumber) {
        super(lineNumber);

        this.livingEntity = livingEntity;
    }

    /**
     * A factory for creating {@link PsiGlidingStateExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The patterns for creating {@link PsiGlidingStateExpression}s
         */
        @NotNull
        private final SkriptPattern[] patterns = SkriptPattern.parse(
            "[the] (gliding|glider) [state] of %entities%",
            "%entities%'[s] (gliding|glider) [state]"
        );

        /**
         * Parses the {@link #patterns} and invokes this method with its types if the match succeeds
         *
         * @param entity the entity to get the gliding state of
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("patterns")
        public PsiGlidingStateExpression parse(@NotNull PsiElement<?> entity, int lineNumber) {
            return create(entity, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param entity the entity to get the gliding state of
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiGlidingStateExpression create(@NotNull PsiElement<?> entity, int lineNumber) {
            return new PsiGlidingStateExpression(entity, lineNumber);
        }
    }
}
