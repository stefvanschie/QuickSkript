package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Shows an action bar to a player.
 *
 * @since 0.1.0
 */
public class PsiActionBarEffect extends PsiElement<Void> {

    /**
     * The text for the action bar
     */
    @NotNull
    protected final PsiElement<?> text;

    /**
     * The player to send the action bar to
     */
    @NotNull
    protected final PsiElement<?> player;

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to send the action bar to
     * @param text the text for the action bar
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiActionBarEffect(@NotNull PsiElement<?> text, @NotNull PsiElement<?> player, int lineNumber) {
        super(lineNumber);

        this.text = text;
        this.player = player;
    }

    /**
     * A factory for creating {@link PsiActionBarEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * A pattern to match {@link PsiActionBarEffect}s
         */
        @NotNull
        private final SkriptPattern pattern =
            SkriptPattern.parse("send [the] action bar [with text] %text% to %players%");

        /**
         * Parses the {@link #pattern} and invokes this method with its types if the match succeeds
         *
         * @param text the text for the action bar
         * @param player the player to send the action bar to
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("pattern")
        public PsiActionBarEffect parse(@NotNull PsiElement<?> text, @NotNull PsiElement<?> player, int lineNumber) {
            return create(player, text, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param text the text for the action bar
         * @param player the player to send the action bar to
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiActionBarEffect create(@NotNull PsiElement<?> text, @NotNull PsiElement<?> player, int lineNumber) {
            return new PsiActionBarEffect(text, player, lineNumber);
        }
    }
}
