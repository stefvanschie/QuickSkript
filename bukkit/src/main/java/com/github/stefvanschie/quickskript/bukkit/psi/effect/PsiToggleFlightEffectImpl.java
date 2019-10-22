package com.github.stefvanschie.quickskript.bukkit.psi.effect;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.effect.PsiToggleFlightEffect;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Enables or disables the ability to fly for a player
 *
 * @since 0.1.0
 */
public class PsiToggleFlightEffectImpl extends PsiToggleFlightEffect {

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to toggle flight for
     * @param enabled true to enable flight, otherwise disable
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiToggleFlightEffectImpl(@NotNull PsiElement<?> player, boolean enabled, int lineNumber) {
        super(player, enabled, lineNumber);
    }

    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        player.execute(context, Player.class).setAllowFlight(enabled);

        return null;
    }

    /**
     * A factory for creating {@link PsiToggleFlightEffectImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiToggleFlightEffect.Factory {

        @NotNull
        @Override
        public PsiToggleFlightEffect create(@NotNull PsiElement<?> player, boolean enable, int lineNumber) {
            return new PsiToggleFlightEffectImpl(player, enable, lineNumber);
        }
    }
}
