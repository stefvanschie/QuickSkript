package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks if the specified player is able to build.
 *
 * @since 0.1.0
 */
public class PsiCanBuildCondition extends PsiElement<Boolean> {

    /**
     * The player to check if they can build.
     */
    @NotNull
    protected final PsiElement<?> player;

    /**
     * The direction relative to the location.
     */
    @NotNull
    protected final PsiElement<?> direction;

    /**
     * The location.
     */
    @NotNull
    protected final PsiElement<?> location;

    /**
     * If this value is true, the result of the computation will stay the same. Otherwise, the result will be inverted.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to check if they can build
     * @param direction the direction relative to the location
     * @param location the location
     * @param positive false if the result of the execution should be negated, true otherwise
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiCanBuildCondition(
        @NotNull PsiElement<?> player,
        @NotNull PsiElement<?> direction,
        @NotNull PsiElement<?> location,
        boolean positive,
        int lineNumber
    ) {
        super(lineNumber);

        this.player = player;
        this.direction = direction;
        this.location = location;
        this.positive = positive;
    }

    /**
     * A factory for creating {@link PsiCanBuildCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching positive can build conditions.
         */
        @NotNull
        private final SkriptPattern positivePattern = SkriptPattern.parse(
            "%players% (can|(is|are) allowed to) build %directions% %locations%"
        );

        /**
         * The pattern for matching negative can build conditions.
         */
        @NotNull
        private final SkriptPattern negativePattern = SkriptPattern.parse(
            "%players% (can('t|not)|(is|are)(n't| not) allowed to) build %directions% %locations%"
        );

        /**
         * Parses the {@link #positivePattern} and invokes this method with its types if the match succeeds
         *
         * @param player the player to check if they can build
         * @param direction the direction relative to the location
         * @param location the location
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("positivePattern")
        public PsiCanBuildCondition parsePositive(
            @NotNull PsiElement<?> player,
            @NotNull PsiElement<?> direction,
            @NotNull PsiElement<?> location,
            int lineNumber
        ) {
            return create(player, direction, location, true, lineNumber);
        }

        /**
         * Parses the {@link #negativePattern} and invokes this method with its types if the match succeeds
         *
         * @param player the player to check if they can build
         * @param direction the direction relative to the location
         * @param location the location
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("negativePattern")
        public PsiCanBuildCondition parseNegative(
            @NotNull PsiElement<?> player,
            @NotNull PsiElement<?> direction,
            @NotNull PsiElement<?> location,
            int lineNumber
        ) {
            return create(player, direction, location, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param player the player to check if they can build
         * @param direction the direction relative to the location
         * @param location the location
         * @param positive false if the result of the execution should be negated, true otherwise
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiCanBuildCondition create(
            @NotNull PsiElement<?> player,
            @NotNull PsiElement<?> direction,
            @NotNull PsiElement<?> location,
            boolean positive,
            int lineNumber
        ) {
            return new PsiCanBuildCondition(player, direction, location, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
