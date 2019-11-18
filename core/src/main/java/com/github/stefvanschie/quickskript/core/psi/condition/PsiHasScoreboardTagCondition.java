package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
    protected final boolean positive;

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
     * A factory for creating {@link PsiHasScoreboardTagCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching positive psi has scoreboard tags
         */
        @NotNull
        private final SkriptPattern positivePattern =
            SkriptPattern.parse("%entities% (has|have) [the] score[ ]board tag[s] %texts%");

        /**
         * The pattern for matching negative psi has scoreboard tags
         */
        @NotNull
        private final SkriptPattern negativePattern =
            SkriptPattern.parse("%entities% (doesn't|does not|do not|don't) have [the] score[ ]board tag[s] %texts%");

        /**
         * Parses the {@link #positivePattern} and invokes this method with its types if the match succeeds
         *
         * @param entity the entity to check
         * @param tag the tag that needs to be found
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("positivePattern")
        public PsiHasScoreboardTagCondition parsePositive(@NotNull PsiElement<?> entity, @NotNull PsiElement<?> tag,
                                                          int lineNumber) {
            return create(entity, tag, true, lineNumber);
        }

        /**
         * Parses the {@link #negativePattern} and invokes this method with its types if the match succeeds
         *
         * @param entity the entity to check
         * @param tag the tag that needs to be found
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("negativePattern")
        public PsiHasScoreboardTagCondition parseNegative(@NotNull PsiElement<?> entity, @NotNull PsiElement<?> tag,
                                                          int lineNumber) {
            return create(entity, tag, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
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
