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
public class PsiChunkCoordinateIsLoadedCondition extends PsiElement<Boolean> {

    /**
     * The x and z coordinate of the chunk.
     */
    @NotNull
    protected final PsiElement<?> x, z;

    /**
     * The world of the chunk.
     */
    @NotNull
    protected final PsiElement<?> world;

    /**
     * If false, the result is negated.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param x the x coordinate of the chunk
     * @param z the z coordinate of the chunk
     * @param world the world of the chunk
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiChunkCoordinateIsLoadedCondition(@NotNull PsiElement<?> x, @NotNull PsiElement<?> z,
                                                  @NotNull PsiElement<?> world, boolean positive, int lineNumber) {
        super(lineNumber);

        this.x = x;
        this.z = z;
        this.world = world;
        this.positive = positive;
    }

    /**
     * A factory for creating {@link PsiChunkCoordinateIsLoadedCondition}s.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param x the x coordinate of the chunk
         * @param z the z coordinate of the chunk
         * @param world the world of the chunk
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("chunk [at] %number%, %number% (in|of) [world] %world% is loaded")
        public PsiChunkCoordinateIsLoadedCondition parsePositive(@NotNull PsiElement<?> x, @NotNull PsiElement<?> z,
                                                                 @NotNull PsiElement<?> world, int lineNumber) {
            return create(x, z, world, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param x the x coordinate of the chunk
         * @param z the z coordinate of the chunk
         * @param world the world of the chunk
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("chunk [at] %number%, %number% (in|of) [world] %world% is(n't| not) loaded")
        public PsiChunkCoordinateIsLoadedCondition parseNegative(@NotNull PsiElement<?> x, @NotNull PsiElement<?> z,
                                                                 @NotNull PsiElement<?> world, int lineNumber) {
            return create(x, z, world, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param x the x coordinate of the chunk
         * @param z the z coordinate of the chunk
         * @param world the world of the chunk
         * @param positive if false, the result is negated
         * @param lineNumber the line number
         * @return the can see condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(value = "_, _, _, _, _ -> new", pure = true)
        public PsiChunkCoordinateIsLoadedCondition create(@NotNull PsiElement<?> x, @NotNull PsiElement<?> z,
                                                          @NotNull PsiElement<?> world, boolean positive,
                                                          int lineNumber) {
            return new PsiChunkCoordinateIsLoadedCondition(x, z, world, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
