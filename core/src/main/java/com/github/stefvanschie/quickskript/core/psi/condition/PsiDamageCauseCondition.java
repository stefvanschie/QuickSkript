package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks whether the damage cause of a damage event is as specified.
 *
 * @since 0.1.0
 */
public class PsiDamageCauseCondition extends PsiElement<Boolean> {

    /**
     * The damage cause to check against.
     */
    @NotNull
    protected final PsiElement<?> damageCause;

    /**
     * If false, the result is inverted.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param damageCause the damage cause to check against
     * @param positive if false, the result is inverted
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiDamageCauseCondition(@NotNull PsiElement<?> damageCause, boolean positive, int lineNumber) {
        super(lineNumber);

        this.damageCause = damageCause;
        this.positive = positive;
    }

    /**
     * A factory for creating {@link PsiDamageCauseCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching positive {@link PsiDamageCauseCondition}s
         */
        @NotNull
        private final SkriptPattern positivePattern = SkriptPattern.parse(
            "[the] damage (was|is|has) [been] (caused|done|made) by %damage cause%"
        );

        /**
         * The pattern for matching negative {@link PsiDamageCauseCondition}s
         */
        @NotNull
        private final SkriptPattern negativePattern = SkriptPattern.parse(
            "[the] damage (was|is|has)n('|o)t [been] (caused|done|made) by %damage cause%"
        );

        /**
         * Parses the {@link #positivePattern} and invokes this method with its types if the match succeeds
         *
         * @param damageCause the damage cause to check against
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("positivePattern")
        public PsiDamageCauseCondition parsePositive(@NotNull PsiElement<?> damageCause, int lineNumber) {
            return create(damageCause, true, lineNumber);
        }

        /**
         * Parses the {@link #negativePattern} and invokes this method with its types if the match succeeds
         *
         * @param damageCause the damage cause to check against
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("negativePattern")
        public PsiDamageCauseCondition parseNegative(@NotNull PsiElement<?> damageCause, int lineNumber) {
            return create(damageCause, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param damageCause the damage cause to check against
         * @param positive if false, the result is inverted
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiDamageCauseCondition create(@NotNull PsiElement<?> damageCause, boolean positive, int lineNumber) {
            return new PsiDamageCauseCondition(damageCause, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
