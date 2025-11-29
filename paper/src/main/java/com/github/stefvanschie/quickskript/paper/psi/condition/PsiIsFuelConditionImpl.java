package com.github.stefvanschie.quickskript.paper.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsFuelCondition;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.literal.ItemType;
import com.github.stefvanschie.quickskript.paper.util.ItemTypeUtil;
import org.bukkit.Material;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks whether the item types are fuel.
 *
 * @since 0.1.0
 */
public class PsiIsFuelConditionImpl extends PsiIsFuelCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param itemTypes the item types to check if they are fuel
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsFuelConditionImpl(@NotNull PsiElement<?> itemTypes, boolean positive, int lineNumber) {
        super(itemTypes, positive, lineNumber);

        if (itemTypes.isPreComputed()) {
            preComputed = executeImpl(null, null);

            super.itemTypes = null;
        }
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return super.positive == super.itemTypes.executeMulti(environment, context, ItemType.class)
            .map(ItemTypeUtil::convertToMaterial)
            .test(Material::isFuel);
    }

    /**
     * A factory for creating {@link PsiIsFuelConditionImpl}s.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsFuelCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        @Override
        public PsiIsFuelCondition create(@NotNull PsiElement<?> itemTypes, boolean positive, int lineNumber) {
            return new PsiIsFuelConditionImpl(itemTypes, positive, lineNumber);
        }
    }
}
