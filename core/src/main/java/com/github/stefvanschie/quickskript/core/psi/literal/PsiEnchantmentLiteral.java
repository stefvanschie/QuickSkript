package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.pattern.group.RegexGroup;
import com.github.stefvanschie.quickskript.core.pattern.group.SkriptPatternGroup;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.util.Pair;
import com.github.stefvanschie.quickskript.core.util.Type;
import com.github.stefvanschie.quickskript.core.util.literal.Enchantment;
import com.github.stefvanschie.quickskript.core.util.literal.EnchantmentType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets an enchantment
 *
 * @since 0.1.0
 */
public class PsiEnchantmentLiteral extends PsiPrecomputedHolder<EnchantmentType> {

    /**
     * Creates a new element with the given line number
     *
     * @param enchantmentType the enchantment
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiEnchantmentLiteral(@NotNull EnchantmentType enchantmentType, int lineNumber) {
        super(enchantmentType, lineNumber);
    }

    /**
     * A factory for creating {@link PsiEnchantmentLiteral}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for parsing enchantments
         */
        @NotNull
        private final SkriptPattern pattern = SkriptPattern.parse("<.+> [<\\d+>]");

        /**
         * Called whenever a potential match is found for an enchantment literal
         *
         * @param loader the skript loader
         * @param result the match result
         * @param lineNumber the line number
         * @return the literal, or null to indicate failure
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Pattern("pattern")
        public PsiEnchantmentLiteral parse(
            @NotNull SkriptLoader loader,
            @NotNull SkriptMatchResult result,
            int lineNumber
        ) {
            String[] regexStrings = new String[2];

            for (Pair<SkriptPatternGroup, String> group : result.getMatchedGroups()) {
                if (!(group.getX() instanceof RegexGroup)) {
                    continue;
                }

                if (regexStrings[0] == null) {
                    regexStrings[0] = group.getY();
                } else {
                    regexStrings[1] = group.getY();

                    break;
                }
            }

            Enchantment enchantment = loader.getEnchantmentRegistry().byName(regexStrings[0].toLowerCase());

            if (enchantment == null) {
                return null;
            }

            if (regexStrings[1] == null) {
                return create(enchantment, null, lineNumber);
            } else {
                try {
                    return create(enchantment, Integer.parseUnsignedInt(regexStrings[1]), lineNumber);
                } catch (NumberFormatException ignored) {
                    return null;
                }
            }
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param enchantment the enchantment
         * @param level the level or null if there's no level
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiEnchantmentLiteral create(@NotNull Enchantment enchantment, @Nullable Integer level, int lineNumber) {
            return new PsiEnchantmentLiteral(new EnchantmentType(enchantment, level), lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.ENCHANTMENT;
        }
    }
}
