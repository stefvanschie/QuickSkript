package com.github.stefvanschie.quickskript.psi.type;

import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiFactory;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A type that returns the global command sender object. This element is always pre computed.
 *
 * @since 0.1.0
 */
public class PsiConsoleSenderType extends PsiElement<ConsoleCommandSender> {

    /**
     * Creates a new psi console sender type
     *
     * @since 0.1.0
     */
    private PsiConsoleSenderType() {
        preComputed = Bukkit.getConsoleSender();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ConsoleCommandSender executeImpl() {
        throw new AssertionError("Since this preComputed variable is always set, this method should never get called");
    }

    /**
     * A factory for creating console sender types
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiFactory<PsiConsoleSenderType> {

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiConsoleSenderType parse(@NotNull String text) {
            if (!text.equalsIgnoreCase("the console"))
                return null;

            return new PsiConsoleSenderType();
        }
    }
}
