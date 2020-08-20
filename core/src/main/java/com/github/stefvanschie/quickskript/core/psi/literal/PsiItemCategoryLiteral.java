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
import com.github.stefvanschie.quickskript.core.util.literal.ItemCategory;
import com.github.stefvanschie.quickskript.core.util.registry.ItemTypeRegistry;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;

/**
 * Represents an item category. This may be pre-computed.
 *
 * @since 0.1.0
 */
public class PsiItemCategoryLiteral extends PsiElement<ItemCategory> {

    /**
     * The item category
     */
    private ItemCategory itemCategory;

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
     * @param itemCategory the item category this literal represents
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiItemCategoryLiteral(@NotNull ItemCategory itemCategory, @Nullable PsiElement<?> amount,
        @Nullable PsiElement<?> enchantment, int lineNumber) {
        super(lineNumber);

        this.itemCategory = itemCategory;
        this.amount = amount;
        this.enchantment = enchantment;

        if (amount != null && amount.isPreComputed() && enchantment != null && enchantment.isPreComputed()) {
            preComputed = executeImpl(null);

            this.itemCategory = null;
            this.amount = null;
            this.enchantment = null;
        }
    }

    @NotNull
    @Override
    protected ItemCategory executeImpl(@Nullable Context context) {
        if (amount != null) {
            itemCategory.setAmount(amount.execute(context, Number.class).intValue());
        }

        if (enchantment != null) {
            itemCategory.addEnchantment(enchantment.execute(context, Enchantment.class));
        }

        return itemCategory;
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
                throw new ParseException(new IllegalStateException("No regex found, did the skript pattern change?"),
                    lineNumber);
            }

            var itemTypes = new HashSet<ItemTypeRegistry.Entry>();

            for (ItemTypeRegistry.Entry entry : skriptLoader.getItemTypeRegistry().getEntries()) {
                outer:
                for (SkriptPattern category : entry.getCategories()) {
                    List<SkriptMatchResult> matches = category.match(pattern);

                    for (SkriptMatchResult match : matches) {
                        if (match.hasUnmatchedParts()) {
                            continue;
                        }

                        itemTypes.add(entry);
                        break outer;
                    }
                }
            }

            if (itemTypes.isEmpty()) {
                return null;
            }

            var itemCategory = new ItemCategory(itemTypes);

            if (result.getParseMark() == 1) {
                itemCategory.all();
            }

            return create(itemCategory, amount, enchantment, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param itemCategory the item category
         * @param amount the amount of items from the item category
         * @param enchantment the enchantment to apply to the items in the item category
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiItemCategoryLiteral create(@NotNull ItemCategory itemCategory, @Nullable PsiElement<?> amount,
            @Nullable PsiElement<?> enchantment, int lineNumber) {
            return new PsiItemCategoryLiteral(itemCategory, amount, enchantment, lineNumber);
        }
    }
}
