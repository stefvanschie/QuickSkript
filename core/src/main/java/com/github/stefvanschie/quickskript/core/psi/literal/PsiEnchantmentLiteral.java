package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.util.literal.Enchantment;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets an enchantment
 *
 * @since 0.1.0
 */
public class PsiEnchantmentLiteral extends PsiPrecomputedHolder<Enchantment> {

    /**
     * Creates a new element with the given line number
     *
     * @param enchantment the enchantment
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiEnchantmentLiteral(@NotNull Enchantment enchantment, int lineNumber) {
        super(enchantment, lineNumber);
    }

    /**
     * A factory for creating {@link PsiEnchantmentLiteral}s
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
        public PsiEnchantmentLiteral parse(@NotNull String text, int lineNumber) {
            for (Enchantment enchantment : Enchantment.values()) {
                if (enchantment.name().replace('_', ' ').equalsIgnoreCase(text)) {
                    return create(enchantment, lineNumber);
                }
            }

            return null;
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param enchantment the enchantment
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiEnchantmentLiteral create(@NotNull Enchantment enchantment, int lineNumber) {
            return new PsiEnchantmentLiteral(enchantment, lineNumber);
        }
    }
}
