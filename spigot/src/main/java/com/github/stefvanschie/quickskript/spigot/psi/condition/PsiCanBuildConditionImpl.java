package com.github.stefvanschie.quickskript.spigot.psi.condition;

import com.github.stefvanschie.quickskript.spigot.plugin.QuickSkript;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiCanBuildCondition;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.literal.Location;
import com.github.stefvanschie.quickskript.core.util.literal.direction.Direction;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the specified player is able to build.
 *
 * @since 0.1.0
 */
public class PsiCanBuildConditionImpl extends PsiCanBuildCondition {

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
    private PsiCanBuildConditionImpl(
        @NotNull PsiElement<?> player,
        @NotNull PsiElement<?> direction,
        @NotNull PsiElement<?> location,
        boolean positive,
        int lineNumber
    ) {
        super(player, direction, location, positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        Player player = super.player.execute(environment, context, Player.class);
        Direction direction = super.direction.execute(environment, context, Direction.class);
        Location location = super.location.execute(environment, context, Location.class);

        return QuickSkript.getInstance().getRegionIntegration().canBuild(player, direction.getRelative(location));
    }

    /**
     * A factory for creating {@link PsiCanBuildConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiCanBuildCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiCanBuildCondition create(@NotNull PsiElement<?> player, @NotNull PsiElement<?> direction, @NotNull PsiElement<?> location, boolean positive, int lineNumber) {
            return new PsiCanBuildConditionImpl(player, direction, location, positive, lineNumber);
        }
    }
}
