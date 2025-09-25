package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.MultiResult;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import com.github.stefvanschie.quickskript.core.util.literal.Slot;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the hotbar slot the specified players have currently selected.
 *
 * @since 0.1.0
 */
public class PsiHotbarSlotExpression extends PsiElement<MultiResult<? extends Slot>> {

    /**
     * The players whose hotbar slots to get or null if unspecified.
     */
    @Nullable
    protected final PsiElement<?> players;

    /**
     * Creates a new element with the given line number
     *
     * @param players the players to get the hotbar slot from
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiHotbarSlotExpression(@Nullable PsiElement<?> players, int lineNumber) {
        super(lineNumber);

        this.players = players;
    }

    /**
     * A factory for creating {@link PsiHotbarSlotExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the patterns and invokes this method with its types if the match succeeds
         *
         * @param players the players to get the selected hotbar of
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("[the] [[currently] selected|current] hotbar slot[s] [of %players%]")
        @Pattern("%players%'[s] [[currently] selected|current] hotbar slot[s]")
        public PsiHotbarSlotExpression parse(@Nullable PsiElement<?> players, int lineNumber) {
            return create(players, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param players the players to get the selected hotbar of
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiHotbarSlotExpression create(@Nullable PsiElement<?> players, int lineNumber) {
            return new PsiHotbarSlotExpression(players, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.SLOTS;
        }
    }
}
