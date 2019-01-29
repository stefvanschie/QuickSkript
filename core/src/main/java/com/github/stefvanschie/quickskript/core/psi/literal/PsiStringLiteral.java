package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.psi.PsiConverter;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A psi element which holds text messages. This is always pre computed.
 *
 * @since 0.1.0
 */
public class PsiStringLiteral extends PsiPrecomputedHolder<Text> {

    /**
     * Creates a new string literal from the given message
     *
     * @param message the message this psi is wrapping
     * @param lineNumber the line number
     * @since 0.1.0
     */
    protected PsiStringLiteral(@NotNull Text message, int lineNumber) {
        super(message, lineNumber);
    }

    /**
     * A factory for creating {@link PsiStringLiteral}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiStringLiteral> {

        /**
         * A pattern for matching strings
         */
        private final Pattern pattern = Pattern.compile("\"([\\s\\S]+)\"");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiStringLiteral tryParse(@NotNull String text, int lineNumber) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches()) {
                return null;
            }

            return create(Text.parse(matcher.group(1)), lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param text the text for the literal
         * @param lineNumber the line number
         * @return the literal
         * @since 0.1.0
         */
        @NotNull
        protected PsiStringLiteral create(Text text, int lineNumber) {
            return new PsiStringLiteral(text, lineNumber);
        }
    }

    /**
     * A converter to convert types to a psi string literal
     *
     * @since 0.1.0
     */
    public static class Converter implements PsiConverter<PsiStringLiteral> {

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiStringLiteral convert(@NotNull Object object, int lineNumber) {
            return new PsiStringLiteral(Text.parse(object.toString()), lineNumber);
        }
    }
}
