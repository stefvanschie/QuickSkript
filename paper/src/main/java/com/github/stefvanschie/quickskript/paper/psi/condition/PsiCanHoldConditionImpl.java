package com.github.stefvanschie.quickskript.paper.psi.condition;

import com.github.stefvanschie.quickskript.paper.util.ItemTypeUtil;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiCanHoldCondition;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.MultiResult;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.literal.ItemType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Checks if the specified inventories can hold the specified item categories.
 *
 * @since 0.1.0
 */
public class PsiCanHoldConditionImpl extends PsiCanHoldCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param inventories the inventories to fit the items in
     * @param itemTypes the item types to fit in the inventory
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiCanHoldConditionImpl(
        @NotNull PsiElement<?> inventories,
        @NotNull PsiElement<?> itemTypes,
        boolean positive,
        int lineNumber
    ) {
        super(inventories, itemTypes, positive, lineNumber);
    }

    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        MultiResult<Inventory> inventories = this.inventories.executeMulti(environment, context, Inventory.class);
        MultiResult<ItemType> itemTypes = this.itemTypes.executeMulti(
            environment,
            context,
            ItemType.class
        );

        return inventories.test(inventory -> itemTypes.test(itemType -> hasSpace(inventory, itemType)));
    }

    /**
     * checks whether the given inventory has space for the given item type.
     *
     * @param inventory the inventory to check for space
     * @param itemType the item type to try and fit in
     * @return true if the item type fits in the inventory, false otherwise
     * @since 0.1.0
     */
    @Contract(pure = true)
    private boolean hasSpace(@NotNull Inventory inventory, @NotNull ItemType itemType) {
        Collection<? extends ItemStack> itemStacks = ItemTypeUtil.convertToItemStacks(itemType);

        if (!itemType.isAll()) {
            Iterator<? extends ItemStack> iterator = itemStacks.iterator();

            if (!iterator.hasNext()) {
                return true;
            }

            itemStacks = Collections.singleton(iterator.next());
        }

        ItemStack[] items = inventory.getStorageContents();

        for (ItemStack inventoryItem : items) {
            for (Iterator<? extends ItemStack> iterator = itemStacks.iterator(); iterator.hasNext();) {
                ItemStack item = iterator.next();

                if (inventoryItem == null) {
                    iterator.remove();
                    continue;
                }

                if (!inventoryItem.isSimilar(item)) {
                    continue;
                }

                int remainingSpace = inventoryItem.getMaxStackSize() - inventoryItem.getAmount();

                if (remainingSpace >= item.getAmount()) {
                    iterator.remove();
                } else {
                    item.setAmount(item.getAmount() - remainingSpace);
                }
            }

            if (itemStacks.isEmpty()) {
                return true;
            }
        }

        return false;
    }

    /**
     * A factory for creating {@link PsiCanHoldConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiCanHoldCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiCanHoldCondition create(
            @NotNull PsiElement<?> inventories,
            @NotNull PsiElement<?> itemCategories,
            boolean positive,
            int lineNumber
        ) {
            return new PsiCanHoldConditionImpl(inventories, itemCategories, positive, lineNumber);
        }
    }
}
