package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    protected final PsiElement<?> player;

    /**
     * The player to hide/show {@link #player} to/for, or all online players if null
     */
    @Nullable
    protected final PsiElement<?> target;

    /**
     * True if the {@link #player} will be shown, otherwise the {@link #player} will be hidden
     */
    protected final boolean show;

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
     * A factory for creating {@link PsiPlayerVisibilityEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern to match a showing element
         */
        @NotNull
        private final SkriptPattern showPattern = SkriptPattern.parse("reveal %players% [(to|for|from) %players%]");

        /**
         * The pattern to match a hiding element
         */
        @NotNull
        private final SkriptPattern hidePattern = SkriptPattern.parse("hide %players% [(from|for) %players%]");

        /**
         * Parses the {@link #showPattern} and invokes this method with its types if the match succeeds
         *
         * @param player the player to show
         * @param target the player to show {@link #player} for
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("showPattern")
        public PsiPlayerVisibilityEffect parseShow(@NotNull PsiElement<?> player, @Nullable PsiElement<?> target,
                                                   int lineNumber) {
            return create(player, target, true, lineNumber);
        }

        /**
         * Parses the {@link #hidePattern} and invokes this method with its types if the match succeeds
         *
         * @param player the player to hide
         * @param target the player to hide {@link #player} for
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("hidePattern")
        public PsiPlayerVisibilityEffect parseHide(@NotNull PsiElement<?> player, @Nullable PsiElement<?> target,
                                                   int lineNumber) {
            return create(player, target, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
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
