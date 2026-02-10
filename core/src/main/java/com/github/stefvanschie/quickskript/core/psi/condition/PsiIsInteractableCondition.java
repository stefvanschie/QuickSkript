package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks whether the provided item types can be interacted with.
 *
 * @since 0.1.0
 */
public class PsiIsInteractableCondition extends PsiElement<Boolean> {

    /**
     * The item types to check if they can be interacted with.
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
     * @param itemTypes the item types to check if they can be interacted with
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiIsInteractableCondition(@NotNull PsiElement<?> itemTypes, boolean positive, int lineNumber) {
        super(lineNumber);

        this.itemTypes = itemTypes;
        this.positive = positive;
    }

    /**
     * A factory for creating {@link PsiIsInteractableCondition}s.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param itemTypes the item types to check if they can be interacted with
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%item types% (is|are) interactable")
        public PsiIsInteractableCondition parsePositive(@NotNull PsiElement<?> itemTypes, int lineNumber) {
            return create(itemTypes, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param itemTypes the item types to check if they can be interacted with
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%item types% (isn't|is not|aren't|are not) interactable")
        public PsiIsInteractableCondition parseNegative(@NotNull PsiElement<?> itemTypes, int lineNumber) {
            return create(itemTypes, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param itemTypes the item types to check if they can be interacted with
         * @param positive if false, the result is negated
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        public PsiIsInteractableCondition create(@NotNull PsiElement<?> itemTypes, boolean positive, int lineNumber) {
            return new PsiIsInteractableCondition(itemTypes, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
