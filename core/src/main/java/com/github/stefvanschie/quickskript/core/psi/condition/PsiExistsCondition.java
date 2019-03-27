package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiExistsCondition(PsiElement<?> object, boolean positive, int lineNumber) {
        super(lineNumber);

        this.object = object;
        this.positive = positive;

        if (object.isPreComputed()) {
            preComputed = executeImpl(null);
            this.object = null;
        }
    }

    /**
     * {@inheritDoc}
     */
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
    public static class Factory implements PsiElementFactory<PsiExistsCondition> {

        /**
         * The pattern for matching positive {@link PsiExistsCondition}s
         */
        private Pattern positivePattern = Pattern.compile("([\\s\\S]+) (?:exists?|(?:is|are) set)");

        /**
         * The pattern for matching negative {@link PsiExistsCondition}s
         */
        private Pattern negativePattern = Pattern.compile("([\\s\\S]+) (?:do(es)?(?:n't| not) exist|(?:is|are)(?:n't| not) set)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Contract(pure = true)
        @Override
        public PsiExistsCondition tryParse(@NotNull String text, int lineNumber) {
            Matcher positiveMatcher = positivePattern.matcher(text);

            if (positiveMatcher.matches()) {
                PsiElement<?> object = SkriptLoader.get().forceParseElement(positiveMatcher.group(1), lineNumber);

                return create(object, true, lineNumber);
            }

            Matcher negativeMatcher = negativePattern.matcher(text);

            if (negativeMatcher.matches()) {
                PsiElement<?> object = SkriptLoader.get().forceParseElement(negativeMatcher.group(1), lineNumber);

                return create(object, false, lineNumber);
            }

            return null;
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param object the object
         * @param positive false if the result should be negated
         * @param lineNumber the line number
         * @return the can see condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiExistsCondition create(@NotNull PsiElement<?> object, boolean positive, int lineNumber) {
            return new PsiExistsCondition(object, positive, lineNumber);
        }
    }
}
