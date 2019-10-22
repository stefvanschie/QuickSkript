package com.github.stefvanschie.quickskript.bukkit.psi.effect;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.effect.PsiKickEffect;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Kicks a player from the server.
 *
 * @since 0.1.0
 */
public class PsiKickEffectImpl extends PsiKickEffect {

    /**
     * Creates a new element with the given line number
     *
     * @param player     the player to kick, see {@link #player}
     * @param reason     the reason for kicking the {@link #player}, see {@link #reason}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiKickEffectImpl(@NotNull PsiElement<?> player, @Nullable PsiElement<?> reason, int lineNumber) {
        super(player, reason, lineNumber);
    }

    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        String reason = this.reason == null ? null : this.reason.execute(context, Text.class).toString();

        player.execute(context, Player.class).kickPlayer(reason);

        return null;
    }

    /**
     * A factory for creating {@link PsiKickEffectImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiKickEffect.Factory {

        @NotNull
        @Override
        public PsiKickEffect create(@NotNull PsiElement<?> player, @Nullable PsiElement<?> reason, int lineNumber) {
            return new PsiKickEffectImpl(player, reason, lineNumber);
        }
    }
}
