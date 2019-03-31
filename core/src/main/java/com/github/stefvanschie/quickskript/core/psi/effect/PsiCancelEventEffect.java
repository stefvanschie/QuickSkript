package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A psi element for cancelling events
 *
 * @since 0.1.0
 */
public class PsiCancelEventEffect extends PsiElement<Void> {

    /**
     * {@inheritDoc}
     */
    protected PsiCancelEventEffect(int lineNumber) {
        super(lineNumber);
    }

    /**
     * @throws UnsupportedOperationException implementation is required for this functionality
     */
    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        throw new UnsupportedOperationException("Cannot execute expression without implementation.");
    }

    /**
     * A factory for creating psi cancel event effects
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiCancelEventEffect> {

        /**
         * A pattern for matching psi cancel event effects
         */
        @NotNull
        private final Pattern pattern = Pattern.compile("cancel (?:the )?event");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiCancelEventEffect tryParse(@NotNull String text, int lineNumber) {
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
         * @param lineNumber the line number of this effect
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        protected PsiCancelEventEffect create(int lineNumber) {
            return new PsiCancelEventEffect(lineNumber);
        }
    }
}
