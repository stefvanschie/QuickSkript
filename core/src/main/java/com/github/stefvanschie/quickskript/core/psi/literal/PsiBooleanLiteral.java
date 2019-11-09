package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

/**
 * A psi element which holds booleans. This is always pre computed.
 *
 * @since 0.1.0
 */
public class PsiBooleanLiteral extends PsiPrecomputedHolder<Boolean> {

    /**
     * Creates a new psi element which holds a precomputed value
     *
     * @param value      the value this psi is wrapping
     * @param lineNumber the line number of this element
     * @since 0.1.0
     */
    private PsiBooleanLiteral(boolean value, int lineNumber) {
        super(value, lineNumber);
    }

    /**
     * A factory for creating {@link PsiBooleanLiteral}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * A pattern for matching truthy values
         */
        @NotNull
        private final Pattern truePattern = Pattern.compile("(?:true)|(?:yes)|(?:on)");

        /**
         * A pattern for matching falsy values
         */
        @NotNull
        private final Pattern falsePattern = Pattern.compile("(?:false)|(?:no)|(?:off)");

        /**
         * This gets called upon parsing
         *
         * @param text the text to parse
         * @param lineNumber the line number
         * @return the function, or null to indicate failure
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Fallback
        public PsiBooleanLiteral tryParse(@NotNull String text, int lineNumber) {
            if (truePattern.matcher(text).matches()) {
                return create(true, lineNumber);
            } else if (falsePattern.matcher(text).matches()) {
                return create(false, lineNumber);
            }

            return null;
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param value the value of the literal
         * @param lineNumber the line number
         * @return the literal
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        protected PsiBooleanLiteral create(boolean value, int lineNumber) {
            return new PsiBooleanLiteral(value, lineNumber);
        }
    }
}
