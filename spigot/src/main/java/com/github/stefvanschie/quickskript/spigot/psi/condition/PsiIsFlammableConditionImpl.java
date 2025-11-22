package com.github.stefvanschie.quickskript.spigot.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsFlammableCondition;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.literal.ItemType;
import com.github.stefvanschie.quickskript.spigot.util.ItemTypeUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the item types are a block and can catch fire.
 *
 * @since 0.1.0
 */
public class PsiIsFlammableConditionImpl extends PsiIsFlammableCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param itemTypes the item types to check if they are a block and can catch fire
     * @param positive if false, the result will be negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsFlammableConditionImpl(@NotNull PsiElement<?> itemTypes, boolean positive, int lineNumber) {
        super(itemTypes, positive, lineNumber);
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return super.positive == super.itemTypes.executeMulti(environment, context, ItemType.class)
            .map(ItemTypeUtil::convertToMaterial)
            .test(material -> {
                if (material == null) {
                    return false;
                }

                return material.isFlammable();
            });
    }

    /**
     * A factory for creating {@link PsiIsFlammableConditionImpl}s.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsFlammableCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        @Override
        public PsiIsFlammableCondition create(@NotNull PsiElement<?> itemTypes, boolean positive, int lineNumber) {
            return new PsiIsFlammableConditionImpl(itemTypes, positive, lineNumber);
        }
    }
}
