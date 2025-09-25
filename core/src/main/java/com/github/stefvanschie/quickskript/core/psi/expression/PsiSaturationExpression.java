package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.expression.util.*;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the player's saturation. This cannot be pre-computed, since this may change during gameplay.
 *
 * @since 0.1.0
 */
public class PsiSaturationExpression extends PsiElement<Float> implements Addable, Deletable, Removable, Resettable,
    Settable {

    /**
     * The player to get the saturation from
     */
    @Nullable
    protected PsiElement<?> player;

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to get the saturation from
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiSaturationExpression(@Nullable PsiElement<?> player, int lineNumber) {
        super(lineNumber);

        this.player = player;
    }

    /**
     * A factory for creating {@link PsiSaturationExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the patterns and invokes this method with its types if the match succeeds
         *
         * @param player the player to get the saturation from
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("[the] saturation [of %players%]")
        @Pattern("%players%'[s] saturation")
        public PsiSaturationExpression parse(@Nullable PsiElement<?> player, int lineNumber) {
            return create(player, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiSaturationExpression create(@Nullable PsiElement<?> player, int lineNumber) {
            return new PsiSaturationExpression(player, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.NUMBER;
        }
    }
}
