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
 * Closes the currently opened inventory for the human entity
 *
 * @since 0.1.0
 */
public class PsiCloseInventoryEffect extends PsiElement<Void> {

    /**
     * The human entity to close the inventory for
     */
    @NotNull
    protected PsiElement<?> humanEntity;

    /**
     * Creates a new element with the given line number
     *
     * @param humanEntity the human entity to close the inventory for, see {@link #humanEntity}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiCloseInventoryEffect(@NotNull PsiElement<?> humanEntity, int lineNumber) {
        super(lineNumber);

        this.humanEntity = humanEntity;
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
     * A factory for creating {@link PsiCloseInventoryEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiCloseInventoryEffect> {

        /**
         * The patterns to match against
         */
        @NotNull
        private final Set<Pattern> patterns = Stream.of(
            "close(?: the)? inventory(?: view)? (?:to|of|for) (?<player>.+)",
            "close (?<player>.+)'s? inventory(?: view)?"
        ).map(Pattern::compile).collect(Collectors.toUnmodifiableSet());

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiCloseInventoryEffect tryParse(@NotNull String text, int lineNumber) {
            var optionalMatcher = patterns.stream()
                .map(pattern -> pattern.matcher(text))
                .filter(Matcher::matches)
                .findAny();

            if (optionalMatcher.isEmpty()) {
                return null;
            }

            String playerGroup = optionalMatcher.get().group("player");

            return create(SkriptLoader.get().forceParseElement(playerGroup, lineNumber), lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param player the player to close the inventory for
         * @param lineNumber the line number of this effect
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiCloseInventoryEffect create(@NotNull PsiElement<?> player, int lineNumber) {
            return new PsiCloseInventoryEffect(player, lineNumber);
        }
    }
}
