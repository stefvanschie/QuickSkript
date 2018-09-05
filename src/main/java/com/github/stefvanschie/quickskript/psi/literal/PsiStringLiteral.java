package com.github.stefvanschie.quickskript.psi.literal;

import com.github.stefvanschie.quickskript.psi.PsiConverter;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.util.TextMessage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A psi element which holds text messages. This is always pre computed.
 *
 * @since 0.1.0
 */
public class PsiStringLiteral extends PsiPrecomputedHolder<TextMessage> {

    /**
     * Creates a new string literal from the given message
     *
     * @param message the message this psi is wrapping
     * @since 0.1.0
     */
    private PsiStringLiteral(@NotNull TextMessage message) {
        super(message);
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
        public PsiStringLiteral tryParse(@NotNull String text) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches())
                return null;

            return new PsiStringLiteral(TextMessage.parse(matcher.group(1)));
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
        public PsiStringLiteral convert(@NotNull Object object) {
            return new PsiStringLiteral(TextMessage.parse(object.toString()));
        }
    }
}
