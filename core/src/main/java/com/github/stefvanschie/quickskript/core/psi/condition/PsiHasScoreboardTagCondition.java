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
 * Checks to see if the specified entity has a specific scoreboard tag. This cannot be pre computed, since this may
 * change during game play.
 *
 * @since 0.1.0
 */
public class PsiHasScoreboardTagCondition extends PsiElement<Boolean> {

    /**
     * The entity to check the {@link #tag} for
     */
    @NotNull
    protected final PsiElement<?> entity;

    /**
     * The tag we're looking for
     */
    @NotNull
    protected final PsiElement<?> tag;

    /**
     * False if the result needs to be inverted
     */
    protected boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param entity the entity to check, see {@link #entity}
     * @param tag the tag that we need to find, see {@link #tag}
     * @param positive false if the result needs to be inverted
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiHasScoreboardTagCondition(@NotNull PsiElement<?> entity, @NotNull PsiElement<?> tag, boolean positive,
                                           int lineNumber) {
        super(lineNumber);

        this.entity = entity;
        this.tag = tag;
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
     * A factory for creating {@link PsiHasScoreboardTagCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiHasScoreboardTagCondition> {

        /**
         * The pattern for matching positive psi has scoreboard tags
         */
        @NotNull
        private Pattern positivePattern = Pattern
            .compile("(?<entity>[\\s\\S]+) (?:has|have)(?: the)? score ?board tags? (?<tag>[\\s\\S]+)");

        /**
         * The pattern for matching negative psi has scoreboard tags
         */
        @NotNull
        private Pattern negativePattern = Pattern
            .compile("(?<entity>[\\s\\S]+) (?:doesn't|does not|do not|don't) have(?: the)? score ?board tags? (?<tag>[\\s\\S]+)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Contract(pure = true)
        @Override
        public PsiHasScoreboardTagCondition tryParse(@NotNull String text, int lineNumber) {
            var skriptLoader = SkriptLoader.get();

            Matcher positiveMatcher = positivePattern.matcher(text);

            if (positiveMatcher.matches()) {
                String entityGroup = positiveMatcher.group("entity");

                PsiElement<?> entity = skriptLoader.forceParseElement(entityGroup, lineNumber);
                PsiElement<?> tag = skriptLoader.forceParseElement(positiveMatcher.group("tag"), lineNumber);

                return create(entity, tag, true, lineNumber);
            }

            Matcher negativeMatcher = negativePattern.matcher(text);

            if (negativeMatcher.matches()) {
                String entityGroup = negativeMatcher.group("entity");

                PsiElement<?> entity = skriptLoader.forceParseElement(entityGroup, lineNumber);
                PsiElement<?> tag = skriptLoader.forceParseElement(negativeMatcher.group("tag"), lineNumber);

                return create(entity, tag, false, lineNumber);
            }

            return null;
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param entity the entity to check
         * @param tag the tag that needs to be found
         * @param positive false if the result of the execution should be negated, true otherwise
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiHasScoreboardTagCondition create(@NotNull PsiElement<?> entity, @NotNull PsiElement<?> tag,
                                                   boolean positive, int lineNumber) {
            return new PsiHasScoreboardTagCondition(entity, tag, positive, lineNumber);
        }
    }
}
