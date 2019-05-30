package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Shows an action bar to a player.
 *
 * @since 0.1.0
 */
public class PsiActionBarEffect extends PsiElement<Void> {

    /**
     * The player to send the action bar to
     */
    @NotNull
    protected final PsiElement<?> player;

    /**
     * The text for the action bar
     */
    @NotNull
    protected final PsiElement<?> text;

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to send the action bar to
     * @param text the text for the action bar
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiActionBarEffect(@NotNull PsiElement<?> player, @NotNull PsiElement<?> text, int lineNumber) {
        super(lineNumber);

        this.player = player;
        this.text = text;
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
     * A factory for creating {@link PsiActionBarEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiActionBarEffect> {

        /**
         * A pattern to match {@link PsiActionBarEffect}s
         */
        @NotNull
        private final Pattern pattern =
            Pattern.compile("send (?:the )?action bar (?:with text )?(?<text>[\\s\\S]+) to (?<player>[\\s\\S]+)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Contract(pure = true)
        @Override
        public PsiActionBarEffect tryParse(@NotNull String text, int lineNumber) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches()) {
                return null;
            }

            var skriptLoader = SkriptLoader.get();

            PsiElement<?> textElement = skriptLoader.forceParseElement(matcher.group("text"), lineNumber);
            PsiElement<?> player = skriptLoader.forceParseElement(matcher.group("player"), lineNumber);

            return create(player, textElement, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param player the player to send the action bar to
         * @param text the text for the action bar
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiActionBarEffect create(@NotNull PsiElement<?> player, @NotNull PsiElement<?> text, int lineNumber) {
            return new PsiActionBarEffect(player, text, lineNumber);
        }
    }
}
