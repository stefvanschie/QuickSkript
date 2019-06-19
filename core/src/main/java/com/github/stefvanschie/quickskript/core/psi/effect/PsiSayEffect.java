package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Makes the player send a message in chat
 *
 * @since 0.1.0
 */
public class PsiSayEffect extends PsiElement<Void> {

    /**
     * The player to send the message for
     */
    @NotNull
    protected PsiElement<?> player;

    /**
     * The message to send
     */
    @NotNull
    protected PsiElement<?> text;

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to send the message for, see {@link #player}
     * @param text the text to send, see {@link #text}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiSayEffect(@NotNull PsiElement<?> player, @NotNull PsiElement<?> text, int lineNumber) {
        super(lineNumber);

        this.player = player;
        this.text = text;
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
     * A factory for creating {@link PsiSayEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiSayEffect> {

        /**
         * The patterns to match against
         */
        @NotNull
        private final Set<Pattern> patterns = Stream.of(
            "force (?<player>.+) to (?:say|send(?: the)? messages?) (?<text>.+)",
            "make (?<player>.+) (?:say|send(?: the)? messages?) (?<text>.+)"
        ).map(Pattern::compile).collect(Collectors.toUnmodifiableSet());

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiSayEffect tryParse(@NotNull String text, int lineNumber) {
            var optionalMatcher = patterns.stream()
                .map(pattern -> pattern.matcher(text))
                .filter(Matcher::matches)
                .findAny();

            if (optionalMatcher.isEmpty()) {
                return null;
            }

            var skriptLoader = SkriptLoader.get();
            var matcher = optionalMatcher.get();

            PsiElement<?> player = skriptLoader.forceParseElement(matcher.group("player"), lineNumber);
            PsiElement<?> textElement = skriptLoader.forceParseElement(matcher.group("text"), lineNumber);

            return create(player, textElement, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param player the player to send the message for
         * @param text the text to send
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiSayEffect create(@NotNull PsiElement<?> player, @NotNull PsiElement<?> text, int lineNumber) {
            return new PsiSayEffect(player, text, lineNumber);
        }
    }
}
