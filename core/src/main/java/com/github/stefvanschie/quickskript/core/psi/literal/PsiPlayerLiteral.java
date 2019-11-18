package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Returns the player from the given context. Can never be pre computed since it relies completely on context.
 *
 * @since 0.1.0
 */
public class PsiPlayerLiteral extends PsiElement<Object> {

    protected PsiPlayerLiteral(int lineNumber) {
        super(lineNumber);
    }

    /**
     * A factory for creating player literals
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern to parse player literals with
         */
        @NotNull
        private final Pattern pattern = Pattern.compile("(?:the )?player");

        /**
         * This gets called upon parsing
         *
         * @param text the text to parse
         * @param lineNumber the line number
         * @return the function, or null to indicate failure
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Fallback
        public PsiPlayerLiteral tryParse(@NotNull String text, int lineNumber) {
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
         * @return the literal
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        protected PsiPlayerLiteral create(int lineNumber) {
            return new PsiPlayerLiteral(lineNumber);
        }
    }
}
