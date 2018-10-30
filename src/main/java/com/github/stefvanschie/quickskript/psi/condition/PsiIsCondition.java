package com.github.stefvanschie.quickskript.psi.condition;

import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.skript.SkriptLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

/**
 * Checks to see if values on both sides are or aren't equal. If this value is pre computed, we'll throw a warning to
 * signal the user that the expression is unneeded and can be replaced by a direct boolean value.
 *
 * @since 0.1.0
 */
public class PsiIsCondition extends PsiElement<Boolean> {

    /**
     * The left and right side which will be used for comparison
     */
    private final PsiElement<?> leftSide, rightSide;

    /**
     * True if the result stays the same, false if it needs to be inverted
     */
    private final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param leftSide the left hand side of the expression
     * @param rightSide the right hand side of the expression
     * @param positive true if the value stays the same, false if it will be inverted
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsCondition(PsiElement<?> leftSide, PsiElement<?> rightSide, boolean positive, int lineNumber) {
        super(lineNumber);

        this.leftSide = leftSide;
        this.rightSide = rightSide;
        this.positive = positive;

        if (this.leftSide.isPreComputed() && this.rightSide.isPreComputed()) {
            //TODO: Show warning
        }
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        Object leftResult = leftSide.execute(context);
        Object rightResult = rightSide.execute(context);

        if (leftResult == null && rightResult == null) {
            return true;
        } else if (leftResult == null || rightResult == null) {
            return false;
        }

        return positive == leftResult.equals(rightResult);
    }

    /**
     * A factory for creating {@link PsiIsCondition}s.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiIsCondition> {

        /**
         * A pattern for matching positive elements
         */
        private static final Pattern POSITIVE_PATTERN =
            Pattern.compile("([\\s\\S]+) (?:(?:is)|(?:are)|(?:=)) (?:(?:equal to)|(?:the same as) )?([\\s\\S]+)");

        /**
         * A pattern for matching negative elements
         */
        private static final Pattern NEGATIVE_PATTERN = Pattern.compile(
            "([\\s\\S]+) (?:(?:(?:(?:is)|(?:are)) (?:(?:not)|(?:neither)))|(?:!=)|(?:isn't)|(?:aren't)) (?:(?:equal to)|(?:the same as) )?([\\s\\S]+)"
        );

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiIsCondition tryParse(@NotNull String text, int lineNumber) {
            var skriptLoader = SkriptLoader.get();

            var negativeMatcher = NEGATIVE_PATTERN.matcher(text);

            if (negativeMatcher.matches()) {
                var leftSideElement = skriptLoader.forceParseElement(negativeMatcher.group(1), lineNumber);
                var rightSideElement = skriptLoader.forceParseElement(negativeMatcher.group(2), lineNumber);

                return new PsiIsCondition(leftSideElement, rightSideElement, false, lineNumber);
            }

            var positiveMatcher = POSITIVE_PATTERN.matcher(text);

            if (positiveMatcher.matches()) {
                var leftSideElement = skriptLoader.forceParseElement(positiveMatcher.group(1), lineNumber);
                var rightSideElement = skriptLoader.forceParseElement(positiveMatcher.group(2), lineNumber);

                return new PsiIsCondition(leftSideElement, rightSideElement, true, lineNumber);
            }

            return null;
        }
    }
}
