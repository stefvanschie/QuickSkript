package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks if the item types are blocks.
 *
 * @since 0.1.0
 */
public class PsiIsBlockCondition extends PsiElement<Boolean> {

    /**
     * The item types to check if they are blocks.
     */
    @NotNull
    protected final PsiElement<?> itemTypes;

    /**
     * If false, the result is negated.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiIsBlockCondition(@NotNull PsiElement<?> itemTypes, boolean positive, int lineNumber) {
        super(lineNumber);

        this.itemTypes = itemTypes;
        this.positive = positive;
    }

    /**
     * A factory for creating {@link PsiIsBlockCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching positive {@link PsiIsBlockCondition}s
         */
        @NotNull
        private final SkriptPattern positivePattern = SkriptPattern.parse(
            "%item types% (is|are) ([a] block|blocks)"
        );

        /**
         * The pattern for matching negative {@link PsiIsBlockCondition}s
         */
        @NotNull
        private final SkriptPattern negativePattern = SkriptPattern.parse(
            "%item types% (isn't|is not|aren't|are not) ([a] block|blocks)"
        );

        /**
         * Parses the {@link #positivePattern} and invokes this method with its types if the match succeeds
         *
         * @param itemTypes the item types to check if they are blocks
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("positivePattern")
        public PsiIsBlockCondition parsePositive(@NotNull PsiElement<?> itemTypes, int lineNumber) {
            return create(itemTypes, true, lineNumber);
        }

        /**
         * Parses the {@link #negativePattern} and invokes this method with its types if the match succeeds
         *
         * @param itemTypes the item types to check if they aren't blocks
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("negativePattern")
        public PsiIsBlockCondition parseNegative(@NotNull PsiElement<?> itemTypes, int lineNumber) {
            return create(itemTypes, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param itemTypes the item types to check if they are blocks
         * @param positive if false, the result is negated
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiIsBlockCondition create(@NotNull PsiElement<?> itemTypes, boolean positive, int lineNumber) {
            return new PsiIsBlockCondition(itemTypes, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
