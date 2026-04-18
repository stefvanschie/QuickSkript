package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks if the blocks or entities are lootable.
 *
 * @since 0.1.0
 */
public class PsiIsLootableCondition extends PsiElement<Boolean> {

    /**
     * The objects to check if they are lootable.
     */
    @NotNull
    protected final PsiElement<?> objects;

    /**
     * If false, the result is negated.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param objects the objects to check if they are lootable
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiIsLootableCondition(@NotNull PsiElement<?> objects, boolean positive, int lineNumber) {
        super(lineNumber);

        this.objects = objects;
        this.positive = positive;
    }

    /**
     * A factory to create instances of {@link PsiIsLootableCondition}.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param objects the objects to check if they are lootable
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%blocks/entities% (is|are) lootable")
        public PsiIsLootableCondition parsePositive(@NotNull PsiElement<?> objects, int lineNumber) {
            return create(objects, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param objects the objects to check if they are lootable
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%blocks/entities% (isn't|is not|aren't|are not) lootable")
        public PsiIsLootableCondition parseNegative(@NotNull PsiElement<?> objects, int lineNumber) {
            return create(objects, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param objects the objects to check if they are lootable
         * @param positive if false, the result is negated
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        public PsiIsLootableCondition create(@NotNull PsiElement<?> objects, boolean positive, int lineNumber) {
            return new PsiIsLootableCondition(objects, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
