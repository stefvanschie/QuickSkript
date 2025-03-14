package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.Type;
import com.github.stefvanschie.quickskript.core.util.literal.Slot;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if slots are empty.
 *
 * @since 0.1.0
 */
public class PsiIsSlotEmptyCondition extends PsiElement<Boolean> {

    /**
     * The slots to check if they are empty.
     */
    @NotNull
    private final PsiElement<?> slots;

    /**
     * If false, the result is negated.
     */
    private final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param slots the slots to check if they are empty
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsSlotEmptyCondition(@NotNull PsiElement<?> slots, boolean positive, int lineNumber) {
        super(lineNumber);

        this.slots = slots;
        this.positive = positive;
    }

    @Nullable
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return this.positive == this.slots.executeMulti(environment, context, Slot.class).test(Slot::isEmpty);
    }

    /**
     * A factory for creating {@link PsiIsSlotEmptyCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching positive {@link PsiIsSlotEmptyCondition}s
         */
        @NotNull
        private final SkriptPattern positivePattern = SkriptPattern.parse("%slots% (is|are) empty");

        /**
         * The pattern for matching negative {@link PsiIsSlotEmptyCondition}s
         */
        @NotNull
        private final SkriptPattern negativePattern = SkriptPattern.parse(
            "%slots% (isn't|is not|aren't|are not) empty"
        );

        /**
         * Parses the {@link #positivePattern} and invokes this method with its types if the match succeeds
         *
         * @param slots the slots to check if they are empty
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("positivePattern")
        public PsiIsSlotEmptyCondition parsePositive(@NotNull PsiElement<?> slots, int lineNumber) {
            return create(slots, true, lineNumber);
        }

        /**
         * Parses the {@link #negativePattern} and invokes this method with its types if the match succeeds
         *
         * @param slots the slots to check if they aren't empty
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("negativePattern")
        public PsiIsSlotEmptyCondition parseNegative(@NotNull PsiElement<?> slots, int lineNumber) {
            return create(slots, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param slots the slots to check if they are empty
         * @param positive if false, the result is negated
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiIsSlotEmptyCondition create(@NotNull PsiElement<?> slots, boolean positive, int lineNumber) {
            return new PsiIsSlotEmptyCondition(slots, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
