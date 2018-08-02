package com.github.stefvanschie.quickskript.psi.literal;

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
    @Override
    protected TextMessage executeImpl() {
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
         * A pattern for matching color codes
         */
        private final static Pattern COLOR_PATTERN = Pattern.compile("[&" + ChatColor.COLOR_CHAR + "]([0-9a-fk-or])");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiStringLiteral parse(@NotNull String text) {
            TextMessage message = new TextMessage();

            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            text = matcher.group(1);

            Matcher colorMatcher = COLOR_PATTERN.matcher(text);

            while (colorMatcher.find()) {
                char code = colorMatcher.group(1).charAt(0);

                message.addPart(new TextString(text.substring(0, colorMatcher.start())));
                message.addPart(new TextFormat(code));
                message.addPart(new TextString(text.substring(colorMatcher.end())));
            }

            return new PsiStringLiteral(message);
        }
    }
}
