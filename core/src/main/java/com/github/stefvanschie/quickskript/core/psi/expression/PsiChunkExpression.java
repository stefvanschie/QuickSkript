package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.expression.util.Resettable;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.MultiResult;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.literal.Chunk;
import com.github.stefvanschie.quickskript.core.util.literal.Location;
import com.github.stefvanschie.quickskript.core.util.literal.direction.Direction;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the chunks from locations.
 *
 * @since 0.1.0
 */
public class PsiChunkExpression extends PsiElement<MultiResult<Chunk>> implements Resettable {

    /**
     * The directions of the locations, may be null if omitted.
     */
    @Nullable
    protected final PsiElement<?> directions;

    /**
     * The locations to get the chunks at.
     */
    @NotNull
    protected final PsiElement<?> locations;

    /**
     * Creates a new element with the given line number
     *
     * @param directions the directions of the locations, may be null if omitted
     * @param locations the locations to get the chunks at
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiChunkExpression(@Nullable PsiElement<?> directions, @NotNull PsiElement<?> locations, int lineNumber) {
        super(lineNumber);

        this.directions = directions;
        this.locations = locations;
    }

    @NotNull
    @Override
    protected MultiResult<Chunk> executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        MultiResult<Location> locations = this.locations.executeMulti(environment, context, Location.class);

        if (this.directions != null) {
            MultiResult<Direction> directions = this.directions.executeMulti(environment, context, Direction.class);

            locations = directions.zip(locations, Direction::getRelative);
        }

        return locations.map(Chunk::new).distinct();
    }

    /**
     * A factory for creating {@link PsiChunkExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param directions the directions of the locations
         * @param locations the locations to get the chunks at
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("[all [[of] the]|the] chunk[s] (of|%directions%) %locations%")
        public PsiChunkExpression parse(@Nullable PsiElement<?> directions, @NotNull PsiElement<?> locations,
                                        int lineNumber) {
            return create(directions, locations, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param locations the locations to get the chunks at
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%locations%'[s] chunk[s]")
        public PsiChunkExpression parse(@NotNull PsiElement<?> locations, int lineNumber) {
            return create(null, locations, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param directions the directions of the locations, may be null if omitted
         * @param locations the locations to get the chunks at
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiChunkExpression create(@Nullable PsiElement<?> directions, @NotNull PsiElement<?> locations,
                                         int lineNumber) {
            return new PsiChunkExpression(directions, locations, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public String getType() {
            return "chunks";
        }
    }
}