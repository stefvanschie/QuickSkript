package com.github.stefvanschie.quickskript.bukkit.psi.effect;

import com.github.stefvanschie.quickskript.bukkit.context.ContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.effect.PsiMessageEffect;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.Objects;

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

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        CommandSender receiver = null;

        if (this.receiver == null && context != null) {
            receiver = ((ContextImpl) context).getCommandSender();
        } else if (this.receiver != null) {
            receiver = this.receiver.execute(context, CommandSender.class);
        }

        if (receiver == null) {
            throw new ExecutionException(
                "Unable to execute message instruction, since no possible receiver has been found", lineNumber
            );
        }

        Object object = Objects.requireNonNull(message.execute(context));

        if (object instanceof Iterable) {
            for (Object obj : (Iterable<?>) object) {
                receiver.sendMessage(obj.toString());
            }
        } else if (object instanceof Object[]) {
            for (Object obj : (Object[]) object) {
                receiver.sendMessage(obj.toString());
            }
        } else if (object.getClass().isArray()) {
            for (int i = 0; i < Array.getLength(object); ++i) {
                receiver.sendMessage(Array.get(object, i).toString());
            }
        } else {
            receiver.sendMessage(object.toString());
        }

        return null;
    }

    /**
     * A factory for creating message effects
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiMessageEffect.Factory {

        /**
         * {@inheritDoc}
         */
        @NotNull
        @Override
        public PsiMessageEffectImpl create(@NotNull PsiElement<?> message, @Nullable PsiElement<?> receiver,
                                           int lineNumber) {
            return new PsiMessageEffectImpl(message, receiver, lineNumber);
        }
    }
}
