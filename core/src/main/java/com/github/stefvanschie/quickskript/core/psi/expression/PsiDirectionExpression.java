package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.Type;
import com.github.stefvanschie.quickskript.core.util.literal.direction.RelativeDirection;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Returns a direction.
 *
 * @since 0.1.0
 */
public class PsiDirectionExpression extends PsiElement<RelativeDirection> {

    /**
     * The length of the direction.
     */
    @Nullable
    private PsiElement<?> length;

    /**
     * The yaw of the direction.
     */
    private final int yaw;

    /**
     * Creates a new direction expression. The length of the direction will be one.
     *
     * @param length the length of the direction
     * @param yaw the yaw
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiDirectionExpression(@Nullable PsiElement<?> length, int yaw, int lineNumber) {
        super(lineNumber);

        this.length = length;
        this.yaw = yaw;

        if (this.length == null || this.length.isPreComputed()) {
            super.preComputed = executeImpl(null, null);

            this.length = null;
        }
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected RelativeDirection executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        if (this.length == null) {
            return new RelativeDirection(this.yaw, 0, 1);
        }

        double length = this.length.execute(null, null, Double.class);

        return new RelativeDirection(this.yaw, 0, length);
    }

    /**
     * A factory for creating {@link PsiDirectionExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiDirectionExpression}s
         */
        @NotNull
        private final SkriptPattern pattern = SkriptPattern.parse(
            "[%number% [(block|met(er|re))[s]]] (0¦in[ ]front [of]|0¦forward[s]|180¦behind|180¦backwards|[to the] (90¦right|-90¦left) [of])"
        );

        /**
         * Parses the {@link #pattern} and invokes this method with its types if the match succeeds
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("pattern")
        public PsiDirectionExpression parse(
            @NotNull SkriptMatchResult match,
            @Nullable PsiElement<?> amount,
            int lineNumber
        ) {
            return create(amount, match.getParseMark(), lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param length the length of the direction
         * @param yaw the yaw
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiDirectionExpression create(@Nullable PsiElement<?> length, int yaw, int lineNumber) {
            return new PsiDirectionExpression(length, yaw, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.DIRECTION;
        }
    }
}
