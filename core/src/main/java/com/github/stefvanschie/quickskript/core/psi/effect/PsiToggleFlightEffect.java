package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
    protected final PsiElement<?> player;

    /**
     * True if flight is allowed, otherwise it will be denied
     */
    protected final boolean enabled;

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
     * A factory for creating {@link PsiToggleFlightEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching enable elements
         */
        @NotNull
        private final SkriptPattern enablePattern =
            SkriptPattern.parse("(allow|enable) (fly|flight) (for|to) %players%");

        /**
         * The pattern for matching disable elements
         */
        @NotNull
        private final SkriptPattern disablePattern =
            SkriptPattern.parse("(disallow|disable) (fly|flight) (for|to) %players%");

        /**
         * Parses the {@link #enablePattern} and invokes this method with its types if the match succeeds
         *
         * @param player the player to allow flight for
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("enablePattern")
        public PsiToggleFlightEffect parseEnable(@NotNull PsiElement<?> player, int lineNumber) {
            return create(player, true, lineNumber);
        }

        /**
         * Parses the {@link #disablePattern} and invokes this method with its types if the match succeeds
         *
         * @param player the player to disable flight for
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("disablePattern")
        public PsiToggleFlightEffect parseDisable(@NotNull PsiElement<?> player, int lineNumber) {
            return create(player, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
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
