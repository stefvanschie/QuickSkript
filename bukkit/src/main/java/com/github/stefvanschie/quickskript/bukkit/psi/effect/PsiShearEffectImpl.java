package com.github.stefvanschie.quickskript.bukkit.psi.effect;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.effect.PsiShearEffect;
import org.bukkit.entity.Sheep;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Shears or grows back wool on a sheep
 *
 * @since 0.1.0
 */
public class PsiShearEffectImpl extends PsiShearEffect {

    /**
     * Creates a new element with the given line number
     *
     * @param sheep      the sheep to shear, see {@link #sheep}
     * @param shear      true if the {@link #sheep} will be sheared, otherwise wool will grow back on, see {@link
     *                   #shear}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiShearEffectImpl(@NotNull PsiElement<?> sheep, boolean shear, int lineNumber) {
        super(sheep, shear, lineNumber);
    }

    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        sheep.execute(context, Sheep.class).setSheared(shear);

        return null;
    }

    /**
     * A factory for creating {@link PsiShearEffectImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiShearEffect.Factory {

        @NotNull
        @Override
        public PsiShearEffect create(@NotNull PsiElement<?> sheep, boolean shear, int lineNumber) {
            return new PsiShearEffectImpl(sheep, shear, lineNumber);
        }
    }
}
