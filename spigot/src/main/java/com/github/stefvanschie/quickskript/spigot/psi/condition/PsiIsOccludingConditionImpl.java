package com.github.stefvanschie.quickskript.spigot.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsOccludingCondition;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.literal.ItemType;
import com.github.stefvanschie.quickskript.spigot.util.ItemTypeUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the item types are occluding.
 *
 * @since 0.1.0
 */
public class PsiIsOccludingConditionImpl extends PsiIsOccludingCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param itemTypes the item types to check if they are occluding
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsOccludingConditionImpl(@NotNull PsiElement<?> itemTypes, boolean positive, int lineNumber) {
        super(itemTypes, positive, lineNumber);
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return super.positive == super.itemTypes.executeMulti(environment, context, ItemType.class)
            .map(ItemTypeUtil::convertToMaterial)
            .test(material -> material != null && material.isOccluding());
    }

    /**
     * A factory to create instances of {@link PsiIsOccludingCondition}.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsOccludingCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        @Override
        public PsiIsOccludingCondition create(@NotNull PsiElement<?> itemTypes, boolean positive, int lineNumber) {
            return new PsiIsOccludingConditionImpl(itemTypes, positive, lineNumber);
        }
    }
}
