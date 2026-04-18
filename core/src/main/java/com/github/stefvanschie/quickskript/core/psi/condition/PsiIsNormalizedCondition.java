package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks if the given vectors are normalized.
 *
 * @since 0.1.0
 */
public class PsiIsNormalizedCondition extends PsiElement<Boolean> {

    /**
     * The vectors to check if they are normalized.
     */
    @NotNull
    protected final PsiElement<?> vectors;

    /**
     * If false, the result is negated.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param vectors the vectors to check if they are normalized
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiIsNormalizedCondition(@NotNull PsiElement<?> vectors, boolean positive, int lineNumber) {
        super(lineNumber);

        this.vectors = vectors;
        this.positive = positive;
    }

    /**
     * A factory to create instances of {@link PsiIsNormalizedCondition}.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param vectors the vectors to check if they are normalized
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%vectors% (is|are) normalized")
        public PsiIsNormalizedCondition parsePositive(@NotNull PsiElement<?> vectors, int lineNumber) {
            return create(vectors, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param vectors the vectors to check if they are normalized
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%vectors% (isn't|is not|aren't|are not) normalized")
        public PsiIsNormalizedCondition parseNegative(@NotNull PsiElement<?> vectors, int lineNumber) {
            return create(vectors, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param vectors the vectors to check if they are normalized
         * @param positive if false, the result is negated
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        public PsiIsNormalizedCondition create(@NotNull PsiElement<?> vectors, boolean positive, int lineNumber) {
            return new PsiIsNormalizedCondition(vectors, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
