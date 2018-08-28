package com.github.stefvanschie.quickskript.psi.expression;

import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiFactory;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A type that returns the global command sender object. This element is always pre computed.
 *
 * @since 0.1.0
 */
public class PsiConsoleSenderExpression extends PsiElement<ConsoleCommandSender> {

    /**
     * Creates a new psi console sender type
     *
     * @since 0.1.0
     */
    private PsiConsoleSenderExpression() {
        preComputed = Bukkit.getConsoleSender();
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    protected ConsoleCommandSender executeImpl(@Nullable Context context) {
        throw new AssertionError("Since this preComputed variable is always set, this method should never get called");
    }

    /**
     * A factory for creating console sender types
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiFactory<PsiConsoleSenderExpression> {

        private Pattern PATTERN = Pattern.compile("(?:the )?(?:(?:console)|(?:server))");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiConsoleSenderExpression parse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);
            
            if (!matcher.matches())
                return null;

            return new PsiConsoleSenderExpression();
        }
    }
}
