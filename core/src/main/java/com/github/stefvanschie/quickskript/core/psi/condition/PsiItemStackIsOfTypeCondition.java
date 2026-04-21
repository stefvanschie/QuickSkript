package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.MultiResult;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.Type;
import com.github.stefvanschie.quickskript.core.util.literal.ItemStack;
import com.github.stefvanschie.quickskript.core.util.literal.ItemType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the provided item stacks are of the provided item types.
 *
 * @since 0.1.0
 */
public class PsiItemStackIsOfTypeCondition extends PsiElement<Boolean> {

    /**
     * The item stacks to check if they are of the provided item types.
     */
    @NotNull
    private final PsiElement<?> itemStacks;

    /**
     * The item types to check if they are the type of the provided item stacks.
     */
    @NotNull
    private final PsiElement<?> itemTypes;

    /**
     * If false, the result is negated.
     */
    private final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param itemStacks the item stacks to check if they are of the provided item types
     * @param itemTypes the item types to check if they are the type of the provided item stacks
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiItemStackIsOfTypeCondition(@NotNull PsiElement<?> itemStacks, @NotNull PsiElement<?> itemTypes,
                                          boolean positive, int lineNumber) {
        super(lineNumber);

        this.itemStacks = itemStacks;
        this.itemTypes = itemTypes;
        this.positive = positive;
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        MultiResult<ItemType> itemTypes = this.itemTypes.executeMulti(environment, context, ItemType.class);
        MultiResult<ItemStack> itemStacks = this.itemStacks.executeMulti(environment, context, ItemStack.class);

        return this.positive == itemTypes.test(itemType ->
            itemStacks.test(itemStack -> itemStack.isSubTypeOf(itemType)));
    }

    /**
     * A factory to create instances of {@link PsiItemStackIsOfTypeCondition}.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param itemStacks the item stacks to check if they are of the provided item types
         * @param itemTypes the item types to check if they are the type of the provided item stacks
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%item stacks% (is|are) of type[s] %item types%")
        public PsiItemStackIsOfTypeCondition parsePositive(@NotNull PsiElement<?> itemStacks,
                                                           @NotNull PsiElement<?> itemTypes, int lineNumber) {
            return create(itemStacks, itemTypes, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param itemStacks the item stacks to check if they are of the provided item types
         * @param itemTypes the item types to check if they are the type of the provided item stacks
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%item stacks% (isn't|is not|aren't|are not) of type[s] %item types%")
        public PsiItemStackIsOfTypeCondition parseNegative(@NotNull PsiElement<?> itemStacks,
                                                           @NotNull PsiElement<?> itemTypes, int lineNumber) {
            return create(itemStacks, itemTypes, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param itemStacks the item stacks to check if they are of the provided item types
         * @param itemTypes the item types to check if they are the type of the provided item stacks
         * @param positive if false, the result is negated
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiItemStackIsOfTypeCondition create(@NotNull PsiElement<?> itemStacks, @NotNull PsiElement<?> itemTypes,
                                                    boolean positive, int lineNumber) {
            return new PsiItemStackIsOfTypeCondition(itemStacks, itemTypes, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
