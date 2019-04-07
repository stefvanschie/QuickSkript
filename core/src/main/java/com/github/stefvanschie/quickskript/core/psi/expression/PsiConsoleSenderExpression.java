package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A type that returns the global command sender object. This element is always pre computed.
 *
 * @since 0.1.0
 */
public class PsiConsoleSenderExpression extends PsiElement<Object> {

    /**
     * Creates a new psi console sender type
     *
     * @param lineNumber the line number
     * @since 0.1.0
     */
    protected PsiConsoleSenderExpression(int lineNumber) {
        super(lineNumber);
    }

    /**
     * @throws UnsupportedOperationException implementation is required for this functionality
     */
    @Nullable
    @Override
    protected Object executeImpl(@Nullable Context context) {
        throw new UnsupportedOperationException("Cannot execute expression without implementation.");
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
        @NotNull
        private final Pattern pattern = Pattern.compile("(?:the )?(?:(?:console)|(?:server))");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Contract(pure = true)
        @Override
        public PsiConsoleSenderExpression tryParse(@NotNull String text, int lineNumber) {
            Matcher matcher = pattern.matcher(text);
            
            if (!matcher.matches()) {
                return null;
            }

            return create(lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        protected PsiConsoleSenderExpression create(int lineNumber) {
            return new PsiConsoleSenderExpression(lineNumber);
        }
    }
}
