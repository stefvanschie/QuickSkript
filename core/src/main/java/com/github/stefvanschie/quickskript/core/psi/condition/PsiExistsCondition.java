package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks whether a particular object is (not) null
 *
 * @since 0.1.0
 */
public class PsiExistsCondition extends PsiElement<Boolean> {

    /**
     * The object to check for
     */
    private PsiElement<?> object;

    /**
     * False is the result should be negated. Keep in mind that 'exists' implies 'not null', so if this is false, it's
     * being checked if it is null.
     */
    private final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiExistsCondition(@NotNull PsiElement<?> object, boolean positive, int lineNumber) {
        super(lineNumber);

        this.object = object;
        this.positive = positive;

        if (object.isPreComputed()) {
            preComputed = executeImpl(null);
            this.object = null;
        }
    }

    @Nullable
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        return positive == (object.execute(context) != null);
    }

    /**
     * A factory for creating {@link PsiExistsCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching positive {@link PsiExistsCondition}s
         */
        @NotNull
        private final SkriptPattern positivePattern = SkriptPattern.parse("%~objects% (exist[s]|(is|are) set)");

        /**
         * The pattern for matching negative {@link PsiExistsCondition}s
         */
        @NotNull
        private final SkriptPattern negativePattern =
            SkriptPattern.parse("%~objects% (do[es](n't| not) exist|(is|are)(n't| not) set)");

        /**
         * Parses the {@link #positivePattern} and invokes this method with its types if the match succeeds
         *
         * @param object the object
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("positivePattern")
        public PsiExistsCondition parsePositive(@NotNull PsiElement<?> object, int lineNumber) {
            return create(object, true, lineNumber);
        }

        /**
         * Parses the {@link #negativePattern} and invokes this method with its types if the match succeeds
         *
         * @param object the object
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("negativePattern")
        public PsiExistsCondition parseNegative(@NotNull PsiElement<?> object, int lineNumber) {
            return create(object, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param object the object
         * @param positive false if the result should be negated
         * @param lineNumber the line number
         * @return the exists condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiExistsCondition create(@NotNull PsiElement<?> object, boolean positive, int lineNumber) {
            return new PsiExistsCondition(object, positive, lineNumber);
        }
    }
}
