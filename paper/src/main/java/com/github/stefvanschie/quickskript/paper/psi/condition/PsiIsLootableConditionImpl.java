package com.github.stefvanschie.quickskript.paper.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsLootableCondition;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.literal.Block;
import com.github.stefvanschie.quickskript.core.util.literal.Location;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.loot.Lootable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the blocks or entities are lootable.
 *
 * @since 0.1.0
 */
public class PsiIsLootableConditionImpl extends PsiIsLootableCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param objects the objects to check if they are lootable
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsLootableConditionImpl(@NotNull PsiElement<?> objects, boolean positive, int lineNumber) {
        super(objects, positive, lineNumber);
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return this.positive == this.objects.executeMulti(environment, context).test(object -> {
            if (object instanceof Block block) {
                Location location = block.getLocation();
                World world = Bukkit.getWorld(location.getWorld().getName());

                if (world == null) {
                    return false;
                }

                int x = (int) location.getX();
                int y = (int) location.getY();
                int z = (int) location.getZ();

                return world.getBlockState(x, y, z) instanceof Lootable;
            }

            return object instanceof Lootable;
        });
    }

    /**
     * A factory to create instances of {@link PsiIsLootableCondition}.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsLootableCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        @Override
        public PsiIsLootableCondition create(@NotNull PsiElement<?> objects, boolean positive, int lineNumber) {
            return new PsiIsLootableConditionImpl(objects, positive, lineNumber);
        }
    }
}
