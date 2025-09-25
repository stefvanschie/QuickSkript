package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Gets the ping of the player. This cannot be pre-computed, because this may change during gameplay.
 *
 * @since 0.1.0
 */
public class PsiPingExpression extends PsiElement<Integer> {

    /**
     * The player to get the ping from
     */
    @NotNull
    protected PsiElement<?> player;

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to get the ping from
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiPingExpression(@NotNull PsiElement<?> player, int lineNumber) {
        super(lineNumber);

        this.player = player;
    }

    /**
     * A factory for creating {@link PsiPingExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the patterns and invokes this method with its types if the match succeeds
         *
         * @param player the player to get the ping from
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("[the] ping of %players%")
        @Pattern("%players%'[s] ping")
        public PsiPingExpression parse(@NotNull PsiElement<?> player, int lineNumber) {
            return create(player, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param player the player to get the ping from
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiPingExpression create(@NotNull PsiElement<?> player, int lineNumber) {
            return new PsiPingExpression(player, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.NUMBER;
        }
    }
}
