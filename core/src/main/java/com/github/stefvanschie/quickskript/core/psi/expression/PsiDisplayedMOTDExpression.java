package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.expression.util.Deletable;
import com.github.stefvanschie.quickskript.core.psi.expression.util.Resettable;
import com.github.stefvanschie.quickskript.core.psi.expression.util.Settable;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * The MOTD that is being displayed. This cannot be pre-computed, since this may change during gameplay.
 *
 * @since 0.1.0
 */
public class PsiDisplayedMOTDExpression extends PsiElement<Text> implements Deletable, Resettable, Settable {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiDisplayedMOTDExpression(int lineNumber) {
        super(lineNumber);
    }

    /**
     * A factory for creating {@link PsiDisplayedMOTDExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern used for matching {@link PsiDisplayedMOTDExpression}s
         */
        @NotNull
        private final SkriptPattern pattern =
            SkriptPattern.parse("[the] (shown|displayed) (MOTD|message of [the] day)");

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
        public PsiDisplayedMOTDExpression parse(int lineNumber) {
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
        public PsiDisplayedMOTDExpression create(int lineNumber) {
            return new PsiDisplayedMOTDExpression(lineNumber);
        }
    }
}
