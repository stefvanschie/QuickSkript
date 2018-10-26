package com.github.stefvanschie.quickskript.psi.expression;

import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.skript.SkriptLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Returns random numbers within a specified range.
 * The min parameter might return values greater than the ones returned by max, this has to be handled.
 * Both ends of the specified range are inclusive.
 *
 * @since 0.1.0
 */
public class PsiRandomNumberExpression extends PsiElement<Number> {

    /**
     * Stores whether an integer or a floating-point value should be returned
     */
    private final boolean integer;

    /**
     * The min and the max values. They may need to be switched to make their names truthful
     */
    private PsiElement<?> min, max;

    /**
     * Creates a new random number expression
     *
     * @param min one of the bounds of the valid value range
     * @param max the other bound of the valid value range
     * @since 0.1.0
     */
    private PsiRandomNumberExpression(boolean integer, @NotNull PsiElement<?> min, @NotNull PsiElement<?> max, int lineNumber) {
        super(lineNumber);

        this.integer = integer;
        this.min = min;
        this.max = max;

        if (this.min.isPreComputed() && this.max.isPreComputed()) {
            Number minNumber = this.min.execute(null, Number.class);
            Number maxNumber = this.max.execute(null, Number.class);

            if (minNumber.doubleValue() == maxNumber.doubleValue()) {
                preComputed = minNumber;
                return;
            }

            if (minNumber.doubleValue() > maxNumber.doubleValue()) {
                PsiElement<?> temp = this.max;
                this.max = this.min;
                this.min = temp;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    protected Number executeImpl(@Nullable Context context) {
        Number minNumber = min.execute(context, Number.class);
        Number maxNumber = max.execute(context, Number.class);

        if (minNumber.doubleValue() > maxNumber.doubleValue()) {
            Number temp = maxNumber;
            maxNumber = minNumber;
            minNumber = temp;
        }

        ThreadLocalRandom random = ThreadLocalRandom.current();

        return integer ? random.nextLong(minNumber.longValue(), maxNumber.longValue() + 1)
                : random.nextDouble(minNumber.doubleValue(), maxNumber.doubleValue() + Double.MIN_VALUE);
    }

    /**
     * A factory for creating random number expressions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiRandomNumberExpression> {

        /**
         * The pattern for matching random number expressions with integer values
         */
        private final Pattern integerPattern = Pattern.compile("(a )?random integer (from|between) (-?\\d+) (to|and) (-?\\d+)");

        /**
         * The pattern for matching random number expressions with floating point values
         */
        private final Pattern numberPattern = Pattern.compile("(a )?random number (from|between) (-?\\d+(?:.\\d+)?) (to|and) (-?\\d+(?:.\\d+)?)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiRandomNumberExpression tryParse(@NotNull String text, int lineNumber) {
            Matcher matcher = integerPattern.matcher(text);
            boolean integer = true;

            if (!matcher.matches()) {
                matcher = numberPattern.matcher(text);

                if (matcher.matches())
                    integer = false;
                else
                    return null;
            }

            return new PsiRandomNumberExpression(integer,
                    SkriptLoader.get().forceParseElement(matcher.group(3), lineNumber),
                    SkriptLoader.get().forceParseElement(matcher.group(5), lineNumber), lineNumber);
        }
    }
}
