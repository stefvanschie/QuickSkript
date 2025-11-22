package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks if the item types are a block and can catch fire.
 *
 * @since 0.1.0
 */
public class PsiIsFlammableCondition extends PsiElement<Boolean> {

    /**
     * The item types to check if they are a block and can catch fire.
     */
    @NotNull
    protected final PsiElement<?> itemTypes;

    /**
     * If false, the result will be negated.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param itemTypes the item types to check if they are a block and can catch fire
     * @param positive if false, the result will be negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiIsFlammableCondition(@NotNull PsiElement<?> itemTypes, boolean positive, int lineNumber) {
        super(lineNumber);

        this.itemTypes = itemTypes;
        this.positive = positive;
    }

    /**
     * A factory for creating {@link PsiIsFlammableCondition}s.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param itemTypes the item types to check if they are a block and can catch fire
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%item types% (is|are) flammable")
        public PsiIsFlammableCondition parsePositive(@NotNull PsiElement<?> itemTypes, int lineNumber) {
            return create(itemTypes, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param itemTypes the item types to check if they are not a block or cannot catch fire
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%item types% (isn't|is not|aren't|are not) flammable")
        public PsiIsFlammableCondition parseNegative(@NotNull PsiElement<?> itemTypes, int lineNumber) {
            return create(itemTypes, false, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param itemTypes the item types to check if they are a block and can catch fire
         * @param positive if false, the result is negated
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        public PsiIsFlammableCondition create(@NotNull PsiElement<?> itemTypes, boolean positive, int lineNumber) {
            return new PsiIsFlammableCondition(itemTypes, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
