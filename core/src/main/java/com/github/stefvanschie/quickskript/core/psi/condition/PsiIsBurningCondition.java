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
 * Checks whether an entity is burning. This cannot be pre computed, since an entity can get burned and/or extinguished
 * during game play.
 *
 * @since 0.1.0
 */
public class PsiIsBurningCondition extends PsiElement<Boolean> {

    /**
     * The entity to check whether they are burned
     */
    @NotNull
    protected final PsiElement<?> entity;

    /**
     * False if the execution result needs to be inverted
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param entity the entity to check whether they are burning
     * @param positive false if the execution result needs to be inverted
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiIsBurningCondition(@NotNull PsiElement<?> entity, boolean positive, int lineNumber) {
        super(lineNumber);

        this.entity = entity;
        this.positive = positive;
    }

    /**
     * @throws UnsupportedOperationException implementation is required for this functionality
     */
    @Nullable
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        throw new UnsupportedOperationException("Cannot execute expression without implementation.");
    }

    /**
     * A factory for creating {@link PsiIsBurningCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiIsBurningCondition> {

        /**
         * The pattern for matching positive is burning conditions
         */
        @NotNull
        private final Pattern positivePattern =
            Pattern.compile("(?<entity>[\\s\\S]+) (?:is|are) (?:burning|ignited|on fire)");

        /**
         * The pattern for matching negative is burning conditions
         */
        @NotNull
        private final Pattern negativePattern =
            Pattern.compile("(?<entity>[\\s\\S]+) (?:isn't|is not|aren't|are not) (?:burning|ignited|on fire)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Contract(pure = true)
        @Override
        public PsiIsBurningCondition tryParse(@NotNull String text, int lineNumber) {
            var skriptLoader = SkriptLoader.get();

            Matcher positiveMatcher = positivePattern.matcher(text);

            if (positiveMatcher.matches()) {
                String entityGroup = positiveMatcher.group("entity");

                PsiElement<?> entity = skriptLoader.forceParseElement(entityGroup, lineNumber);

                return create(entity, true, lineNumber);
            }

            Matcher negativeMatcher = negativePattern.matcher(text);

            if (negativeMatcher.matches()) {
                String entityGroup = negativeMatcher.group("entity");

                PsiElement<?> entity = skriptLoader.forceParseElement(entityGroup, lineNumber);

                return create(entity, false, lineNumber);
            }

            return null;
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param entity the entity to check whether they are burning
         * @param positive false if the result of the execution should be negated, true otherwise
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiIsBurningCondition create(@NotNull PsiElement<?> entity, boolean positive, int lineNumber) {
            return new PsiIsBurningCondition(entity, positive, lineNumber);
        }
    }
}
