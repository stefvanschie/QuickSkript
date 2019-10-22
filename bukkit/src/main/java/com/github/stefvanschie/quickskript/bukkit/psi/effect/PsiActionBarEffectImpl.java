package com.github.stefvanschie.quickskript.bukkit.psi.effect;

import com.github.stefvanschie.quickskript.bukkit.util.Platform;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.effect.PsiActionBarEffect;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Shows an action bar to a player.
 *
 * @since 0.1.0
 */
public class PsiActionBarEffectImpl extends PsiActionBarEffect {

    /**
     * Creates a new element with the given line number
     *
     * @param player     the player to send the action bar to
     * @param text       the text for the action bar
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiActionBarEffectImpl(@NotNull PsiElement<?> player, @NotNull PsiElement<?> text, int lineNumber) {
        super(player, text, lineNumber);
    }

    @Nullable
    @Contract(value = "_ -> null", pure = true)
    @SuppressWarnings("deprecation")
    @Override
    protected Void executeImpl(@Nullable Context context) {
        Platform platform = Platform.getPlatform();
        Player player = this.player.execute(context, Player.class);
        String text = this.text.execute(context, Text.class).toString();

        if (platform.isAvailable(Platform.PAPER)) {
            player.sendActionBar(text);
        } else if (platform.isAvailable(Platform.SPIGOT)) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(text));
        }

        return null;
    }

    /**
     * A factory for creating {@link PsiActionBarEffectImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiActionBarEffect.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiActionBarEffect create(@NotNull PsiElement<?> player, @NotNull PsiElement<?> text, int lineNumber) {
            return new PsiActionBarEffectImpl(player, text, lineNumber);
        }
    }
}
