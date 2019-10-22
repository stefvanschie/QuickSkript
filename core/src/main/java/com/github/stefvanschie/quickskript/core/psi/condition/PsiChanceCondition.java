package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.pattern.group.OptionalGroup;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ThreadLocalRandom;

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
    @NotNull
    private final PsiElement<?> number;

    /**
     * Whether the number should be interpreted as a percentage or a floating point number between 0 and 1.
     */
    private final boolean asPercentage;

    /**
     * Creates a new element with the given line number
     *
     * @param number the number for determining the chance
     * @param asPercentage whether the number should be treated as a percentage or a floating point number
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiChanceCondition(@NotNull PsiElement<?> number, boolean asPercentage, int lineNumber) {
        super(lineNumber);

        this.number = number;
        this.asPercentage = asPercentage;
    }

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
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiChanceCondition}s
         */
        @SuppressWarnings("HardcodedFileSeparator")
        @NotNull
        private final SkriptPattern pattern = SkriptPattern.parse("chance of %number%[\\%]");

        /**
         * Parses the {@link #pattern} and invokes this method with its types if the match succeeds
         *
         * @param result the match state
         * @param chance the chance
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("pattern")
        private PsiChanceCondition parse(@NotNull SkriptMatchResult result, @NotNull PsiElement<?> chance,
                                           int lineNumber) {
            boolean asPercentage = result.getMatchedGroups().stream()
                .map(Pair::getX)
                .anyMatch(group -> group instanceof OptionalGroup);

            return create(chance, asPercentage, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param number the number
         * @param asPercentage whether the number should be treated as a percentage
         * @param lineNumber the line number
         * @return the can see condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiChanceCondition create(@NotNull PsiElement<?> number, boolean asPercentage, int lineNumber) {
            return new PsiChanceCondition(number, asPercentage, lineNumber);
        }
    }
}
