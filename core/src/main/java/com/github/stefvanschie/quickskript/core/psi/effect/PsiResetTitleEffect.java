package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    protected final PsiElement<?> player;

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
     * A factory to create {@link PsiResetTitleEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The patterns to match this element with
         */
        @NotNull
        private final SkriptPattern[] patterns = SkriptPattern.parse(
            "reset [the] title[s] [of %players%]",
            "reset [the] %players%'[s] title[s]"
        );

        /**
         * Parses the {@link #patterns} and invokes this method with its types if the match succeeds
         *
         * @param player the player to reset the title for
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("patterns")
        public PsiResetTitleEffect parse(@NotNull PsiElement<?> player, int lineNumber) {
            return create(player, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
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
