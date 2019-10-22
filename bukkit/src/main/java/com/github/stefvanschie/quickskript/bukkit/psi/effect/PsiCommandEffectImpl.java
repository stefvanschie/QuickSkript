package com.github.stefvanschie.quickskript.bukkit.psi.effect;

import com.github.stefvanschie.quickskript.bukkit.context.ContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.effect.PsiCommandEffect;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Executes a command.
 *
 * @since 0.1.0
 */
public class PsiCommandEffectImpl extends PsiCommandEffect {

    /**
     * Creates a new element with the given line number
     *
     * @param commandName   the name of the command to execute
     * @param commandSender the command sender to execute the command
     * @param lineNumber    the line number this element is associated with
     * @since 0.1.0
     */
    private PsiCommandEffectImpl(@Nullable PsiElement<?> commandSender, @NotNull PsiElement<?> commandName,
            int lineNumber) {
        super(commandSender, commandName, lineNumber);
    }

    @Nullable
    @Contract(value = "_ -> null", pure = true)
    @Override
    protected Void executeImpl(@Nullable Context context) {
        CommandSender sender = null;

        if (commandSender != null) {
            sender = commandSender.execute(context, CommandSender.class);
        }

        if (sender == null && context != null) {
            sender = ((ContextImpl) context).getCommandSender();
        }

        if (sender == null) {
            sender = Bukkit.getConsoleSender();
        }

        Bukkit.dispatchCommand(sender, commandName.execute(context, Text.class).toString());

        return null;
    }

    /**
     * A factory for creating {@link PsiCommandEffectImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiCommandEffect.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiCommandEffect create(@Nullable PsiElement<?> commandSender, @NotNull PsiElement<?> commandName,
                int lineNumber) {
            return new PsiCommandEffectImpl(commandSender, commandName, lineNumber);
        }
    }
}
