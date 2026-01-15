package com.github.stefvanschie.quickskript.paper.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsHandRaisedCondition;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.literal.Hand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

/**
 * Checks if living entities have their hands raised.
 *
 * @since 0.1.0
 */
public class PsiIsHandRaisedConditionImpl extends PsiIsHandRaisedCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param livingEntities the living entities to check if their hands are raised
     * @param hand the type of hand to check
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsHandRaisedConditionImpl(
        @NotNull PsiElement<?> livingEntities,
        @NotNull Hand hand,
        boolean positive,
        int lineNumber
    ) {
        super(livingEntities, hand, positive, lineNumber);
    }

    @NotNull
    @Override
    public Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        boolean isMainHand = super.hand == Hand.MAIN_HAND;

        return super.positive == super.livingEntities.executeMulti(environment, context, LivingEntity.class)
            .test(entity -> entity.hasActiveItem() && (entity.getActiveItemHand() == EquipmentSlot.HAND) == isMainHand);
    }

    /**
     * A factory for creating {@link PsiIsHandRaisedConditionImpl}s.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsHandRaisedCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _, _ -> new", pure = true)
        @Override
        public PsiIsHandRaisedCondition create(
            @NotNull PsiElement<?> livingEntities,
            @NotNull Hand hand,
            boolean positive,
            int lineNumber
        ) {
            return new PsiIsHandRaisedConditionImpl(livingEntities, hand, positive, lineNumber);
        }
    }
}
