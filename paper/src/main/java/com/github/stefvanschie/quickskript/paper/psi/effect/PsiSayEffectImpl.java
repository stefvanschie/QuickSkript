package com.github.stefvanschie.quickskript.paper.psi.effect;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.effect.PsiSayEffect;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Makes the player send a message in chat
 *
 * @since 0.1.0
 */
public class PsiSayEffectImpl extends PsiSayEffect {

    /**
     * Creates a new element with the given line number
     *
     * @param player     the player to send the message for, see {@link #player}
     * @param text       the text to send, see {@link #text}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiSayEffectImpl(@NotNull PsiElement<?> player, @NotNull PsiElement<?> text, int lineNumber) {
        super(player, text, lineNumber);
    }

    @Nullable
    @Override
    protected Void executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        player.execute(environment, context, Player.class).chat(text.execute(environment, context, String.class));

        return null;
    }

    /**
     * A factory for creating {@link PsiSayEffectImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiSayEffect.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiSayEffect create(@NotNull PsiElement<?> player, @NotNull PsiElement<?> text, int lineNumber) {
            return new PsiSayEffectImpl(player, text, lineNumber);
        }
    }
}
