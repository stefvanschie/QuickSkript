package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.expression.util.Addable;
import com.github.stefvanschie.quickskript.core.psi.expression.util.RemoveAllable;
import com.github.stefvanschie.quickskript.core.psi.expression.util.Settable;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Gets the amount of experience dropped when listening to an experience orb spawn event.
 *
 * @since 0.1.0
 */
public class PsiExperienceExpression extends PsiElement<Integer> implements Addable, RemoveAllable,
    Settable {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiExperienceExpression(int lineNumber) {
        super(lineNumber);
    }

    /**
     * A factory for creating {@link PsiExperienceExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiExperienceExpression}s
         */
        @NotNull
        private final SkriptPattern pattern = SkriptPattern.parse("[the] [(spawned|dropped)] [e]xp[erience] [orb[s]]");

        /**
         * Parses the {@link #pattern} and invokes this method with its types if the match succeeds
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("pattern")
        public PsiExperienceExpression parse(int lineNumber) {
            return create(lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiExperienceExpression create(int lineNumber) {
            return new PsiExperienceExpression(lineNumber);
        }
    }
}
