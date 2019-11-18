package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks whether a player is sneaking. This cannot be pre computed, since players may start or end sneaking during game
 * play.
 *
 * @since 0.1.0
 */
public class PsiIsSneakingCondition extends PsiElement<Boolean> {

    /**
     * The player to check whether they are sneaking
     */
    @NotNull
    protected final PsiElement<?> player;

    /**
     * If false, the execution result will be inverted
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to check whether they are sneaking
     * @param positive if false, the execution result will be inverted
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiIsSneakingCondition(@NotNull PsiElement<?> player, boolean positive, int lineNumber) {
        super(lineNumber);

        this.player = player;
        this.positive = positive;
    }

    /**
     * A factory for creating {@link PsiIsSneakingCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * A pattern for matching positive {@link PsiIsSneakingCondition}s
         */
        @NotNull
        private final SkriptPattern positivePattern = SkriptPattern.parse("%players% (is|are) sneaking");

        /**
         * A pattern for matching negative {@link PsiIsSneakingCondition}s
         */
        @NotNull
        private final SkriptPattern negativePattern =
            SkriptPattern.parse("%players% (isn't|is not|aren't|are not) sneaking");

        /**
         * Parses the {@link #positivePattern} and invokes this method with its types if the match succeeds
         *
         * @param player the player to check whether they are sneaking
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("positivePattern")
        public PsiIsSneakingCondition parsePositive(@NotNull PsiElement<?> player, int lineNumber) {
            return create(player, true, lineNumber);
        }

        /**
         * Parses the {@link #negativePattern} and invokes this method with its types if the match succeeds
         *
         * @param player the player to check whether they are sneaking
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("negativePattern")
        public PsiIsSneakingCondition parseNegative(@NotNull PsiElement<?> player, int lineNumber) {
            return create(player, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param player the player to check whether they are sneaking
         * @param positive false if the result of the execution should be negated, true otherwise
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiIsSneakingCondition create(@NotNull PsiElement<?> player, boolean positive, int lineNumber) {
            return new PsiIsSneakingCondition(player, positive, lineNumber);
        }
    }
}
