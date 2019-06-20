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
 * Hides/Shows player to/from other players
 *
 * @since 0.1.0
 */
public class PsiPlayerVisibilityEffect extends PsiElement<Void> {

    /**
     * The player to hide/show
     */
    @NotNull
    protected PsiElement<?> player;

    /**
     * The player to hide/show {@link #player} to/for, or all online players if null
     */
    @Nullable
    protected PsiElement<?> target;

    /**
     * True if the {@link #player} will be shown, otherwise the {@link #player} will be hidden
     */
    protected boolean show;

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to hide/show, see {@link #player}
     * @param target the player to hide/show the {@link #player} for, see {@link #target}
     * @param show true if the player should be shown, otherwise hidden, see {@link #show}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiPlayerVisibilityEffect(@NotNull PsiElement<?> player, @Nullable PsiElement<?> target, boolean show,
                                        int lineNumber) {
        super(lineNumber);

        this.player = player;
        this.target = target;
        this.show = show;
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
     * A factory for creating {@link PsiPlayerVisibilityEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiPlayerVisibilityEffect> {

        /**
         * The pattern to match a showing element
         */
        @NotNull
        private final Pattern showPattern =
            Pattern.compile("reveal (?<player>.+?)(?: (?:to|for|from) (?<target>.+))?$");

        /**
         * The pattern to match a hiding element
         */
        @NotNull
        private final Pattern hidePattern = Pattern.compile("hide (?<player>.+?)(?: (?:for|from) (?<target>.+))?$");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiPlayerVisibilityEffect tryParse(@NotNull String text, int lineNumber) {
            var skriptLoader = SkriptLoader.get();

            var showMatcher = showPattern.matcher(text);

            if (showMatcher.matches()) {
                String targetGroup = showMatcher.group("target");

                PsiElement<?> player = skriptLoader.forceParseElement(showMatcher.group("player"), lineNumber);
                PsiElement<?> target = targetGroup == null
                    ? null
                    : skriptLoader.forceParseElement(targetGroup, lineNumber);

                return create(player, target, true, lineNumber);
            }

            var hideMatcher = hidePattern.matcher(text);

            if (hideMatcher.matches()) {
                String targetGroup = hideMatcher.group("target");

                PsiElement<?> player = skriptLoader.forceParseElement(hideMatcher.group("player"), lineNumber);
                PsiElement<?> target = targetGroup == null
                    ? null
                    : skriptLoader.forceParseElement(targetGroup, lineNumber);

                return create(player, target, false, lineNumber);
            }

            return null;
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param player the player to hide/show
         * @param target the player to hide/show {@link #player} for
         * @param show true to show the player, false to hide them
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiPlayerVisibilityEffect create(@NotNull PsiElement<?> player, @Nullable PsiElement<?> target,
                                                boolean show, int lineNumber) {
            return new PsiPlayerVisibilityEffect(player, target, show, lineNumber);
        }
    }
}
