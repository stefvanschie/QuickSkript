package com.github.stefvanschie.quickskript.bukkit.psi.effect;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.effect.PsiFlyEffect;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Makes the player start/stop flying
 *
 * @since 0.1.0
 */
public class PsiFlyEffectImpl extends PsiFlyEffect {

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to start/stop flight for, see {@link #player}
     * @param enable true if the player will start flight, otherwise stop flight, see {@link #enable}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiFlyEffectImpl(@NotNull PsiElement<?> player, boolean enable, int lineNumber) {
        super(player, enable, lineNumber);
    }

    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        Player player = this.player.execute(context, Player.class);

        player.setAllowFlight(enable);
        player.setFlying(enable);

        return null;
    }

    /**
     * A factory for creating {@link PsiFlyEffectImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiFlyEffect.Factory {

        @NotNull
        @Override
        public PsiFlyEffect create(@NotNull PsiElement<?> player, boolean enable, int lineNumber) {
            return new PsiFlyEffectImpl(player, enable, lineNumber);
        }
    }
}
