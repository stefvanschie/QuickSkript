package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.pattern.group.RegexGroup;
import com.github.stefvanschie.quickskript.core.pattern.group.SkriptPatternGroup;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.util.Pair;
import com.github.stefvanschie.quickskript.core.util.Type;
import com.github.stefvanschie.quickskript.core.util.literal.Enchantment;
import com.github.stefvanschie.quickskript.core.util.literal.ItemType;
import com.github.stefvanschie.quickskript.core.util.registry.ItemTypeRegistry;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Represents an item category. This may be pre-computed.
 *
 * @since 0.1.0
 */
public class PsiItemCategoryLiteral extends PsiElement<ItemType> {

    /**
     * The item category
     */
    private ItemType itemType;

    /**
     * The amount of each item in this item category
     */
    private PsiElement<?> amount;

    /**
     * The enchantment of the items in this item category
     */
    private PsiElement<?> enchantment;

    /**
     * Creates a new element with the given line number
     *
     * @param itemType the item category this literal represents
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiItemCategoryLiteral(@NotNull ItemType itemType, @Nullable PsiElement<?> amount,
        @Nullable PsiElement<?> enchantment, int lineNumber) {
        super(lineNumber);

        this.itemType = itemType;
        this.amount = amount;
        this.enchantment = enchantment;

        if (amount != null && amount.isPreComputed() && enchantment != null && enchantment.isPreComputed()) {
            preComputed = executeImpl(null, null);

            this.itemType = null;
            this.amount = null;
            this.enchantment = null;
        }
    }

    @NotNull
    @Override
    protected ItemType executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        if (amount != null) {
            itemType.setAmount(amount.execute(environment, context, Number.class).intValue());
        }

        if (enchantment != null) {
            itemType.addEnchantment(enchantment.execute(environment, context, Enchantment.class));
        }

        return itemType;
    }

    /**
     * A factory for creating {@link PsiItemCategoryLiteral}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiItemCategoryLiteral}s
         */
        @NotNull
        private final SkriptPattern pattern =
            SkriptPattern.parse("[%number% [of]] [1\u00A6(all|every)] <.+> [of %enchantment%]");

        /**
         * Parses the {@link #pattern} and invokes this method with its types if the match succeeds
         *
         * @param skriptLoader the skript loader to parse with
         * @param result the result of matching the pattern
         * @param amount the amount of this category
         * @param enchantment the enchantment added to this category
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Pattern("pattern")
        public PsiItemCategoryLiteral parse(@NotNull SkriptLoader skriptLoader, @NotNull SkriptMatchResult result,
            @Nullable PsiElement<?> amount, @Nullable PsiElement<?> enchantment, int lineNumber) {
            String pattern = null;

            for (Pair<SkriptPatternGroup, String> matchedGroup : result.getMatchedGroups()) {
                if (!(matchedGroup.getX() instanceof RegexGroup)) {
                    continue;
                }

                pattern = matchedGroup.getY();
            }

            if (pattern == null) {
                throw new IllegalStateException("No regex found, did the skript pattern change?");
            }

            Set<ItemTypeRegistry.Entry> itemTypes = skriptLoader.getItemTypeRegistry().getEntriesByCategory(pattern);

            if (itemTypes == null || itemTypes.isEmpty()) {
                ItemTypeRegistry.Entry entry = skriptLoader.getItemTypeRegistry().getEntryByName(pattern);

                if (entry == null) {
                    return null;
                }

                itemTypes = Collections.singleton(entry);
            }

            var itemCategory = new ItemType(itemTypes);

            if (result.getParseMark() == 1) {
                itemCategory.all();
            }

            return create(itemCategory, amount, enchantment, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param itemType the item category
         * @param amount the amount of items from the item category
         * @param enchantment the enchantment to apply to the items in the item category
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiItemCategoryLiteral create(@NotNull ItemType itemType, @Nullable PsiElement<?> amount,
            @Nullable PsiElement<?> enchantment, int lineNumber) {
            return new PsiItemCategoryLiteral(itemType, amount, enchantment, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.ITEM_TYPES;
        }
    }
}
