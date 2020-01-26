package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.util.literal.Experience;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets experience
 *
 * @since 0.1.0
 */
public class PsiExperienceLiteral extends PsiPrecomputedHolder<Experience> {

    /**
     * Creates a new element with the given line number
     *
     * @param experience the experience
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiExperienceLiteral(@NotNull Experience experience, int lineNumber) {
        super(experience, lineNumber);
    }

    /**
     * A factory for creating {@link PsiExperienceLiteral}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Internal skript pattern used for matching
         */
        private final SkriptPattern pattern = SkriptPattern.parse(" ([e]xp|experience [point[s]])");

        /**
         * This gets called upon parsing
         *
         * @param text the text to parse
         * @param lineNumber the line number
         * @return the literal, or null to indicate failure
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Fallback
        public PsiExperienceLiteral parse(@NotNull String text, int lineNumber) {
            int index = text.indexOf(' ');

            if (index == -1) {
                return null;
            }

            int amount;

            try {
                amount = Integer.parseUnsignedInt(text.substring(0, index));
            } catch (NumberFormatException ignored) {
                return null;
            }

            if (pattern.match(text.substring(index)).stream().allMatch(SkriptMatchResult::hasUnmatchedParts)) {
                return null;
            }

            return create(new Experience(amount), lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param experience the experience
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiExperienceLiteral create(@NotNull Experience experience, int lineNumber) {
            return new PsiExperienceLiteral(experience, lineNumber);
        }
    }
}
