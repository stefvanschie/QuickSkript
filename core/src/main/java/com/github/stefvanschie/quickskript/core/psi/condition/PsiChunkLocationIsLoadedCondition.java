package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks if the chunks at the locations are loaded.
 *
 * @since 0.1.0
 */
public class PsiChunkLocationIsLoadedCondition extends PsiElement<Boolean> {

    /**
     * The directions to determine the chunk locations.
     */
    @NotNull
    protected final PsiElement<?> directions;

    /**
     * The locations to check if the chunks are loaded.
     */
    @NotNull
    protected final PsiElement<?> locations;

    /**
     * If false, the result is negated.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param directions the directions to determine the chunk locations
     * @param locations the locations to check if the chunks are loaded
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiChunkLocationIsLoadedCondition(@NotNull PsiElement<?> directions, @NotNull PsiElement<?> locations,
                                                boolean positive, int lineNumber) {
        super(lineNumber);

        this.directions = directions;
        this.locations = locations;
        this.positive = positive;
    }

    /**
     * A factory for creating {@link PsiChunkLocationIsLoadedCondition}s.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param directions the directions to determine the chunk locations
         * @param locations the locations to check if the chunks are loaded
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("chunk[s] %directions% %locations% (is|are) loaded")
        public PsiChunkLocationIsLoadedCondition parsePositive(@NotNull PsiElement<?> directions,
                                                               @NotNull PsiElement<?> locations, int lineNumber) {
            return create(directions, locations, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param directions the directions to determine the chunk locations
         * @param locations the locations to check if the chunks are loaded
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("chunk[s] %directions% %locations% (is|are)(n't| not) loaded")
        public PsiChunkLocationIsLoadedCondition parseNegative(@NotNull PsiElement<?> directions,
                                                               @NotNull PsiElement<?> locations, int lineNumber) {
            return create(directions, locations, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param directions the directions to determine the chunk locations
         * @param locations the locations to check if the chunks are loaded
         * @param positive if false, the result is negated
         * @param lineNumber the line number
         * @return the can see condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiChunkLocationIsLoadedCondition create(@NotNull PsiElement<?> directions,
                                                        @NotNull PsiElement<?> locations, boolean positive,
                                                        int lineNumber) {
            return new PsiChunkLocationIsLoadedCondition(directions, locations, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
