package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks whether a player is currently sprinting. This cannot be pre computed, since the player may stop or start
 * sprinting during game play.
 *
 * @since 0.1.0
 */
public class PsiIsSprintingCondition extends PsiElement<Boolean> {

    /**
     * The player to test their sprinting state
     */
    @NotNull
    protected final PsiElement<?> player;

    /**
     * False if the result of execution needs to be inverted
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to test their sprinting state
     * @param positive false if the result of execution needs to be inverted
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiIsSprintingCondition(@NotNull PsiElement<?> player, boolean positive, int lineNumber) {
        super(lineNumber);

        this.player = player;
        this.positive = positive;
    }

    /**
     * A factory to create {@link PsiIsSprintingCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * A pattern for matching positive {@link PsiIsSprintingCondition}s
         */
        @NotNull
        private final SkriptPattern positivePattern = SkriptPattern.parse("%players% (is|are) sprinting");

        /**
         * A pattern for matching negative {@link PsiIsSprintingCondition}s
         */
        @NotNull
        private final SkriptPattern negativePattern =
            SkriptPattern.parse("%players% (isn't|is not|aren't|are not) sprinting");

        /**
         * Parses the {@link #positivePattern} and invokes this method with its types if the match succeeds
         *
         * @param player the player to check whether they are sprinting
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("positivePattern")
        public PsiIsSprintingCondition parsePositive(@NotNull PsiElement<?> player, int lineNumber) {
            return create(player, true, lineNumber);
        }

        /**
         * Parses the {@link #negativePattern} and invokes this method with its types if the match succeeds
         *
         * @param player the player to check whether they are sprinting
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("negativePattern")
        public PsiIsSprintingCondition parseNegative(@NotNull PsiElement<?> player, int lineNumber) {
            return create(player, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param player the player to check whether they are sprinting
         * @param positive false if the result of the execution should be negated, true otherwise
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiIsSprintingCondition create(@NotNull PsiElement<?> player, boolean positive, int lineNumber) {
            return new PsiIsSprintingCondition(player, positive, lineNumber);
        }
    }
}
