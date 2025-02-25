package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks if an entity is wet.
 *
 * @since 0.1.0
 */
public class PsiEntityIsWetCondition extends PsiElement<Boolean> {

    /**
     * The entities to check if they are wet.
     */
    @NotNull
    protected final PsiElement<?> entities;

    /**
     * If false, the result is negated.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param entities the entities to check if they are wet
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiEntityIsWetCondition(@NotNull PsiElement<?> entities, boolean positive, int lineNumber) {
        super(lineNumber);

        this.entities = entities;
        this.positive = positive;
    }

    /**
     * A factory for creating {@link PsiEntityIsWetCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching positive {@link PsiEntityIsWetCondition}s
         */
        @NotNull
        private final SkriptPattern positivePattern = SkriptPattern.parse("%entities% (is|are) wet");

        /**
         * The pattern for matching negative {@link PsiEntityIsWetCondition}s
         */
        @NotNull
        private final SkriptPattern negativePattern = SkriptPattern.parse(
            "%entities% (isn't|is not|aren't|are not) wet"
        );

        /**
         * Parses the {@link #positivePattern} and invokes this method with its types if the match succeeds
         *
         * @param entities the entities to check if they are wet
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("positivePattern")
        public PsiEntityIsWetCondition parsePositive(@NotNull PsiElement<?> entities, int lineNumber) {
            return create(entities, true, lineNumber);
        }

        /**
         * Parses the {@link #negativePattern} and invokes this method with its types if the match succeeds
         *
         * @param entities the entities to check if they aren't wet
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("negativePattern")
        public PsiEntityIsWetCondition parseNegative(@NotNull PsiElement<?> entities, int lineNumber) {
            return create(entities, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param entities the entities to check if they are wet
         * @param positive if false, the result is negated
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiEntityIsWetCondition create(@NotNull PsiElement<?> entities, boolean positive, int lineNumber) {
            return new PsiEntityIsWetCondition(entities, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
