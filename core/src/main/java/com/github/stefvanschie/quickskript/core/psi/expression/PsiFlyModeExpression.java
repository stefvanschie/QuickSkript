package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.expression.util.Resettable;
import com.github.stefvanschie.quickskript.core.psi.expression.util.Settable;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Gets the fly mode for a player. This cannot be pre computed, since the fly mode of a player may change during game
 * play.
 *
 * @since 0.1.0
 */
public class PsiFlyModeExpression extends PsiElement<Boolean> implements Resettable, Settable {

    /**
     * The player to get the fly mode from
     */
    protected final PsiElement<?> player;

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to get the fly mode from
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiFlyModeExpression(@NotNull PsiElement<?> player, int lineNumber) {
        super(lineNumber);

        this.player = player;
    }

    /**
     * A factory for creating {@link PsiFlyModeExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The patterns for matching {@link PsiFlyModeExpression}s
         */
        @NotNull
        private final SkriptPattern[] patterns = SkriptPattern.parse(
            "[the] fl(y[ing]|ight) (mode|state) of %players%",
            "%players%'[s] fl(y[ing]|ight) (mode|state)"
        );

        /**
         * Parses the {@link #patterns} and invokes this method with its types if the match succeeds
         *
         * @param player the player to get the fly mode from
         * @param lineNumber the line number
         * @return the expression
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("patterns")
        public PsiFlyModeExpression parse(@NotNull PsiElement<?> player, int lineNumber) {
            return create(player, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param player the player tog et the fly mode from
         * @param lineNumber the line number
         * @return the expression
         */
        @NotNull
        @Contract(pure = true)
        public PsiFlyModeExpression create(@NotNull PsiElement<?> player, int lineNumber) {
            return new PsiFlyModeExpression(player, lineNumber);
        }
    }
}
