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
 * Resets a player's title and subtitle
 *
 * @since 0.1.0
 */
public class PsiResetTitleEffect extends PsiElement<Void> {

    /**
     * The player to reset the title and subtitle for
     */
    @Nullable
    protected PsiElement<?> player;

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to reset the title for, see {@link #player}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiResetTitleEffect(@Nullable PsiElement<?> player, int lineNumber) {
        super(lineNumber);

        this.player = player;
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
     * A factory to create {@link PsiResetTitleEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiResetTitleEffect> {

        /**
         * The patterns to match this element with
         */
        @NotNull
        private final Set<Pattern> patterns = Stream.of(
            "reset(?: the)? titles?(?: of (?<player>.+))?",
            "reset(?: the)? (?<player>.+)'s? titles?"
        ).map(Pattern::compile).collect(Collectors.toUnmodifiableSet());

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiResetTitleEffect tryParse(@NotNull String text, int lineNumber) {
            var optionalMatcher = patterns.stream()
                .map(pattern -> pattern.matcher(text))
                .filter(Matcher::matches)
                .findAny();

            if (optionalMatcher.isEmpty()) {
                return null;
            }

            var skriptLoader = SkriptLoader.get();

            String playerGroup = optionalMatcher.get().group("player");
            PsiElement<?> player = playerGroup == null ? null : skriptLoader.forceParseElement(playerGroup, lineNumber);

            return create(player, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param player the player to reset the title for
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiResetTitleEffect create(@Nullable PsiElement<?> player, int lineNumber) {
            return new PsiResetTitleEffect(player, lineNumber);
        }
    }
}
