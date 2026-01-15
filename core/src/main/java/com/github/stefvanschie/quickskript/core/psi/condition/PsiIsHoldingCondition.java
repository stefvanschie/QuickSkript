package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import com.github.stefvanschie.quickskript.core.util.literal.Hand;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Check if living entities are holding a particular item in their hand.
 *
 * @since 0.1.0
 */
public class PsiIsHoldingCondition extends PsiElement<Boolean> {

    /**
     * The living entities to check their item in hand, or null if unspecified.
     */
    @Nullable
    protected final PsiElement<?> livingEntities;

    /**
     * The item types to check against.
     */
    @NotNull
    protected final PsiElement<?> itemTypes;

    /**
     * The hand to check the item of.
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
     * @param livingEntities the living entities to check their item in hand, or null if unspecified
     * @param itemTypes the item types to check against
     * @param hand the hand to check the item of
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiIsHoldingCondition(
        @Nullable PsiElement<?> livingEntities,
        @NotNull PsiElement<?> itemTypes,
        @NotNull Hand hand,
        boolean positive,
        int lineNumber
    ) {
        super(lineNumber);

        this.livingEntities = livingEntities;
        this.itemTypes = itemTypes;
        this.hand = hand;
        this.positive = positive;
    }

    /**
     * A factory for creating {@link PsiIsHoldingCondition}s.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param livingEntities the living entities to check their item in hand, or null if unspecified
         * @param itemTypes the item types to check against
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("[%living entities%] ha(s|ve) %item types% in [main] hand")
        @Pattern("[%living entities%] (is|are) holding %item types% [in main hand]")
        public PsiIsHoldingCondition parseMainPositive(
            @Nullable PsiElement<?> livingEntities,
            @NotNull PsiElement<?> itemTypes,
            int lineNumber
        ) {
            return create(livingEntities, itemTypes, Hand.MAIN_HAND, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param livingEntities the living entities to check their item in hand, or null if unspecified
         * @param itemTypes the item types to check against
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("[%living entities%] (ha(s|ve) not|do[es]n't have) %item types% in [main] hand")
        @Pattern("[%living entities%] (is not|isn't) holding %item types% [in main hand]")
        public PsiIsHoldingCondition parseMainNegative(
            @Nullable PsiElement<?> livingEntities,
            @NotNull PsiElement<?> itemTypes,
            int lineNumber
        ) {
            return create(livingEntities, itemTypes, Hand.MAIN_HAND, false, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param livingEntities the living entities to check their item in hand, or null if unspecified
         * @param itemTypes the item types to check against
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("[%living entities%] ha(s|ve) %item types% in off[-| ]hand")
        @Pattern("[%living entities%] (is|are) holding %item types% in off[-| ]hand")
        public PsiIsHoldingCondition parseOffPositive(
            @Nullable PsiElement<?> livingEntities,
            @NotNull PsiElement<?> itemTypes,
            int lineNumber
        ) {
            return create(livingEntities, itemTypes, Hand.OFFHAND, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param livingEntities the living entities to check their item in hand, or null if unspecified
         * @param itemTypes the item types to check against
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("[%living entities%] (ha(s|ve) not|do[es]n't have) %item types% in off[-| ]hand")
        @Pattern("[%living entities%] (is not|isn't) holding %item types% in off[-| ]hand")
        public PsiIsHoldingCondition parseOffNegative(
            @Nullable PsiElement<?> livingEntities,
            @NotNull PsiElement<?> itemTypes,
            int lineNumber
        ) {
            return create(livingEntities, itemTypes, Hand.OFFHAND, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param livingEntities the living entities to check their item in hand, or null if unspecified
         * @param itemTypes the item types to check against
         * @param hand the hand to check the item of
         * @param positive if false, the result is negated
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(value = "_, _, _, _, _ -> new", pure = true)
        public PsiIsHoldingCondition create(
            @Nullable PsiElement<?> livingEntities,
            @NotNull PsiElement<?> itemTypes,
            @NotNull Hand hand,
            boolean positive,
            int lineNumber
        ) {
            return new PsiIsHoldingCondition(livingEntities, itemTypes, hand, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
