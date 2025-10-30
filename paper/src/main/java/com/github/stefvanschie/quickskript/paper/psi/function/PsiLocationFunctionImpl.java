package com.github.stefvanschie.quickskript.paper.psi.function;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.function.PsiLocationFunction;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A function for creating locations
 *
 * @since 0.1.0
 */
public class PsiLocationFunctionImpl extends PsiLocationFunction {

    /**
     * Creates a new location function
     *
     * @param world the world
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     * @param yaw the yaw
     * @param pitch the pitch
     * @since 0.1.0
     */
    private PsiLocationFunctionImpl(@NotNull PsiElement<?> world, @NotNull PsiElement<?> x, @NotNull PsiElement<?> y,
                                    @NotNull PsiElement<?> z, @Nullable PsiElement<?> yaw,
                                    @Nullable PsiElement<?> pitch, int lineNumber) {
        super(world, x, y, z, yaw, pitch, lineNumber);
    }

    @NotNull
    @Override
    public Location executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return new Location(
                world.execute(environment, context, World.class),
                x.execute(environment, context, Number.class).doubleValue(),
                y.execute(environment, context, Number.class).doubleValue(),
                z.execute(environment, context, Number.class).doubleValue(),
                yaw == null ? 0 : yaw.execute(environment, context, Number.class).floatValue(),
                pitch == null ? 0 : pitch.execute(environment, context, Number.class).floatValue()
        );
    }

    /**
     * A factory for creating location functions
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiLocationFunction.Factory {

        @NotNull
        @Override
        public PsiLocationFunctionImpl create(@NotNull PsiElement<?> world, @NotNull PsiElement<?> x,
                                              @NotNull PsiElement<?> y, @NotNull PsiElement<?> z,
                                              @Nullable PsiElement<?> yaw, @Nullable PsiElement<?> pitch,
                                              int lineNumber) {
            return new PsiLocationFunctionImpl(world, x, y, z, yaw, pitch, lineNumber);
        }
    }
}
