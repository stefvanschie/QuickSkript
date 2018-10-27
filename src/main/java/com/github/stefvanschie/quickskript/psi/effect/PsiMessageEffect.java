package com.github.stefvanschie.quickskript.psi.effect;

import com.github.stefvanschie.quickskript.context.CommandContext;
import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.context.EventContext;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.util.Text;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerEvent;
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
    private final PsiElement<?> message;

    /**
     * The receiver of the {@link #message}. This may be null.
     */
    @Nullable
    private final PsiElement<?> receiver;

    /**
     * Creates a new message effect.
     *
     * @param message the message to be send
     * @param receiver the receiver to receive the message
     * @since 0.1.0
     */
    private PsiMessageEffect(@NotNull PsiElement<?> message, @Nullable PsiElement<?> receiver, int lineNumber) {
        super(lineNumber);

        this.message = message;
        this.receiver = receiver;
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        CommandSender receiver = null;

        if (this.receiver == null) {
            if (context instanceof CommandContext)
                receiver = ((CommandContext) context).getSender();
            else if (context instanceof EventContext) {
                Event event = ((EventContext) context).getEvent();

                if (event instanceof PlayerEvent)
                    receiver = ((PlayerEvent) event).getPlayer();
            }
        } else
            receiver = this.receiver.execute(context, CommandSender.class);

        if (receiver == null)
            throw new ExecutionException(
                "Unable to execute message instruction, since no possible receiver has been found", lineNumber
            );

        receiver.sendMessage(message.execute(context, Text.class).construct());
        return null;
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
        @Override
        public PsiMessageEffect tryParse(@NotNull String text, int lineNumber) {
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

            PsiElement<?> receiver = null;

            if (index != -1) {
                String to = text.substring(index + 4);

                text = text.substring(0, index);

                //find player or console
                receiver = SkriptLoader.get().forceParseElement(to, lineNumber);
            }

            PsiElement<?> message = SkriptLoader.get().forceParseElement(text, lineNumber);

            return new PsiMessageEffect(message, receiver, lineNumber);
        }
    }
}
