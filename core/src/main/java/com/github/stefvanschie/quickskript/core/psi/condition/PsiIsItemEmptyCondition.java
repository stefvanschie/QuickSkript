package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.Type;
import com.github.stefvanschie.quickskript.core.util.literal.Item;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if an item is empty.
 *
 * @since 0.1.0
 */
public class PsiIsItemEmptyCondition extends PsiElement<Boolean> {

    /**
     * The item to check if it's empty.
     */
    private PsiElement<? extends Item> item;

    /**
     * If false, the result is negated.
     */
    private final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param item the item to check if it's empty
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsItemEmptyCondition(@NotNull PsiElement<? extends Item> item, boolean positive, int lineNumber) {
        super(lineNumber);

        this.item = item;
        this.positive = positive;

        if (item.isPreComputed()) {
            preComputed = executeImpl(null, null);

            this.item = null;
        }
    }

    @Nullable
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        Item item = this.item.execute(environment, context);

        if (item == null) {
            throw new ExecutionException(
                "Result of PsiIsItemEmptyCondition should be Item, but it was null",
                super.lineNumber
            );
        }

        return this.positive == item.isEmpty();
    }

    /**
     * A factory for creating {@link PsiIsItemEmptyCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching positive {@link PsiIsItemEmptyCondition}s
         */
        @NotNull
        private final SkriptPattern positivePattern = SkriptPattern.parse("%item% (is|are) empty");

        /**
         * The pattern for matching negative {@link PsiIsItemEmptyCondition}s
         */
        @NotNull
        private final SkriptPattern negativePattern = SkriptPattern.parse(
            "%item% (isn't|is not|aren't|are not) empty"
        );

        /**
         * Parses the {@link #positivePattern} and invokes this method with its types if the match succeeds
         *
         * @param item the item to check if it's empty
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("positivePattern")
        public PsiIsItemEmptyCondition parsePositive(@NotNull PsiElement<? extends Item> item, int lineNumber) {
            return create(item, true, lineNumber);
        }

        /**
         * Parses the {@link #negativePattern} and invokes this method with its types if the match succeeds
         *
         * @param item the item to check if it's not empty
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("negativePattern")
        public PsiIsItemEmptyCondition parseNegative(@NotNull PsiElement<? extends Item> item, int lineNumber) {
            return create(item, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param item the item to check if it's empty
         * @param positive if false, the result is negated
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiIsItemEmptyCondition create(
            @NotNull PsiElement<? extends Item> item,
            boolean positive,
            int lineNumber
        ) {
            return new PsiIsItemEmptyCondition(item, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
