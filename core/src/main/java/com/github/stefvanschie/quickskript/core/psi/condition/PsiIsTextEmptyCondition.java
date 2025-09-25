package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the texts are empty.
 *
 * @since 0.1.0
 */
public class PsiIsTextEmptyCondition extends PsiElement<Boolean> {

    /**
     * The texts to check if they are empty.
     */
    private PsiElement<?> texts;

    /**
     * If false, the result is negated.
     */
    private final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param texts the texts to check if they are empty
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsTextEmptyCondition(@NotNull PsiElement<?> texts, boolean positive, int lineNumber) {
        super(lineNumber);

        this.texts = texts;
        this.positive = positive;

        if (this.texts.isPreComputed()) {
            preComputed = executeImpl(null, null);

            this.texts = null;
        }
    }

    @Nullable
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return this.positive == this.texts.executeMulti(environment, context, String.class).test(String::isEmpty);
    }

    /**
     * A factory for creating {@link PsiIsTextEmptyCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param texts the texts to check if they are empty
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%texts% (is|are) empty")
        public PsiIsTextEmptyCondition parsePositive(@NotNull PsiElement<?> texts, int lineNumber) {
            return create(texts, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param texts the texts to check if they aren't empty
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%texts% (isn't|is not|aren't|are not) empty")
        public PsiIsTextEmptyCondition parseNegative(@NotNull PsiElement<?> texts, int lineNumber) {
            return create(texts, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param texts the texts to check if they are empty
         * @param positive if false, the result is negated
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiIsTextEmptyCondition create(@NotNull PsiElement<?> texts, boolean positive, int lineNumber) {
            return new PsiIsTextEmptyCondition(texts, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
