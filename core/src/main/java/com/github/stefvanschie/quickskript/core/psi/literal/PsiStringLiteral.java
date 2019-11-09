package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.psi.PsiConverter;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import org.jetbrains.annotations.Contract;
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
    private PsiStringLiteral(@NotNull Text message, int lineNumber) {
        super(message, lineNumber);
    }

    /**
     * A factory for creating {@link PsiStringLiteral}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * A pattern for matching strings. Making the plus lazy is crucial to ensure this doesn't accidentally match
         * multiple strings as one.
         */
        @NotNull
        private final Pattern pattern = Pattern.compile("\"(?<text>[^\"]+)\"");

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
        public PsiStringLiteral tryParse(@NotNull String text, int lineNumber) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches()) {
                return null;
            }

            return create(Text.parse(matcher.group("text")), lineNumber);
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
        @Contract(pure = true)
        protected PsiStringLiteral create(@NotNull Text text, int lineNumber) {
            return new PsiStringLiteral(text, lineNumber);
        }
    }

    /**
     * A converter to convert types to a psi string literal
     *
     * @since 0.1.0
     */
    public static class Converter implements PsiConverter<PsiStringLiteral> {

        @Nullable
        @Contract(pure = true)
        @Override
        public PsiStringLiteral convert(@NotNull Object object, int lineNumber) {
            return new PsiStringLiteral(Text.parse(object.toString()), lineNumber);
        }
    }
}
