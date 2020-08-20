package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.pattern.group.RegexGroup;
import com.github.stefvanschie.quickskript.core.pattern.group.SkriptPatternGroup;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.exception.ParseException;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.util.Pair;
import com.github.stefvanschie.quickskript.core.util.literal.Enchantment;
import com.github.stefvanschie.quickskript.core.util.literal.Item;
import com.github.stefvanschie.quickskript.core.util.registry.ItemTypeRegistry;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets an item
 *
 * @since 0.1.0
 */
public class PsiItemLiteral extends PsiElement<Item> {

    /**
     * The item
     */
    private ItemTypeRegistry.Entry item;

    /**
     * The amount of items, or 1 if null
     */
    @Nullable
    private PsiElement<?> amount;

    /**
     * The enchantment of the item, or null if the item isn't enchanted
     */
    @Nullable
    private PsiElement<?> enchantment;

    /**
     * Creates a new element with the given line number
     *
     * @param item the item
     * @param amount the amount of items or null if not specified
     * @param enchantment the enchantment on the item or null if not specified
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiItemLiteral(@NotNull ItemTypeRegistry.Entry item, @Nullable PsiElement<?> amount,
        @Nullable PsiElement<?> enchantment, int lineNumber) {
        super(lineNumber);

        this.item = item;
        this.amount = amount;
        this.enchantment = enchantment;

        if ((amount == null || amount.isPreComputed()) && (enchantment == null || enchantment.isPreComputed())) {
            preComputed = executeImpl(null);

            this.item = null;
            this.amount = null;
            this.enchantment = null;
        }
    }

    @Nullable
    @Override
    protected Item executeImpl(@Nullable Context context) {
        var item = new Item(this.item, amount == null ? 1 : amount.execute(context, Number.class).intValue());

        if (enchantment != null) {
            item.setEnchantment(enchantment.execute(context, Enchantment.class));
        }

        return item;
    }

    /**
     * A factory for creating {@link PsiItemLiteral}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching item literals
         */
        @NotNull
        private final SkriptPattern pattern = SkriptPattern.parse("[%number% [of]] <.+> [of %enchantment%]");

        /**
         * Parses the {@link #pattern} and invokes this method with its types if the match succeeds
         *
         * @param skriptLoader the skript loader
         * @param result the result of the pattern match
         * @param amount the amount of items or null if not specified
         * @param enchantment the enchantment on the item or null if not specified
         * @param lineNumber the line number
         * @return the literal, or null to indicate failure
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Pattern("pattern")
        public PsiItemLiteral parse(@NotNull SkriptLoader skriptLoader, @NotNull SkriptMatchResult result,
            @Nullable PsiElement<?> amount, @Nullable PsiElement<?> enchantment, int lineNumber) {
            for (Pair<SkriptPatternGroup, String> pair : result.getMatchedGroups()) {
                if (!(pair.getX() instanceof RegexGroup)) {
                    continue;
                }

                for (ItemTypeRegistry.Entry entry : skriptLoader.getItemTypeRegistry().getEntries()) {
                    for (SkriptMatchResult match : entry.getPattern().match(pair.getY())) {
                        if (match.hasUnmatchedParts()) {
                            continue;
                        }

                        return create(entry, amount, enchantment, lineNumber);
                    }
                }

                return null;
            }

            throw new ParseException("Unable to find item name", lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param item the item
         * @param amount the amount of items or null if not specified
         * @param enchantment the enchantment on the item or null if not specified
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiItemLiteral create(@NotNull ItemTypeRegistry.Entry item, PsiElement<?> amount,
            PsiElement<?> enchantment, int lineNumber) {
            return new PsiItemLiteral(item, amount, enchantment, lineNumber);
        }
    }
}
