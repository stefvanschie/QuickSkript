package com.github.stefvanschie.quickskript.psi.effect;

import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.psi.PsiFactory;
import com.github.stefvanschie.quickskript.psi.exception.ParseException;
import com.github.stefvanschie.quickskript.util.TextMessage;
import org.bukkit.command.CommandSender;
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
    private final PsiElement<TextMessage> message;

    /**
     * The receiver of the {@link #message}. This may be null.
     */
    @Nullable
    private final PsiElement<CommandSender> receiver;

    /**
     * Creates a new message effect.
     *
     * @param message the message to be send
     * @param receiver the receiver to receive the message
     * @since 0.1.0
     */
    private PsiMessageEffect(@NotNull PsiElement<TextMessage> message, @Nullable PsiElement<CommandSender> receiver) {
        this.message = message;
        this.receiver = receiver;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Void executeImpl() {
        //TODO if the receiver is null, it should use a receiver based on the execution context
        receiver.execute().sendMessage(message.execute().construct());
        return null;
    }

    /**
     * A factory for creating message effects
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiFactory<PsiMessageEffect> {

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiMessageEffect parse(@NotNull String text) {
            if (!text.startsWith("message") && !text.startsWith("send"))
                return null;

            if (text.startsWith("message"))
                text = text.substring(7).trim();
            else if (text.startsWith("send")) {
                text = text.substring(4).trim();

                if (text.startsWith("message")) {
                    text = text.substring(7);

                    if (text.startsWith("s"))
                        text = text.substring(1);

                    text = text.trim();
                }
            }

            int index = text.indexOf(" to ");

            PsiElement<CommandSender> receiver = null;

            if (index != -1) {
                String to = text.substring(index + 4);

                text = text.substring(0, index - 1);

                //find player or console
                receiver = (PsiElement<CommandSender>) PsiElementFactory.parseText(to, CommandSender.class);

                if (receiver == null)
                    throw new ParseException("Effect was unable to find an expression named " + to);
            }

            PsiElement<TextMessage> message = (PsiElement<TextMessage>) PsiElementFactory.parseText(text,
                TextMessage.class);

            if (message == null)
                throw new ParseException("Effect was unable to find an expression named " + text);

            return new PsiMessageEffect(message, receiver);
        }
    }
}
