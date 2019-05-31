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
 * Hides a player from the server list
 *
 * @since 0.1.0
 */
public class PsiHidePlayerFromServerListEffect extends PsiElement<Void> {

    /**
     * The player toh ide from the server list
     */
    @NotNull
    protected PsiElement<?> player;

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to hide from the server list, see {@link #player}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiHidePlayerFromServerListEffect(@NotNull PsiElement<?> player, int lineNumber) {
        super(lineNumber);

        this.player = player;
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
     * A factory for creating {@link PsiHidePlayerFromServerListEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiHidePlayerFromServerListEffect> {

        /**
         * The pattern for matching {@link PsiHidePlayerFromServerListEffect}s with
         */
        @NotNull
        private final Pattern pattern =
            Pattern.compile("hide (<player>.+?)(?:'s? info(?:rmation)?)? (?:in|on|from) (?:the)? server list");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiHidePlayerFromServerListEffect tryParse(@NotNull String text, int lineNumber) {
            var matcher = pattern.matcher(text);

            if (!matcher.matches()) {
                return null;
            }

            PsiElement<?> player = SkriptLoader.get().forceParseElement(matcher.group("player"), lineNumber);

            return create(player, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param player the player to hide from the server list
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiHidePlayerFromServerListEffect create(@NotNull PsiElement<?> player, int lineNumber) {
            return new PsiHidePlayerFromServerListEffect(player, lineNumber);
        }
    }
}
