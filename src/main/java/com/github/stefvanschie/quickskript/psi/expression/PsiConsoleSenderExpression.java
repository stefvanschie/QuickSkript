package com.github.stefvanschie.quickskript.psi.expression;

import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.psi.literal.PsiPrecomputedHolder;
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
public class PsiConsoleSenderExpression extends PsiPrecomputedHolder<ConsoleCommandSender> {

    /**
     * Creates a new psi console sender type
     *
     * @since 0.1.0
     */
    private PsiConsoleSenderExpression() {
        super(Bukkit.getConsoleSender());
    }

    /**
     * A factory for creating console sender types
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiConsoleSenderExpression> {

        /**
         * The pattern to parse console sender expressions with
         */
        private final Pattern pattern = Pattern.compile("(?:the )?(?:(?:console)|(?:server))");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiConsoleSenderExpression tryParse(@NotNull String text) {
            Matcher matcher = pattern.matcher(text);
            
            if (!matcher.matches())
                return null;

            return new PsiConsoleSenderExpression();
        }
    }
}
