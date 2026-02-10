package com.github.stefvanschie.quickskript.spigot.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsInteractableCondition;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.literal.ItemType;
import com.github.stefvanschie.quickskript.spigot.util.ItemTypeUtil;
import org.bukkit.Material;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks whether the provided item types can be interacted with.
 *
 * @since 0.1.0
 */
public class PsiIsInteractableConditionImpl extends PsiIsInteractableCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param itemTypes the item types to check if they can be interacted with
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsInteractableConditionImpl(@NotNull PsiElement<?> itemTypes, boolean positive, int lineNumber) {
        super(itemTypes, positive, lineNumber);
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return super.positive == super.itemTypes.executeMulti(environment, context, ItemType.class)
            .map(ItemTypeUtil::convertToMaterial)
            .test(Material::isInteractable);
    }

    /**
     * A factory for creating {@link PsiIsInteractableConditionImpl}s.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsInteractableCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiIsInteractableCondition create(@NotNull PsiElement<?> itemTypes, boolean positive, int lineNumber) {
            return new PsiIsInteractableConditionImpl(itemTypes, positive, lineNumber);
        }
    }
}
