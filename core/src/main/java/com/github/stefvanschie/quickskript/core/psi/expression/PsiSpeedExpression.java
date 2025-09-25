package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.expression.util.Addable;
import com.github.stefvanschie.quickskript.core.psi.expression.util.Removable;
import com.github.stefvanschie.quickskript.core.psi.expression.util.Resettable;
import com.github.stefvanschie.quickskript.core.psi.expression.util.Settable;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Gets the player's walking or flying speed
 *
 * @since 0.1.0
 */
public class PsiSpeedExpression extends PsiElement<Float> implements Addable, Removable, Resettable, Settable {

    /**
     * The type of movement to get the value of
     */
    @NotNull
    protected MovementType movementType;

    /**
     * The player to get the speed from
     */
    @NotNull
    protected PsiElement<?> player;

    /**
     * Creates a new element with the given line number
     *
     * @param movementType the type of movement to get the value of
     * @param player the player to get the speed from
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiSpeedExpression(@NotNull MovementType movementType, @NotNull PsiElement<?> player, int lineNumber) {
        super(lineNumber);

        this.movementType = movementType;
        this.player = player;
    }

    /**
     * A factory for creating {@link PsiSpeedExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the patterns and invokes this method with its types if the match succeeds
         *
         * @param player the player to get the speed from
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("[the] (walk[ing])[ |-]speed of %players%")
        @Pattern("%players%'[s] (walk[ing])[ |-]speed")
        public PsiSpeedExpression parseWalking(@NotNull PsiElement<?> player, int lineNumber) {
            return create(MovementType.WALKING, player, lineNumber);
        }

        /**
         * Parses the patterns and invokes this method with its types if the match succeeds
         *
         * @param player the player to get the speed from
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("[the] (fl(y[ing]|ight))[ |-]speed of %players%")
        @Pattern("%players%'[s] (fl(y[ing]|ight))[ |-]speed")
        public PsiSpeedExpression parseFlying(@NotNull PsiElement<?> player, int lineNumber) {
            return create(MovementType.FLYING, player, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param movementType the type of movement to get the value of
         * @param player the player to get the speed from
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiSpeedExpression create(@NotNull MovementType movementType, @NotNull PsiElement<?> player,
            int lineNumber) {
            return new PsiSpeedExpression(movementType, player, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.NUMBER;
        }
    }

    /**
     * The type of movement we should get the value of
     *
     * @since 0.1.0
     */
    protected enum MovementType {
        WALKING,
        FLYING
    }
}
