package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import com.github.stefvanschie.quickskript.core.util.literal.Hand;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks if living entities have their hands raised.
 *
 * @since 0.1.0
 */
public class PsiIsHandRaisedCondition extends PsiElement<Boolean> {

    /**
     * The living entities to check if their hands are raised.
     */
    @NotNull
    protected final PsiElement<?> livingEntities;

    /**
     * The type of hand to check.
     */
    @NotNull
    protected final Hand hand;

    /**
     * If false, the result is negated.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param livingEntities the living entities to check if their hands are raised
     * @param hand the type of hand to check
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiIsHandRaisedCondition(
        @NotNull PsiElement<?> livingEntities,
        @NotNull Hand hand,
        boolean positive,
        int lineNumber
    ) {
        super(lineNumber);

        this.livingEntities = livingEntities;
        this.hand = hand;
        this.positive = positive;
    }

    /**
     * A factory for creating {@link PsiIsHandRaisedCondition}s.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param livingEntities the living entities to check if their hands are raised
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%living entities%'[s] [main] hand[s] (is|are) raised")
        @Pattern("[main] hand[s] of %living entities% (is|are) raised")
        public PsiIsHandRaisedCondition parseMainPositive(@NotNull PsiElement<?> livingEntities, int lineNumber) {
            return create(livingEntities, Hand.MAIN_HAND, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param livingEntities the living entities to check if their hands are raised
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%living entities%'[s] [main] hand[s] (isn't|is not|aren't|are not) raised")
        @Pattern("[main] hand[s] of %living entities% (isn't|is not|aren't|are not) raised")
        public PsiIsHandRaisedCondition parseMainNegative(@NotNull PsiElement<?> livingEntities, int lineNumber) {
            return create(livingEntities, Hand.MAIN_HAND, false, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param livingEntities the living entities to check if their hands are raised
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%living entities%'[s] off[ |-]hand[s] (is|are) raised")
        @Pattern("off[ |-]hand[s] of %living entities% (is|are) raised")
        public PsiIsHandRaisedCondition parseOffPositive(@NotNull PsiElement<?> livingEntities, int lineNumber) {
            return create(livingEntities, Hand.OFFHAND, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param livingEntities the living entities to check if their hands are raised
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%living entities%'[s] off[ |-]hand[s] (isn't|is not|aren't|are not) raised")
        @Pattern("off[ |-]hand[s] of %living entities% (isn't|is not|aren't|are not) raised")
        public PsiIsHandRaisedCondition parseOffNegative(@NotNull PsiElement<?> livingEntities, int lineNumber) {
            return create(livingEntities, Hand.OFFHAND, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param livingEntities the living entities to check if their hands are raised
         * @param hand the type of hand to check
         * @param positive false if the result of the execution should be negated, true otherwise
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(value = "_, _, _, _ -> new", pure = true)
        public PsiIsHandRaisedCondition create(
            @NotNull PsiElement<?> livingEntities,
            @NotNull Hand hand,
            boolean positive,
            int lineNumber
        ) {
            return new PsiIsHandRaisedCondition(livingEntities, hand, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
