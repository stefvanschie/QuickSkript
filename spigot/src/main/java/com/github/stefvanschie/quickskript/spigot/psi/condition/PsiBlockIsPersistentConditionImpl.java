package com.github.stefvanschie.quickskript.spigot.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiBlockIsPersistentCondition;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.literal.Block;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.data.type.Leaves;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Check if the blocks are persistent.
 *
 * @since 0.1.0
 */
public class PsiBlockIsPersistentConditionImpl extends PsiBlockIsPersistentCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param blocks the blocks to check if they are persistent
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiBlockIsPersistentConditionImpl(@NotNull PsiElement<?> blocks, boolean positive, int lineNumber) {
        super(blocks, positive, lineNumber);
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return super.positive == super.blocks.executeMulti(environment, context, Block.class)
            .map(Block::getLocation)
            .test(location -> {
            World world = Bukkit.getWorld(location.getWorld().getName());

            return world != null
                && world.getBlockAt((int) location.getX(), (int) location.getY(), (int) location.getZ()).getBlockData() instanceof Leaves leaves
                && leaves.isPersistent();
        });
    }

    /**
     * A factory for creating instances of {@link PsiBlockIsPersistentConditionImpl}.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiBlockIsPersistentCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        @Override
        protected PsiBlockIsPersistentCondition create(@NotNull PsiElement<?> blocks, boolean positive,
                                                       int lineNumber) {
            return new PsiBlockIsPersistentConditionImpl(blocks, positive, lineNumber);
        }
    }
}
