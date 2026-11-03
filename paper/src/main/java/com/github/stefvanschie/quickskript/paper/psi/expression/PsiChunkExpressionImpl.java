package com.github.stefvanschie.quickskript.paper.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiChunkExpression;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.MultiResult;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.literal.Location;
import com.github.stefvanschie.quickskript.core.util.literal.direction.Direction;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the chunks from locations.
 *
 * @since 0.1.0
 */
public class PsiChunkExpressionImpl extends PsiChunkExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param directions the directions of the locations, may be null if omitted
     * @param locations the locations to get the chunks at
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiChunkExpressionImpl(@Nullable PsiElement<?> directions, @NotNull PsiElement<?> locations,
                                   int lineNumber) {
        super(directions, locations, lineNumber);
    }

    @Override
    public void reset(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        class ChunkImpl {
            private final World world;

            private final int x, z;

            private ChunkImpl(@NotNull Location location) {
                this.world = Bukkit.getWorld(location.getWorld().getName());
                this.x = (int) Math.floor(location.getX()) >> 4;
                this.z = (int) Math.floor(location.getZ()) >> 4;
            }
        }

        MultiResult<Location> locations = super.locations.executeMulti(environment, context, Location.class);

        if (super.directions != null) {
            MultiResult<Direction> directions = super.directions.executeMulti(environment, context, Direction.class);

            locations = directions.zip(locations, Direction::getRelative);
        }

        for (ChunkImpl chunk : locations.map(ChunkImpl::new).distinct()) {
            World world = chunk.world;

            if (world == null) {
                continue;
            }

            //noinspection removal
            world.regenerateChunk(chunk.x, chunk.z);
        }
    }

    /**
     * A factory for creating {@link PsiChunkExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiChunkExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiChunkExpression create(@Nullable PsiElement<?> directions, @NotNull PsiElement<?> locations,
                                         int lineNumber) {
            return new PsiChunkExpressionImpl(directions, locations, lineNumber);
        }
    }
}