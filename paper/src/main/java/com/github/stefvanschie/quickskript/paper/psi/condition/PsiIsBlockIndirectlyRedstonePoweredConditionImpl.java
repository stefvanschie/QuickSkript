package com.github.stefvanschie.quickskript.paper.psi.condition;

import com.github.stefvanschie.quickskript.paper.util.LocationUtil;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsBlockIndirectlyRedstonePoweredCondition;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.literal.Block;
import com.github.stefvanschie.quickskript.core.util.literal.Location;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the blocks are indirectly powered by redstone.
 *
 * @since 0.1.0
 */
public class PsiIsBlockIndirectlyRedstonePoweredConditionImpl extends PsiIsBlockIndirectlyRedstonePoweredCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param blocks the block to check if they are indirectly powered by redstone
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsBlockIndirectlyRedstonePoweredConditionImpl(
        @NotNull PsiElement<?> blocks,
        boolean positive,
        int lineNumber
    ) {
        super(blocks, positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return super.positive == super.blocks.executeMulti(environment, context, Block.class).test(block -> {
            Location location = block.getLocation();
            org.bukkit.Location bukkitLocation = LocationUtil.convert(location);

            if (bukkitLocation == null) {
                throw new ExecutionException(
                    "Unable to find world with name '" + location.getWorld().getName() + "'",
                    super.lineNumber
                );
            }

            return bukkitLocation.getBlock().isBlockIndirectlyPowered();
        });
    }

    /**
     * A factory for creating {@link PsiIsBlockIndirectlyRedstonePoweredConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsBlockIndirectlyRedstonePoweredCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiIsBlockIndirectlyRedstonePoweredCondition create(
            @NotNull PsiElement<?> blocks,
            boolean positive,
            int lineNumber
        ) {
            return new PsiIsBlockIndirectlyRedstonePoweredConditionImpl(blocks, positive, lineNumber);
        }
    }
}
