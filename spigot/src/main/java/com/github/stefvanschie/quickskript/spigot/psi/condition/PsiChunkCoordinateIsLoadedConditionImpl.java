package com.github.stefvanschie.quickskript.spigot.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiChunkCoordinateIsLoadedCondition;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.literal.World;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the chunks at the locations are loaded.
 *
 * @since 0.1.0
 */
public class PsiChunkCoordinateIsLoadedConditionImpl extends PsiChunkCoordinateIsLoadedCondition {

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
    private PsiChunkCoordinateIsLoadedConditionImpl(@NotNull PsiElement<?> x, @NotNull PsiElement<?> z,
                                                    @NotNull PsiElement<?> world, boolean positive, int lineNumber) {
        super(x, z, world, positive, lineNumber);
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        int x = super.x.execute(environment, context, Number.class).intValue();
        int z = super.z.execute(environment, context, Number.class).intValue();
        World world = super.world.execute(environment, context, World.class);
        org.bukkit.World bukkitWorld = Bukkit.getWorld(world.getName());

        if (bukkitWorld == null) {
            return false;
        }

        return bukkitWorld.isChunkLoaded(x, z);
    }

    /**
     * A factory for creating instances of {@link PsiChunkCoordinateIsLoadedConditionImpl}.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiChunkCoordinateIsLoadedCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _, _, _ -> new", pure = true)
        @Override
        public PsiChunkCoordinateIsLoadedCondition create(@NotNull PsiElement<?> x, @NotNull PsiElement<?> z,
                                                          @NotNull PsiElement<?> world, boolean positive,
                                                          int lineNumber) {
            return new PsiChunkCoordinateIsLoadedConditionImpl(x, z, world, positive, lineNumber);
        }
    }
}
