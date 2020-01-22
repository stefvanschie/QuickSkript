package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.util.literal.DamageCause;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets a damage cause
 *
 * @since 0.1.0
 */
public class PsiDamageCauseLiteral extends PsiPrecomputedHolder<DamageCause> {

    /**
     * Creates a new element with the given line number
     *
     * @param damageCause the damage cause
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiDamageCauseLiteral(@NotNull DamageCause damageCause, int lineNumber) {
        super(damageCause, lineNumber);
    }

    /**
     * A factory for creating {@link PsiDamageCauseLiteral}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

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
        public PsiDamageCauseLiteral parse(@NotNull String text, int lineNumber) {
            for (DamageCause damageCause : DamageCause.values()) {
                if (damageCause.getName().equalsIgnoreCase(text)) {
                    return create(damageCause, lineNumber);
                }
            }

            return null;
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param damageCause the damage cause
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiDamageCauseLiteral create(@NotNull DamageCause damageCause, int lineNumber) {
            return new PsiDamageCauseLiteral(damageCause, lineNumber);
        }
    }
}
