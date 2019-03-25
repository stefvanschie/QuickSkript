package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A condition based around the chance of this resulting in true. This cannot be pre computed, since it is expected that
 * the result differs per execution.
 *
 * @since 0.1.0
 */
public class PsiChanceCondition extends PsiElement<Boolean> {

    /**
     * The number for calculating the chance
     */
    private PsiElement<?> number;

    /**
     * Whether the number should be interpreted as a percentage or a floating point number between 0 and 1.
     */
    private boolean asPercentage;

    /**
     * Creates a new element with the given line number
     *
     * @param number the number for determining the chance
     * @param asPercentage whether the number should be treated as a percentage or a floating point number
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiChanceCondition(PsiElement<?> number, boolean asPercentage, int lineNumber) {
        super(lineNumber);

        this.number = number;
        this.asPercentage = asPercentage;
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        return ThreadLocalRandom.current().nextDouble(asPercentage ? 100 : 1) < number.execute(context, Double.class);
    }

    /**
     * A factory for creating {@link PsiChanceCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiChanceCondition> {

        /**
         * The pattern for matching {@link PsiChanceCondition}s
         */
        private final Pattern pattern = Pattern.compile("chance of ([\\s\\S]+?)(%)?$");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Contract(pure = true)
        @Override
        public PsiChanceCondition tryParse(@NotNull String text, int lineNumber) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches()) {
                return null;
            }

            PsiElement<?> number = SkriptLoader.get().forceParseElement(matcher.group(1), lineNumber);

            return create(number, matcher.groupCount() >= 2, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param number the number
         * @param asPercentage whether the number should be treated as a percentage
         * @param lineNumber the line number
         * @return the can see condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiChanceCondition create(PsiElement<?> number, boolean asPercentage, int lineNumber) {
            return new PsiChanceCondition(number, asPercentage, lineNumber);
        }
    }
}
