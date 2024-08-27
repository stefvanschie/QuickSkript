package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.MultiResult;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks whether a given list of objects contains an element.
 *
 * @since 0.1.0
 */
public class PsiContainsObjectCondition extends PsiElement<Boolean> {

    /**
     * The list to check.
     */
    private PsiElement<?> list;

    /**
     * The elements to check if they're in the list.
     */
    private PsiElement<?> elements;

    /**
     * If false, the result is inverted.
     */
    private final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param list the list to check
     * @param elements the elements to check if they're in the list
     * @param positive if false, the result is inverted
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiContainsObjectCondition(
        @NotNull PsiElement<?> list,
        @NotNull PsiElement<?> elements,
        boolean positive,
        int lineNumber
    ) {
        super(lineNumber);

        this.list = list;
        this.elements = elements;
        this.positive = positive;

        if (list.isPreComputed() && elements.isPreComputed()) {
            preComputed = executeImpl(null, null);

            this.list = null;
            this.elements = null;
        }
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        MultiResult<Object> objects = this.list.executeMulti(environment, context, Object.class);

        return this.positive == this.elements.executeMulti(environment, context).test(objects::contains);
    }

    /**
     * A factory for creating {@link PsiContainsObjectCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching positive {@link PsiContainsObjectCondition}s
         */
        @NotNull
        private final SkriptPattern positivePattern = SkriptPattern.parse("%objects% contain[s] %objects%");

        /**
         * The pattern for matching negative {@link PsiContainsObjectCondition}s
         */
        @NotNull
        private final SkriptPattern negativePattern = SkriptPattern.parse(
            "%objects% (doesn't|does not|do not|don't) contain %objects%"
        );

        /**
         * Parses the {@link #positivePattern} and invokes this method with its types if the match succeeds
         *
         * @param list the list to check
         * @param elements the elements to check if they're in the list
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("positivePattern")
        public PsiContainsObjectCondition parsePositive(
            @NotNull PsiElement<?> list,
            @NotNull PsiElement<?> elements,
            int lineNumber
        ) {
            return create(list, elements, true, lineNumber);
        }

        /**
         * Parses the {@link #negativePattern} and invokes this method with its types if the match succeeds
         *
         * @param list the list to check
         * @param elements the elements to check if they're in the list
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("negativePattern")
        public PsiContainsObjectCondition parseNegative(
            @NotNull PsiElement<?> list,
            @NotNull PsiElement<?> elements,
            int lineNumber
        ) {
            return create(list, elements, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param list the list to check
         * @param elements the elements to check if they're in the list
         * @param positive if false, the result is inverted
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiContainsObjectCondition create(
            @NotNull PsiElement<?> list,
            @NotNull PsiElement<?> elements,
            boolean positive,
            int lineNumber
        ) {
            return new PsiContainsObjectCondition(list, elements, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
