package com.github.stefvanschie.quickskript.psi.literal;

import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.psi.PsiConverter;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiFactory;
import com.github.stefvanschie.quickskript.util.TextFormat;
import com.github.stefvanschie.quickskript.util.TextMessage;
import com.github.stefvanschie.quickskript.util.TextString;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A psi element which holds text messages. This is always pre computed.
 *
 * @since 0.1.0
 */
public class PsiStringLiteral extends PsiElement<TextMessage> {

    /**
     * Creates a new string literal from the given message
     *
     * @param message the message this psi is wrapping
     * @since 0.1.0
     */
    private PsiStringLiteral(@NotNull TextMessage message) {
        preComputed = message;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    protected TextMessage executeImpl(@Nullable Context context) {
        throw new AssertionError("Since this preComputed variable is always set, this method should never get called");
    }

    /**
     * A factory for creating {@link PsiStringLiteral}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiFactory<PsiStringLiteral> {

        /**
         * A pattern for matching strings
         */
        private final static Pattern PATTERN = Pattern.compile("\"([\\s\\S]+)\"");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiStringLiteral parse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

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
