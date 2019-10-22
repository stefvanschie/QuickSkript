package com.github.stefvanschie.quickskript.bukkit.psi.effect;

import com.github.stefvanschie.quickskript.bukkit.plugin.QuickSkript;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.effect.PsiPlayerVisibilityEffect;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;

/**
 * Hides/Shows player to/from other players
 *
 * @since 0.1.0
 */
public class PsiPlayerVisibilityEffectImpl extends PsiPlayerVisibilityEffect {

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to hide/show, see {@link #player}
     * @param target the player to hide/show the {@link #player} for, see {@link #target}
     * @param show true if the player should be shown, otherwise hidden, see {@link #show}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiPlayerVisibilityEffectImpl(@NotNull PsiElement<?> player, @Nullable PsiElement<?> target, boolean show,
                                            int lineNumber) {
        super(player, target, show, lineNumber);
    }

    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        Collection<? extends Player> targets = target == null
                ? Bukkit.getOnlinePlayers()
                : Collections.singleton(target.execute(context, Player.class));

        Player player = this.player.execute(context, Player.class);

        targets.forEach(target -> {
            if (show) {
                target.showPlayer(QuickSkript.getInstance(), player);
            } else {
                target.hidePlayer(QuickSkript.getInstance(), player);
            }
        });

        return null;
    }

    /**
     * A factory for creating {@link PsiPlayerVisibilityEffectImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiPlayerVisibilityEffect.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiPlayerVisibilityEffect create(@NotNull PsiElement<?> player, @Nullable PsiElement<?> target,
                                                boolean show, int lineNumber) {
            return new PsiPlayerVisibilityEffectImpl(player, target, show, lineNumber);
        }
    }
}
