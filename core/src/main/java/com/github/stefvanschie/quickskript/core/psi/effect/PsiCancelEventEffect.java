package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A psi element for cancelling events
 *
 * @since 0.1.0
 */
public class PsiCancelEventEffect extends PsiElement<Void> {

    protected PsiCancelEventEffect(int lineNumber) {
        super(lineNumber);
    }

    /**
     * A factory for creating psi cancel event effects
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * A pattern for matching psi cancel event effects
         */
        @NotNull
        private final SkriptPattern pattern = SkriptPattern.parse("cancel [the] event");

        /**
         * Parses the {@link #pattern} and invokes this method with its types if the match succeeds
         *
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("pattern")
        public PsiCancelEventEffect parse(int lineNumber) {
            return create(lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param lineNumber the line number of this effect
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        protected PsiCancelEventEffect create(int lineNumber) {
            return new PsiCancelEventEffect(lineNumber);
        }
    }
}
