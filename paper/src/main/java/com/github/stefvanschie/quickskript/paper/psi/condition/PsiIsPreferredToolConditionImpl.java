package com.github.stefvanschie.quickskript.paper.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsPreferredToolCondition;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.MultiResult;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.literal.Block;
import com.github.stefvanschie.quickskript.core.util.literal.BlockData;
import com.github.stefvanschie.quickskript.core.util.literal.ItemType;
import com.github.stefvanschie.quickskript.core.util.literal.Location;
import com.github.stefvanschie.quickskript.paper.util.ItemTypeUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks whether the given item types are the preferred tools for the provided blocks or block data.
 *
 * @since 0.1.0
 */
public class PsiIsPreferredToolConditionImpl extends PsiIsPreferredToolCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param itemTypes the item types to check if they are the preferred tools
     * @param blocks the blocks or block data to check the preferred tool for
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsPreferredToolConditionImpl(@NotNull PsiElement<?> itemTypes, @NotNull PsiElement<?> blocks,
                                              boolean positive, int lineNumber) {
        super(itemTypes, blocks, positive, lineNumber);
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        MultiResult<? extends ItemType> itemTypes = super.itemTypes.executeMulti(environment, context, ItemType.class);

        return super.positive == super.blocks.executeMulti(environment, context).test(object ->
            itemTypes.map(ItemTypeUtil::convertToItemStack).test(itemStack -> {
                if (itemStack == null) {
                    return false;
                }

                if (object instanceof Block block) {
                    Location location = block.getLocation();
                    World world = Bukkit.getWorld(location.getWorld().getName());

                    if (world == null) {
                        return false;
                    }

                    int x = (int) location.getX();
                    int y = (int) location.getY();
                    int z = (int) location.getZ();

                    return world.getBlockAt(x, y, z).isPreferredTool(itemStack);
                }

                if (object instanceof BlockData blockData) {
                    return Bukkit.createBlockData(blockData.convertToString()).isPreferredTool(itemStack);
                }

                throw new ExecutionException(
                    "Invalid object, expected block or block data, but was '" + object.getClass().getSimpleName() + "'",
                    super.lineNumber
                );
            })
        );
    }

    /**
     * A factory to create instances of {@link PsiIsPreferredToolConditionImpl}.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsPreferredToolCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _, _ -> new", pure = true)
        @Override
        public PsiIsPreferredToolCondition create(@NotNull PsiElement<?> itemTypes, @NotNull PsiElement<?> blocks,
                                                  boolean positive, int lineNumber) {
            return new PsiIsPreferredToolConditionImpl(itemTypes, blocks, positive, lineNumber);
        }
    }
}
