package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks if the fishing hooks are in open water.
 *
 * @since 0.1.0
 */
public class PsiIsFishhookInOpenWaterCondition extends PsiElement<Boolean> {

    /**
     * The fishing hooks to check if they are in open water.
     */
    @NotNull
    protected final PsiElement<?> fishingHooks;

    /**
     * If false, the result is negated.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param fishingHooks the fishing hooks to check if they are in open water
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiIsFishhookInOpenWaterCondition(@NotNull PsiElement<?> fishingHooks, boolean positive, int lineNumber) {
        super(lineNumber);

        this.fishingHooks = fishingHooks;
        this.positive = positive;
    }

    /**
     * A factory for creating {@link PsiIsFishhookInOpenWaterCondition}s.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param fishingHooks the fishing hooks to check if they are in open water
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%entities% (is|are) in open water[s]")
        public PsiIsFishhookInOpenWaterCondition parsePositive(@NotNull PsiElement<?> fishingHooks, int lineNumber) {
            return create(fishingHooks, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param fishingHooks the fishing hooks to check if they are in open water
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%entities% (isn't|is not|aren't|are not) in open water[s]")
        public PsiIsFishhookInOpenWaterCondition parseNegative(@NotNull PsiElement<?> fishingHooks, int lineNumber) {
            return create(fishingHooks, false, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param fishingHooks the fishing hooks to check if they are in open water
         * @param positive if false, the result is negated
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiIsFishhookInOpenWaterCondition create(
            @NotNull PsiElement<?> fishingHooks,
            boolean positive,
            int lineNumber
        ) {
            return new PsiIsFishhookInOpenWaterCondition(fishingHooks, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
