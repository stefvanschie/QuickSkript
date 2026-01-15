package com.github.stefvanschie.quickskript.spigot.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsHoldingCondition;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.MultiResult;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.literal.Hand;
import com.github.stefvanschie.quickskript.core.util.literal.ItemType;
import com.github.stefvanschie.quickskript.spigot.util.ItemTypeUtil;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Check if living entities are holding a particular item in their hand.
 *
 * @since 0.1.0
 */
public class PsiIsHoldingConditionImpl extends PsiIsHoldingCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param livingEntities the living entities to check their item in hand, or null if unspecified
     * @param itemTypes the item types to check against
     * @param hand the hand to check the item of
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsHoldingConditionImpl(
        @Nullable PsiElement<?> livingEntities,
        @NotNull PsiElement<?> itemTypes,
        @NotNull Hand hand,
        boolean positive,
        int lineNumber
    ) {
        super(livingEntities, itemTypes, hand, positive, lineNumber);
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        if (super.livingEntities == null) {
            throw new ExecutionException("Living entities may not be null", super.lineNumber);
        }

        MultiResult<? extends ItemType> itemTypes = super.itemTypes.executeMulti(environment, context, ItemType.class);

        return super.positive == super.livingEntities.executeMulti(environment, context, LivingEntity.class)
            .map(LivingEntity::getEquipment)
            .map(equipment -> {
                if (super.hand == Hand.MAIN_HAND) {
                    return equipment.getItemInMainHand();
                } else if (super.hand == Hand.OFFHAND) {
                    return equipment.getItemInOffHand();
                } else {
                    throw new ExecutionException("Unknown hand " + super.hand, super.lineNumber);
                }})
            .test(item -> itemTypes.test(itemType -> ItemTypeUtil.equals(itemType, item)));
    }

    /**
     * A factory for creating {@link PsiIsHoldingConditionImpl}s.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsHoldingCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _, _, _ -> new", pure = true)
        @Override
        public PsiIsHoldingCondition create(
            @Nullable PsiElement<?> livingEntities,
            @NotNull PsiElement<?> itemTypes,
            @NotNull Hand hand,
            boolean positive,
            int lineNumber
        ) {
            return new PsiIsHoldingConditionImpl(livingEntities, itemTypes, hand, positive, lineNumber);
        }
    }
}
