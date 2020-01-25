package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.util.literal.Enchantment;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

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
            text = text.toUpperCase(Locale.getDefault());
            Enchantment.Type enchantment = null;

            for (Enchantment.Type type : Enchantment.Type.values()) {
                String name = type.name().replace('_', ' ').toUpperCase(Locale.getDefault());

                if (text.startsWith(name)) {
                    text = text.substring(name.length());
                    enchantment = type;
                    break;
                }
            }

            if (enchantment == null) {
                return null;
            }

            if (text.isBlank()) {
                return create(enchantment, null, lineNumber);
            }

            try {
                return create(enchantment, Integer.parseUnsignedInt(text.trim()), lineNumber);
            } catch (NumberFormatException ignored) {
                return null;
            }
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param type the enchantment type
         * @param level the level or null if there's no level
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiEnchantmentLiteral create(@NotNull Enchantment.Type type, @Nullable Integer level, int lineNumber) {
            return new PsiEnchantmentLiteral(new Enchantment(type, level), lineNumber);
        }
    }
}
