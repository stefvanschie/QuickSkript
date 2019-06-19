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
 * Makes the player start/stop flying
 *
 * @since 0.1.0
 */
public class PsiFlyEffect extends PsiElement<Void> {

    /**
     * The player to start/stop flight for
     */
    @NotNull
    protected PsiElement<?> player;

    /**
     * If true, the {@link #player} will start flying, otherwise they'll stop
     */
    protected boolean enable;

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to start/stop flight for, see {@link #player}
     * @param enable true if the player will start flight, otherwise stop flight, see {@link #enable}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiFlyEffect(@NotNull PsiElement<?> player, boolean enable, int lineNumber) {
        super(lineNumber);

        this.player = player;
        this.enable = enable;
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
     * A factory for creating {@link PsiFlyEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiFlyEffect> {

        /**
         * The patterns to match against
         */
        @NotNull
        private final Set<Pattern> patterns = Stream.of(
            "force (?<player>.+?) to(?: (?<enable>stop|start))? fly(?:ing)?",
            "make (?<player>.+?)(?: (?<enable>stop|start))? fly(?:ing)?"
        ).map(Pattern::compile).collect(Collectors.toUnmodifiableSet());

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiFlyEffect tryParse(@NotNull String text, int lineNumber) {
            var optionalMatcher = patterns.stream()
                .map(pattern -> pattern.matcher(text))
                .filter(Matcher::matches)
                .findAny();

            if (optionalMatcher.isEmpty()) {
                return null;
            }

            var matcher = optionalMatcher.get();
            String enable = matcher.group("enable");

            PsiElement<?> player = SkriptLoader.get().forceParseElement(matcher.group("player"), lineNumber);

            return create(player, enable == null || enable.equals("start"), lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param player the player to start/stop flight for
         * @param enable true if we start flight, otherwise stop flight
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiFlyEffect create(@NotNull PsiElement<?> player, boolean enable, int lineNumber) {
            return new PsiFlyEffect(player, enable, lineNumber);
        }
    }
}
