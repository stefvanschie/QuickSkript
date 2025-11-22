package com.github.stefvanschie.quickskript.spigot.psi.condition;

import com.github.stefvanschie.quickskript.spigot.util.ItemTypeUtil;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsBlockCondition;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.literal.ItemType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the item types are blocks.
 *
 * @since 0.1.0
 */
public class PsiIsBlockConditionImpl extends PsiIsBlockCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param itemTypes the item types to check if they are blocks
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsBlockConditionImpl(@NotNull PsiElement<?> itemTypes, boolean positive, int lineNumber) {
        super(itemTypes, positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return super.positive == this.itemTypes.executeMulti(environment, context, ItemType.class)
            .map(ItemTypeUtil::convertToMaterial)
            .test(material -> {
                if (material == null) {
                    return false;
                }

                return material.isBlock();
            });
    }

    /**
     * A factory for creating {@link PsiIsBlockConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsBlockCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiIsBlockCondition create(@NotNull PsiElement<?> itemTypes, boolean positive, int lineNumber) {
            return new PsiIsBlockConditionImpl(itemTypes, positive, lineNumber);
        }
    }
}
