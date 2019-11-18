package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Makes the player send a message in chat
 *
 * @since 0.1.0
 */
public class PsiSayEffect extends PsiElement<Void> {

    /**
     * The player to send the message for
     */
    @NotNull
    protected final PsiElement<?> player;

    /**
     * The message to send
     */
    @NotNull
    protected final PsiElement<?> text;

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to send the message for, see {@link #player}
     * @param text the text to send, see {@link #text}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiSayEffect(@NotNull PsiElement<?> player, @NotNull PsiElement<?> text, int lineNumber) {
        super(lineNumber);

        this.player = player;
        this.text = text;
    }

    /**
     * A factory for creating {@link PsiSayEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The patterns to match against
         */
        @NotNull
        private final SkriptPattern[] patterns = SkriptPattern.parse(
            "make %players% (say|send [the] message[s]) %texts%",
            "force %players% to (say|send [the] message[s]) %texts%"
        );

        /**
         * Parses the {@link #patterns} and invokes this method with its types if the match succeeds
         *
         * @param player the player to send the message for
         * @param text the text to send
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("patterns")
        public PsiSayEffect parse(@NotNull PsiElement<?> player, @NotNull PsiElement<?> text, int lineNumber) {
            return create(player, text, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param player the player to send the message for
         * @param text the text to send
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiSayEffect create(@NotNull PsiElement<?> player, @NotNull PsiElement<?> text, int lineNumber) {
            return new PsiSayEffect(player, text, lineNumber);
        }
    }
}
