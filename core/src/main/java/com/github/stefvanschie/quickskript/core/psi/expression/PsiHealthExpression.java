package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.expression.util.*;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Gets the amount of health a damageable has. This cannot be pore computed, since this may change during game play.
 *
 * @since 0.1.0
 */
public class PsiHealthExpression extends PsiElement<Double> implements Addable, Deletable, Removable, Resettable,
    Settable {

    /**
     * The damageable to get the health from
     */
    @NotNull
    protected final PsiElement<?> damageable;

    /**
     * Creates a new element with the given line number
     *
     * @param damageable the damageable to get the health from, see {@link #damageable}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiHealthExpression(@NotNull PsiElement<?> damageable, int lineNumber) {
        super(lineNumber);

        this.damageable = damageable;
    }

    /**
     * A factory for creating {@link PsiHealthExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The patterns for matching {@link PsiHealthExpression}s
         */
        @NotNull
        private final SkriptPattern[] patterns = SkriptPattern.parse(
            "[the] health of %living entities%",
            "%living entities%'[s] health"
        );

        /**
         * Parses the {@link #patterns} and invokes this method with its types if the match succeeds
         *
         * @param damageable the damageable to get the health from
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("patterns")
        public PsiHealthExpression parse(@NotNull PsiElement<?> damageable, int lineNumber) {
            return create(damageable, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param damageable the damageable to get the health from
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiHealthExpression create(@NotNull PsiElement<?> damageable, int lineNumber) {
            return new PsiHealthExpression(damageable, lineNumber);
        }
    }
}
