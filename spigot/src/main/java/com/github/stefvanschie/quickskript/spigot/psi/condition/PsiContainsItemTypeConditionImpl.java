package com.github.stefvanschie.quickskript.spigot.psi.condition;

import com.github.stefvanschie.quickskript.spigot.util.ItemTypeUtil;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiContainsItemTypeCondition;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.MultiResult;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.literal.ItemType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;

/**
 * Checks whether a given inventory contains a given item type.
 *
 * @since 0.1.0
 */
public class PsiContainsItemTypeConditionImpl extends PsiContainsItemTypeCondition {

    /**
     * Creates a new element with the given line number.
     *
     * @param inventory the inventory to check
     * @param itemType the item type to check if it is contained in the inventory
     * @param positive if false, the result is inverted
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiContainsItemTypeConditionImpl(
        @NotNull PsiElement<?> inventory,
        @NotNull PsiElement<?> itemType,
        boolean positive,
        int lineNumber
    ) {
        super(inventory, itemType, positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        MultiResult<Inventory> inventories = super.inventory.executeMulti(environment, context, Inventory.class);
        MultiResult<ItemType> itemTypes = super.itemType.executeMulti(environment, context, ItemType.class);

        return inventories.test(inventory -> itemTypes.test(itemType -> {
            Collection<? extends ItemStack> itemStacks = ItemTypeUtil.convertToItemStacks(itemType);

            for (ItemStack inventoryItem : inventory.getStorageContents()) {
                if (inventoryItem == null) {
                    continue;
                }

                for (Iterator<? extends ItemStack> iterator = itemStacks.iterator(); iterator.hasNext();) {
                    ItemStack itemStack = iterator.next();

                    if (!inventoryItem.isSimilar(itemStack)) {
                        continue;
                    }

                    int remaining = itemStack.getAmount() - inventoryItem.getAmount();

                    if (remaining <= 0) {
                        if (!itemType.isAll()) {
                            return true;
                        }

                        iterator.remove();
                    } else {
                        itemStack.setAmount(remaining);
                    }
                }
            }

            return itemStacks.isEmpty();
        }));
    }

    /**
     * A factory for creating {@link PsiContainsItemTypeConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiContainsItemTypeCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiContainsItemTypeCondition create(
            @NotNull PsiElement<?> inventory,
            @NotNull PsiElement<?> itemType,
            boolean positive,
            int lineNumber
        ) {
            return new PsiContainsItemTypeConditionImpl(inventory, itemType, positive, lineNumber);
        }
    }
}
