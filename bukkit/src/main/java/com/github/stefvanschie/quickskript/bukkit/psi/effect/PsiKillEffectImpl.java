package com.github.stefvanschie.quickskript.bukkit.psi.effect;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.effect.PsiKillEffect;
import org.bukkit.entity.Damageable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Kills the entity
 *
 * @since 0.1.0
 */
public class PsiKillEffectImpl extends PsiKillEffect {

    /**
     * Creates a new element with the given line number
     *
     * @param entity     the entity to kill, see {@link #entity}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiKillEffectImpl(@NotNull PsiElement<?> entity, int lineNumber) {
        super(entity, lineNumber);
    }

    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        entity.execute(context, Damageable.class).setHealth(0);

        return null;
    }

    /**
     * A factory for creating {@link PsiKillEffectImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiKillEffect.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiKillEffect create(@NotNull PsiElement<?> entity, int lineNumber) {
            return new PsiKillEffectImpl(entity, lineNumber);
        }
    }
}
