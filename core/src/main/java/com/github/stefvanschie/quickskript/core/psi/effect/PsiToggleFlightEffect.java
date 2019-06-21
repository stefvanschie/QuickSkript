package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

/**
 * Enables or disables the ability to fly for a player
 *
 * @since 0.1.0
 */
public class PsiToggleFlightEffect extends PsiElement<Void> {

    /**
     * The player to toggle the flight state for
     */
    @NotNull
    protected PsiElement<?> player;

    /**
     * True if flight is allowed, otherwise it will be denied
     */
    protected boolean enabled;

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to toggle flight for
     * @param enabled true to enable flight, otherwise disable
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiToggleFlightEffect(@NotNull PsiElement<?> player, boolean enabled, int lineNumber) {
        super(lineNumber);

        this.player = player;
        this.enabled = enabled;
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
     * A factory for creating {@link PsiToggleFlightEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiToggleFlightEffect> {

        /**
         * The pattern for matching enable elements
         */
        @NotNull
        private final Pattern enablePattern =
            Pattern.compile("(?:allow|enable) (?:fly|flight) (?:for|to) (?<player>.+)");

        /**
         * The pattern for matching disable elements
         */
        @NotNull
        private final Pattern disablePattern =
            Pattern.compile("(?:disallow|disable) (?:fly|flight) (?:for|to) (?<player>.+)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiToggleFlightEffect tryParse(@NotNull String text, int lineNumber) {
            var skriptLoader = SkriptLoader.get();
            var enableMatcher = enablePattern.matcher(text);

            if (enableMatcher.matches()) {
                PsiElement<?> player = skriptLoader.forceParseElement(enableMatcher.group("player"), lineNumber);

                return create(player, true, lineNumber);
            }

            var disableMatcher = disablePattern.matcher(text);

            if (disableMatcher.matches()) {
                PsiElement<?> player = skriptLoader.forceParseElement(enableMatcher.group("player"), lineNumber);

                return create(player, false, lineNumber);
            }

            return null;
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param player the player to toggle flight for
         * @param enable true to enable flight, otherwise disable
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiToggleFlightEffect create(@NotNull PsiElement<?> player, boolean enable, int lineNumber) {
            return new PsiToggleFlightEffect(player, enable, lineNumber);
        }
    }
}
