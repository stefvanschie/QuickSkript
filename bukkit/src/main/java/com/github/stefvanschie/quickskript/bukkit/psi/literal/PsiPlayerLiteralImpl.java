package com.github.stefvanschie.quickskript.bukkit.psi.literal;

import com.github.stefvanschie.quickskript.bukkit.context.ContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.literal.PsiPlayerLiteral;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Returns the player from the given context. Can never be pre computed since it relies completely on context.
 *
 * @since 0.1.0
 */
public class PsiPlayerLiteralImpl extends PsiPlayerLiteral {

    private PsiPlayerLiteralImpl(int lineNumber) {
        super(lineNumber);
    }

    @NotNull
    @Override
    protected Player executeImpl(@Nullable Context context) {
        if (context == null) {
            throw new ExecutionException("Cannot find a player without a context", lineNumber);
        }

        CommandSender sender = ((ContextImpl) context).getCommandSender();

        if (!(sender instanceof Player)) {
            throw new ExecutionException("Command wasn't executed by a player", lineNumber);
        }

        return (Player) sender;
    }

    /**
     * A factory for creating player literals
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiPlayerLiteral.Factory {

        @NotNull
        @Override
        public PsiPlayerLiteralImpl create(int lineNumber) {
            return new PsiPlayerLiteralImpl(lineNumber);
        }
    }
}
