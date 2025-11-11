package com.github.stefvanschie.quickskript.paper.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsFishhookInOpenWaterCondition;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.MultiResult;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.entity.FishHook;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the fishing hooks are in open water.
 *
 * @since 0.1.0
 */
public class PsiIsFishhookInOpenWaterConditionImpl extends PsiIsFishhookInOpenWaterCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param fishingHooks the fishing hooks to check if they are in open water
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsFishhookInOpenWaterConditionImpl(
        @NotNull PsiElement<?> fishingHooks,
        boolean positive,
        int lineNumber
    ) {
        super(fishingHooks, positive, lineNumber);
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        MultiResult<? extends FishHook> fishingHooks = super.fishingHooks.executeMulti(
            environment, context, FishHook.class
        );

        return super.positive == fishingHooks.test(FishHook::isInOpenWater);
    }

    /**
     * A factory for creating {@link PsiIsFishhookInOpenWaterConditionImpl}s.
     *
     * @since 0.1.0
     */
    public static final class Factory extends PsiIsFishhookInOpenWaterCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        @Override
        public PsiIsFishhookInOpenWaterCondition create(
            @NotNull PsiElement<?> fishingHooks,
            boolean positive,
            int lineNumber
        ) {
            return new PsiIsFishhookInOpenWaterConditionImpl(fishingHooks, positive, lineNumber);
        }
    }
}
