package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
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
     * @throws UnsupportedOperationException implementation is required for this functionality
     */
    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        throw new UnsupportedOperationException("Cannot execute expression without implementation.");
    }

    /**
     * A factory for creating message effects
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiMessageEffect> {

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Contract(pure = true)
        @Override
        public PsiMessageEffect tryParse(@NotNull String text, int lineNumber) {
            if (!text.startsWith("message") && !text.startsWith("send"))
                return null;

            if (text.startsWith("message")) {
                text = text.substring(7).trim();
            } else if (text.startsWith("send")) {
                text = text.substring(4).trim();

                if (text.startsWith("message")) {
                    text = text.substring(7);

                    if (text.startsWith("s")) {
                        text = text.substring(1);
                    }

                    text = text.trim();
                }
            }

            int index = text.indexOf(" to ");

            PsiElement<?> receiver = null;

            if (index != -1) {
                String to = text.substring(index + 4);

                text = text.substring(0, index);

                //find player or console
                receiver = SkriptLoader.get().forceParseElement(to, lineNumber);
            }

            PsiElement<?> message = SkriptLoader.get().forceParseElement(text, lineNumber);

            return create(message, receiver, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
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
