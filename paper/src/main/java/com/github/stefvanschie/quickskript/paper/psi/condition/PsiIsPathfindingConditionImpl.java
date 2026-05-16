package com.github.stefvanschie.quickskript.paper.psi.condition;

import com.destroystokyo.paper.entity.Pathfinder;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsPathfindingCondition;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the living entities are pathfinding.
 *
 * @since 0.1.0
 */
public class PsiIsPathfindingConditionImpl extends PsiIsPathfindingCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param livingEntities the living entities to check if they are pathfinding
     * @param target the target destination or null
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsPathfindingConditionImpl(@NotNull PsiElement<?> livingEntities, @Nullable PsiElement<?> target,
                                            boolean positive, int lineNumber) {
        super(livingEntities, target, positive, lineNumber);
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return super.positive == super.livingEntities.executeMulti(environment, context, LivingEntity.class).test(entity -> {
            if (!(entity instanceof Mob mob)) {
                return false;
            }

            Pathfinder pathfinder = mob.getPathfinder();

            if (super.target == null) {
                return pathfinder.hasPath();
            }

            Object target = super.target.execute(environment, context);
            Pathfinder.PathResult path = pathfinder.getCurrentPath();

            if (target == null || path == null) {
                return false;
            }

            Location point = path.getFinalPoint();

            if (target instanceof com.github.stefvanschie.quickskript.core.util.literal.Location location) {
                World world = Bukkit.getWorld(location.getWorld().getName());
                double x = location.getX();
                double y = location.getY();
                double z = location.getZ();

                return new Location(world, x, y, z, location.getYaw(), location.getPitch()).equals(point);
            }

            if (point == null) {
                return false;
            }

            if (!(target instanceof LivingEntity livingEntity)) {
                throw new ExecutionException(
                    "Target is of type '" + target.getClass().getSimpleName() + "' but was expected to be a LivingEntity",
                    super.lineNumber
                );
            }

            return livingEntity.getLocation().distanceSquared(point) < 1;
        });
    }

    /**
     * A factory for creating instances of {@link PsiIsPathfindingConditionImpl}.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsPathfindingCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _, _ -> new", pure = true)
        @Override
        public PsiIsPathfindingCondition create(@NotNull PsiElement<?> livingEntities, @Nullable PsiElement<?> target,
                                                boolean positive, int lineNumber) {
            return new PsiIsPathfindingConditionImpl(livingEntities, target, positive, lineNumber);
        }
    }
}
