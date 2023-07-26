package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.Type;
import com.github.stefvanschie.quickskript.core.util.literal.direction.AbsoluteDirection;
import com.github.stefvanschie.quickskript.core.util.literal.direction.RelativeDirection;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Returns an absolute direction.
 *
 * @since 0.1.0
 */
public class PsiAbsoluteDirectionExpression extends PsiElement<AbsoluteDirection> {

    /**
     * The length of the direction.
     */
    @Nullable
    private PsiElement<?> length;

    /**
     * The x, y, and z of the direction.
     */
    private final int x, y, z;

    /**
     * An absolute direction to which this direction is relative to.
     */
    @Nullable
    private PsiElement<?> relativeDirection;

    /**
     * Creates a new direction expression. The length of the direction will be one.
     *
     * @param length the length of the direction
     * @param x the x component
     * @param y the y component
     * @param z the z component
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiAbsoluteDirectionExpression(
        @Nullable PsiElement<?> length,
        int x,
        int y,
        int z,
        @Nullable PsiElement<?> relativeDirection,
        int lineNumber
    ) {
        super(lineNumber);

        this.length = length;
        this.x = x;
        this.y = y;
        this.z = z;
        this.relativeDirection = relativeDirection;

        boolean lengthKnown = this.length == null || this.length.isPreComputed();
        boolean relativeDirectionKnown = this.relativeDirection == null || this.relativeDirection.isPreComputed();

        if (lengthKnown && relativeDirectionKnown) {
            super.preComputed = executeImpl(null, null);

            this.length = null;
            this.relativeDirection = null;
        }
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected AbsoluteDirection executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        double length = 1;

        if (this.length != null) {
            length = this.length.execute(null, null, Double.class);
        }

        AbsoluteDirection relative = new AbsoluteDirection(0, 0, 0);

        if (this.relativeDirection != null) {
            relative = this.relativeDirection.execute(null, null, AbsoluteDirection.class);
        }

        return new AbsoluteDirection(this.x * length, this.y * length, this.z * length).add(relative);
    }

    /**
     * A factory for creating {@link PsiAbsoluteDirectionExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The north pattern for matching {@link PsiAbsoluteDirectionExpression}s
         */
        @NotNull
        private final SkriptPattern patternNorth = SkriptPattern.parse(
            "[%number% [(block|met(er|re))[s]] [to the]] north[[-| ](1¦east|-1¦west)][(ward[s|ly]|er[n|ly])] [of] [%direction%]"
        );

        /**
         * The south pattern for matching {@link PsiAbsoluteDirectionExpression}s
         */
        @NotNull
        private final SkriptPattern patternSouth = SkriptPattern.parse(
            "[%number% [(block|met(er|re))[s]] [to the]] south[[-| ](1¦east|-1¦west)][(ward[s|ly]|er[n|ly])] [of] [%direction%]"
        );

        /**
         * The east/west pattern for matching {@link PsiAbsoluteDirectionExpression}s
         */
        @NotNull
        private final SkriptPattern patternEastWest = SkriptPattern.parse(
            "[%number% [(block|met(er|re))[s]] [to the]] (1¦east|-1¦west)[(ward[s|ly]|er[n|ly])] [of] [%direction%]"
        );

        /**
         * The up/down pattern for matching {@link PsiAbsoluteDirectionExpression}s
         */
        @NotNull
        private final SkriptPattern patternUpDown = SkriptPattern.parse(
            "[%number% [(block|met(er|re))[s]] [to the]] (1¦above|1¦over|(1¦up|-1¦down)[ward[s|ly]]|-1¦below|-1¦under[neath]|-1¦beneath) [%direction%]"
        );

        /**
         * Parses the {@link #patternNorth} and invokes this method with its types if the match succeeds
         *
         * @param match the pattern match
         * @param amount the amount
         * @param relative the relative direction
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("patternNorth")
        public PsiAbsoluteDirectionExpression parseNorth(
            @NotNull SkriptMatchResult match,
            @Nullable PsiElement<?> amount,
            @Nullable PsiElement<?> relative,
            int lineNumber
        ) {
            return create(amount, match.getParseMark(), 0, -1, relative, lineNumber);
        }

        /**
         * Parses the {@link #patternSouth} and invokes this method with its types if the match succeeds
         *
         * @param match the pattern match
         * @param amount the amount
         * @param relative the relative direction
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("patternSouth")
        public PsiAbsoluteDirectionExpression parseSouth(
            @NotNull SkriptMatchResult match,
            @Nullable PsiElement<?> amount,
            @Nullable PsiElement<?> relative,
            int lineNumber
        ) {
            return create(amount, match.getParseMark(), 0, 1, relative, lineNumber);
        }

        /**
         * Parses the {@link #patternEastWest} and invokes this method with its types if the match succeeds
         *
         * @param match the pattern match
         * @param amount the amount
         * @param relative the relative direction
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("patternEastWest")
        public PsiAbsoluteDirectionExpression parseEastWest(
            @NotNull SkriptMatchResult match,
            @Nullable PsiElement<?> amount,
            @Nullable PsiElement<?> relative,
            int lineNumber
        ) {
            return create(amount, match.getParseMark(), 0, 0, relative, lineNumber);
        }

        /**
         * Parses the {@link #patternUpDown} and invokes this method with its types if the match succeeds
         *
         * @param match the pattern match
         * @param amount the amount
         * @param relative the relative direction
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("patternUpDown")
        public PsiAbsoluteDirectionExpression parseUpDown(
            @NotNull SkriptMatchResult match,
            @Nullable PsiElement<?> amount,
            @Nullable PsiElement<?> relative,
            int lineNumber
        ) {
            return create(amount, 0, match.getParseMark(), 0, relative, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param length the length of the direction
         * @param x the x component
         * @param y the y component
         * @param z the z component
         * @param relativeDirection the relative direction of the resulting direction
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiAbsoluteDirectionExpression create(
            @Nullable PsiElement<?> length,
            int x,
            int y,
            int z,
            @Nullable PsiElement<?> relativeDirection,
            int lineNumber
        ) {
            return new PsiAbsoluteDirectionExpression(length, x, y, z, relativeDirection, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.DIRECTION;
        }
    }
}
