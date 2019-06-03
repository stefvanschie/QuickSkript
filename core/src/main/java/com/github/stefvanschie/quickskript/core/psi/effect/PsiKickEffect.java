package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

/**
 * Kicks a player from the server.
 *
 * @since 0.1.0
 */
public class PsiKickEffect extends PsiElement<Void> {

    /**
     * The player to kick
     */
    @NotNull
    protected PsiElement<?> player;

    /**
     * The reason for kicking the {@link #player}, or null
     */
    @Nullable
    protected PsiElement<?> reason;

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to kick, see {@link #player}
     * @param reason the reason for kicking the {@link #player}, see {@link #reason}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiKickEffect(@NotNull PsiElement<?> player, @Nullable PsiElement<?> reason, int lineNumber) {
        super(lineNumber);

        this.player = player;
        this.reason = reason;
    }

    /**
     * @throws UnsupportedOperationException implementation is required for this functionality
     */
    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        throw new UnsupportedOperationException("Cannot execute expression without implementation.");
    }

    /**
     * A factory for creating {@link PsiKickEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiKickEffect> {

        /**
         * The pattern for matching {@link PsiKickEffect}s
         */
        @NotNull
        private final Pattern pattern = Pattern
            .compile("kick (?<player>.+?)(?: (?:by reason of|because(?: of)?|on account of|due to) (?<reason>.+?))?$");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiKickEffect tryParse(@NotNull String text, int lineNumber) {
            var matcher = pattern.matcher(text);

            if (!matcher.matches()) {
                return null;
            }

            var skriptLoader = SkriptLoader.get();

            PsiElement<?> player = skriptLoader.forceParseElement(matcher.group("player"), lineNumber);
            PsiElement<?> reason = null;

            if (matcher.groupCount() > 1) {
                reason = skriptLoader.forceParseElement(matcher.group("reason"), lineNumber);
            }

            return create(player, reason, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param player the player to kick
         * @param reason the reason for kicking the player, or null
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiKickEffect create(@NotNull PsiElement<?> player, @Nullable PsiElement<?> reason, int lineNumber) {
            return new PsiKickEffect(player, reason, lineNumber);
        }
    }
}
