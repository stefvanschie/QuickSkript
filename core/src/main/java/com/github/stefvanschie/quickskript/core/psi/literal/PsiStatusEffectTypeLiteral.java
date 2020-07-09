package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.util.literal.StatusEffectType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Retrieves a potion effect type
 *
 * @since 0.1.0
 */
public class PsiStatusEffectTypeLiteral extends PsiPrecomputedHolder<StatusEffectType> {

    /**
     * Creates a new element with the given line number
     *
     * @param statusEffectType the status effect type this element represents
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiStatusEffectTypeLiteral(@NotNull StatusEffectType statusEffectType, int lineNumber) {
        super(statusEffectType, lineNumber);
    }

    /**
     * A factory for creating {@link PsiStatusEffectTypeLiteral}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * This gets called upon parsing
         *
         * @param text the text to match
         * @param lineNumber the line number
         * @return the expression or null if the text couldn't be matched
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Fallback
        public PsiStatusEffectTypeLiteral parse(@NotNull String text, int lineNumber) {
            for (StatusEffectType statusEffectType : StatusEffectType.values()) {
                if (statusEffectType.getName().equals(text)) {
                    return create(statusEffectType, lineNumber);
                }
            }

            return null;
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param statusEffectType the status effect type that will be used for constructing the element
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiStatusEffectTypeLiteral create(@NotNull StatusEffectType statusEffectType, int lineNumber) {
            return new PsiStatusEffectTypeLiteral(statusEffectType, lineNumber);
        }
    }
}
