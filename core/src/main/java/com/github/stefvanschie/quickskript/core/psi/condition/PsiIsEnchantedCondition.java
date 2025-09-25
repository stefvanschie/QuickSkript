package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.MultiResult;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.Type;
import com.github.stefvanschie.quickskript.core.util.literal.EnchantmentType;
import com.github.stefvanschie.quickskript.core.util.literal.ItemType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * Checks whether item types are enchanted.
 *
 * @since 0.1.0
 */
public class PsiIsEnchantedCondition extends PsiElement<Boolean> {

    /**
     * The item types to check if they are enchanted.
     */
    @NotNull
    private final PsiElement<?> itemTypes;

    /**
     * The enchantment types to check if they appear on the item types. If null, the item types may have any
     * enchantment.
     */
    @Nullable
    private final PsiElement<?> enchantmentTypes;

    /**
     * If false, the result is negated.
     */
    private final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param itemTypes the item types to check if they are enchanted
     * @param enchantmentTypes the enchantment types to check if they appear on the item types
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsEnchantedCondition(
        @NotNull PsiElement<?> itemTypes,
        @Nullable PsiElement<?> enchantmentTypes,
        boolean positive,
        int lineNumber
    ) {
        super(lineNumber);

        this.itemTypes = itemTypes;
        this.enchantmentTypes = enchantmentTypes;
        this.positive = positive;
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        MultiResult<? extends ItemType> itemTypes = this.itemTypes.executeMulti(environment, context, ItemType.class);

        if (this.enchantmentTypes == null) {
            return this.positive == itemTypes.test(itemType -> !itemType.getEnchantments().isEmpty());
        }

        MultiResult<? extends EnchantmentType> enchantmentTypes = this.enchantmentTypes.executeMulti(environment,
            context, EnchantmentType.class);

        return this.positive == itemTypes.test(itemType -> {
            Collection<? extends EnchantmentType> enchantments = itemType.getEnchantments();

            for (EnchantmentType enchantmentType : enchantmentTypes) {
                if (!enchantments.contains(enchantmentType)) {
                    return false;
                }
            }

            return true;
        });
    }

    /**
     * A factory for creating {@link PsiIsEnchantedCondition}s.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param itemTypes the item types to check if they are enchanted
         * @param enchantmentTypes the enchantment types to check if they appear on the item types
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%item types% (is|are) enchanted [with %enchantment type%]")
        public PsiIsEnchantedCondition parsePositive(
            @NotNull PsiElement<?> itemTypes,
            @Nullable PsiElement<?> enchantmentTypes,
            int lineNumber
        ) {
            return create(itemTypes, enchantmentTypes, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param itemTypes the item types to check if they are enchanted
         * @param enchantmentTypes the enchantment types to check if they appear on the item types
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%item types% (isn't|is not|aren't|are not) enchanted [with %enchantment type%]")
        public PsiIsEnchantedCondition parseNegative(
            @NotNull PsiElement<?> itemTypes,
            @Nullable PsiElement<?> enchantmentTypes,
            int lineNumber
        ) {
            return create(itemTypes, enchantmentTypes, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param itemTypes the item types to check if they are enchanted
         * @param enchantmentTypes the enchantment types to check if they appear on the item types
         * @param positive if false, the result is negated
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiIsEnchantedCondition create(
            @NotNull PsiElement<?> itemTypes,
            @Nullable PsiElement<?> enchantmentTypes,
            boolean positive,
            int lineNumber
        ) {
            return new PsiIsEnchantedCondition(itemTypes, enchantmentTypes, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
