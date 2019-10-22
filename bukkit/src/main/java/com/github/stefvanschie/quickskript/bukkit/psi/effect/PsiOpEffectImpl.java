package com.github.stefvanschie.quickskript.bukkit.psi.effect;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.effect.PsiOpEffect;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An effect for opping/de-opping a player
 *
 * @since 0.1.0
 */
public class PsiOpEffectImpl extends PsiOpEffect {

    /**
     * Creates a new element with the given line number
     *
     * @param offlinePlayer the offline player to change operator status
     * @param op            true to op, false to de-op
     * @param lineNumber    the line number this element is associated with
     * @since 0.1.0
     */
    private PsiOpEffectImpl(@NotNull PsiElement<?> offlinePlayer, boolean op, int lineNumber) {
        super(offlinePlayer, op, lineNumber);
    }

    @Nullable
    @Contract(pure = true)
    @Override
    protected Void executeImpl(@Nullable Context context) {
        offlinePlayer.execute(context, OfflinePlayer.class).setOp(op);

        return null;
    }

    /**
     * A class for creating {@link PsiOpEffectImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiOpEffect.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiOpEffect create(@NotNull PsiElement<?> offlinePlayer, boolean op, int lineNumber) {
            return new PsiOpEffectImpl(offlinePlayer, op, lineNumber);
        }
    }
}
