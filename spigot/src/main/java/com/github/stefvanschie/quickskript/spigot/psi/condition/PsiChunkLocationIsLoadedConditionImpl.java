package com.github.stefvanschie.quickskript.spigot.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiChunkLocationIsLoadedCondition;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.MultiResult;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.literal.Location;
import com.github.stefvanschie.quickskript.core.util.literal.direction.Direction;
import com.github.stefvanschie.quickskript.spigot.util.LocationUtil;
import org.bukkit.World;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the chunks at the locations are loaded.
 *
 * @since 0.1.0
 */
public class PsiChunkLocationIsLoadedConditionImpl extends PsiChunkLocationIsLoadedCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param directions the directions to determine the chunk locations
     * @param locations the locations to check if the chunks are loaded
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiChunkLocationIsLoadedConditionImpl(@NotNull PsiElement<?> directions, @NotNull PsiElement<?> locations,
                                                  boolean positive, int lineNumber) {
        super(directions, locations, positive, lineNumber);
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        MultiResult<Direction> directions = super.directions.executeMulti(environment, context, Direction.class);
        MultiResult<Location> locations = super.locations.executeMulti(environment, context, Location.class);

        return super.positive == directions.zip(locations, Direction::getRelative)
            .map(LocationUtil::convert)
            .test(location -> {
                World world = location.getWorld();

                if (world == null) {
                    return false;
                }

                return world.isChunkLoaded(location.getBlockX() >> 4, location.getBlockZ() >> 4);
            });
    }

    /**
     * A factory for creating instances of {@link PsiChunkLocationIsLoadedConditionImpl}.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiChunkLocationIsLoadedCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _, _ -> new", pure = true)
        @Override
        public PsiChunkLocationIsLoadedCondition create(@NotNull PsiElement<?> directions,
                                                        @NotNull PsiElement<?> locations, boolean positive,
                                                        int lineNumber) {
            return new PsiChunkLocationIsLoadedConditionImpl(directions, locations, positive, lineNumber);
        }
    }
}
