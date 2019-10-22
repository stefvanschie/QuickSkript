package com.github.stefvanschie.quickskript.bukkit.psi.effect;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.effect.PsiFeedEffect;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Feeds the player by a specified amount of hunger bars.
 *
 * @since 0.1.0
 */
public class PsiFeedEffectImpl extends PsiFeedEffect {

    /**
     * Creates a new element with the given line number
     *
     * @param player     the player to whose hunger should be restored, see {@link #player}
     * @param amount     the amount of hunger to restore, or null, see {@link #amount}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiFeedEffectImpl(@NotNull PsiElement<?> player, @Nullable PsiElement<?> amount, int lineNumber) {
        super(player, amount, lineNumber);
    }

    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        Player player = this.player.execute(context, Player.class);

        if (amount == null) {
            player.setFoodLevel(20);
            return null;
        }

        Number amount = this.amount.execute(context, Number.class);

        player.setFoodLevel(player.getFoodLevel() + (int) amount.doubleValue());
        return null;
    }

    /**
     * A factory for creating {@link PsiFeedEffectImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiFeedEffect.Factory {

        @NotNull
        @Override
        public PsiFeedEffect create(@NotNull PsiElement<?> player, @Nullable PsiElement<?> amount, int lineNumber) {
            return new PsiFeedEffectImpl(player, amount, lineNumber);
        }
    }
}
