package com.github.stefvanschie.quickskript.spigot.psi.effect;

import com.github.stefvanschie.quickskript.spigot.context.ContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.effect.PsiMessageEffect;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Sends a message to a command sender or player. This effect will never be pre computed.
 *
 * @since 0.1.0
 */
public class PsiMessageEffectImpl extends PsiMessageEffect {
    /**
     * Creates a new message effect.
     *
     * @param message the message to be send
     * @param receiver the receiver to receive the message
     * @since 0.1.0
     */
    private PsiMessageEffectImpl(@NotNull PsiElement<?> message, @Nullable PsiElement<?> receiver, int lineNumber) {
        super(message, receiver, lineNumber);
    }

    @Nullable
    @Override
    protected Void executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        CommandSender receiver = null;

        if (this.receiver == null && context != null) {
            receiver = ((ContextImpl) context).getCommandSender();
        } else if (this.receiver != null) {
            receiver = this.receiver.execute(environment, context, CommandSender.class);
        }

        if (receiver == null) {
            throw new ExecutionException(
                "Unable to execute message instruction, since no possible receiver has been found", lineNumber
            );
        }

        CommandSender finalReceiver = receiver;
        message.executeMulti(environment, context).forEach(msg -> finalReceiver.sendMessage(msg.toString()));

        return null;
    }

    /**
     * A factory for creating message effects
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiMessageEffect.Factory {

        @NotNull
        @Override
        public PsiMessageEffectImpl create(@NotNull PsiElement<?> message, @Nullable PsiElement<?> receiver,
                                           int lineNumber) {
            return new PsiMessageEffectImpl(message, receiver, lineNumber);
        }
    }
}
