package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Sends a message to a command sender or player. This effect will never be pre computed.
 *
 * @since 0.1.0
 */
public class PsiMessageEffect extends PsiElement<Void> {

    /**
     * The message to be send to the {@link #receiver}
     */
    @NotNull
    protected final PsiElement<?> message;

    /**
     * The receiver of the {@link #message}. This may be null.
     */
    @Nullable
    protected final PsiElement<?> receiver;

    /**
     * Creates a new message effect.
     *
     * @param message the message to be send
     * @param receiver the receiver to receive the message
     * @param lineNumber the line number
     * @since 0.1.0
     */
    protected PsiMessageEffect(@NotNull PsiElement<?> message, @Nullable PsiElement<?> receiver, int lineNumber) {
        super(lineNumber);

        this.message = message;
        this.receiver = receiver;
    }

    /**
     * A factory for creating message effects
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern to match {@link PsiMessageEffect}s
         */
        @SuppressWarnings("HardcodedFileSeparator")
        @NotNull
        private final SkriptPattern pattern =
            SkriptPattern.parse("(message|send [message[s]]) %texts% [to %players/console%]").greedy(false);

        /**
         * Parses the {@link #pattern} and invokes this method with its types if the match succeeds
         *
         * @param message the message to send
         * @param receiver the player or console which should receive this message
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("pattern")
        public PsiMessageEffect tryParse(@NotNull PsiElement<?> message, @Nullable PsiElement<?> receiver,
                                         int lineNumber) {
            return create(message, receiver, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param message the message to be sent
         * @param receiver the receiver of the message
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        protected PsiMessageEffect create(@NotNull PsiElement<?> message, @Nullable PsiElement<?> receiver,
                                          int lineNumber) {
            return new PsiMessageEffect(message, receiver, lineNumber);
        }
    }
}
