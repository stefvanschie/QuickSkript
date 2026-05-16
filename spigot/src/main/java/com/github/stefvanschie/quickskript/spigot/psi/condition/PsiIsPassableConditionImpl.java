package com.github.stefvanschie.quickskript.spigot.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsPassableCondition;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.literal.Block;
import com.github.stefvanschie.quickskript.core.util.literal.Location;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the blocks are passable.
 *
 * @since 0.1.0
 */
public class PsiIsPassableConditionImpl extends PsiIsPassableCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param blocks the blocks to check if they are passable
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsPassableConditionImpl(@NotNull PsiElement<?> blocks, boolean positive, int lineNumber) {
        super(blocks, positive, lineNumber);
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return super.positive == super.blocks.executeMulti(environment, context, Block.class).map(block -> {
            Location location = block.getLocation();
            String name = location.getWorld().getName();
            World world = Bukkit.getWorld(name);

            if (world == null) {
                throw new ExecutionException("World '" + name + "' does not exist or is unloaded", super.lineNumber);
            }

            return world.getBlockAt((int) location.getX(), (int) location.getY(), (int) location.getZ());
        }).test(org.bukkit.block.Block::isPassable);
    }

    /**
     * A factory to create instances of {@link PsiIsPassableCondition}.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsPassableCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        @Override
        public PsiIsPassableCondition create(@NotNull PsiElement<?> blocks, boolean positive, int lineNumber) {
            return new PsiIsPassableConditionImpl(blocks, positive, lineNumber);
        }
    }
}
