package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import com.github.stefvanschie.quickskript.core.util.literal.World;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks whether respawn anchors work in the given worlds.
 *
 * @since 0.1.0
 */
public class PsiDoRespawnAnchorsWorkCondition extends PsiElement<Boolean> {

    /**
     * The worlds to check for.
     */
    @NotNull
    protected final PsiElement<? extends World> worlds;

    /**
     * If false, the result is inverted.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param worlds the worlds to check for
     * @param positive if false, the result is inverted
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiDoRespawnAnchorsWorkCondition(
        @NotNull PsiElement<? extends World> worlds,
        boolean positive,
        int lineNumber
    ) {
        super(lineNumber);

        this.worlds = worlds;
        this.positive = positive;
    }

    /**
     * A factory for creating {@link PsiDoRespawnAnchorsWorkCondition}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching positive {@link PsiDoRespawnAnchorsWorkCondition}s
         */
        @NotNull
        private final SkriptPattern positivePattern = SkriptPattern.parse("respawn anchors [do] work in %worlds%");

        /**
         * The pattern for matching negative {@link PsiDoRespawnAnchorsWorkCondition}s
         */
        @NotNull
        private final SkriptPattern negativePattern = SkriptPattern.parse(
            "respawn anchors do(n't| not) work in %worlds%"
        );

        /**
         * Parses the {@link #positivePattern} and invokes this method with its types if the match succeeds
         *
         * @param worlds the worlds to check for
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("positivePattern")
        public PsiDoRespawnAnchorsWorkCondition parsePositive(
            @NotNull PsiElement<? extends World> worlds,
            int lineNumber
        ) {
            return create(worlds, true, lineNumber);
        }

        /**
         * Parses the {@link #negativePattern} and invokes this method with its types if the match succeeds
         *
         * @param worlds the worlds to check for
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("negativePattern")
        public PsiDoRespawnAnchorsWorkCondition parseNegative(
            @NotNull PsiElement<? extends World> worlds,
            int lineNumber
        ) {
            return create(worlds, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param worlds the worlds to check for
         * @param positive if false, the result is inverted
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiDoRespawnAnchorsWorkCondition create(
            @NotNull PsiElement<? extends World> worlds,
            boolean positive,
            int lineNumber
        ) {
            return new PsiDoRespawnAnchorsWorkCondition(worlds, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
