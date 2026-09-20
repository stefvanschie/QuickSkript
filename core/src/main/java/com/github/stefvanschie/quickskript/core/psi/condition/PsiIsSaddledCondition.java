package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks whether the living entities are saddled.
 *
 * @since 0.1.0
 */
public class PsiIsSaddledCondition extends PsiElement<Boolean> {

    /**
     * The living entities to check whether they are saddled.
     */
    @NotNull
    protected final PsiElement<?> livingEntities;

    /**
     * If true, this will check if the living entities are wearing a saddle.
     */
    protected final boolean properly;

    /**
     * If false, the result is negated.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param livingEntities the living entities to check whether they are saddled
     * @param properly if true, this will check if the living entities are wearing a saddle
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiIsSaddledCondition(@NotNull PsiElement<?> livingEntities, boolean properly, boolean positive,
                                    int lineNumber) {
        super(lineNumber);

        this.livingEntities = livingEntities;
        this.properly = properly;
        this.positive = positive;
    }

    /**
     * A factory for creating instances of {@link PsiIsSaddledCondition}.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param livingEntities the living entities to check if they are saddled
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%living entities% (is|are) [1¦properly] saddled")
        public PsiIsSaddledCondition parsePositive(@NotNull SkriptMatchResult result,
                                                     @NotNull PsiElement<?> livingEntities, int lineNumber) {
            return create(livingEntities, result.getParseMark() == 1, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param livingEntities the living entities to check if they are saddled
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%living entities% (isn't|is not|aren't|are not) [1¦properly] saddled")
        public PsiIsSaddledCondition parseNegative(@NotNull SkriptMatchResult result,
                                                     @NotNull PsiElement<?> livingEntities, int lineNumber) {
            return create(livingEntities, result.getParseMark() == 1, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param livingEntities the living entities to check if they are responsive
         * @param properly if true, this will check if the living entities are wearing a saddle
         * @param positive false if the result of the execution should be negated, true otherwise
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(value = "_, _, _, _ -> new", pure = true)
        public PsiIsSaddledCondition create(@NotNull PsiElement<?> livingEntities, boolean properly, boolean positive,
                                            int lineNumber) {
            return new PsiIsSaddledCondition(livingEntities, properly, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public String getType() {
            return "boolean";
        }
    }
}
