package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import com.github.stefvanschie.quickskript.core.util.literal.direction.AbsoluteDirection;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Returns an absolute direction that is zero on all axes.
 *
 * @since 0.1.0
 */
public class PsiAtExpression extends PsiPrecomputedHolder<@NotNull AbsoluteDirection> {

    /**
     * A direction that is zero on all axes.
     */
    @NotNull
    private static final AbsoluteDirection ZERO_DIRECTION = new AbsoluteDirection(0, 0, 0);

    /**
     * Creates a new at expression with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiAtExpression(int lineNumber) {
        super(ZERO_DIRECTION, lineNumber);
    }

    /**
     * A factory for creating {@link PsiAtExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiAtExpression}s
         */
        @NotNull
        private static final SkriptPattern PATTERN = SkriptPattern.parse("at");

        /**
         * Parses the {@link #PATTERN} and invokes this method with its types if the match succeeds
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("PATTERN")
        public PsiAtExpression parse(int lineNumber) {
            return create(lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiAtExpression create(int lineNumber) {
            return new PsiAtExpression(lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.DIRECTION;
        }
    }
}
