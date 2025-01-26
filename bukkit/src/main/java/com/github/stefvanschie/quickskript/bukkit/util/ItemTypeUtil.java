package com.github.stefvanschie.quickskript.bukkit.util;

import com.github.stefvanschie.quickskript.core.util.literal.ItemType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * A utility class for operations related to item types.
 *
 * @since 0.1.0
 */
public class ItemTypeUtil {

    /**
     * A private constructor to prevent instantiation.
     *
     * @since 0.1.0
     */
    private ItemTypeUtil() {}

    /**
     * Converts an {@link ItemType} to a collection of item stacks.
     *
     * @param itemType the item type to convert
     * @return a collection of item stacks
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public static Collection<? extends ItemStack> convertToItemStacks(@NotNull ItemType itemType) {
        Map<Enchantment, Integer> enchantments = new HashMap<>();

        for (com.github.stefvanschie.quickskript.core.util.literal.Enchantment enchantment : itemType.getEnchantments()) {
            org.bukkit.enchantments.Enchantment bukkitEnchantment = EnchantmentTypeUtil.convert(enchantment.getType());

            enchantments.put(bukkitEnchantment, enchantment.getLevel());
        }

        Collection<ItemStack> itemStacks = new HashSet<>();

        for (Material material : convertToMaterials(itemType)) {
            ItemStack itemStack = new ItemStack(material, itemType.getAmount());

            enchantments.forEach(itemStack::addEnchantment);

            itemStacks.add(itemStack);
        }

        return itemStacks;
    }

    /**
     * Converts an {@link ItemType} in a list of materials that correspond to the entries of the item type. If the item
     * type does not have any entries, this will return an empty list.
     *
     * @param itemType the item type to convert
     * @return a list of materials
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public static List<? extends Material> convertToMaterials(@NotNull ItemType itemType) {
        List<Material> materials = new ArrayList<>();

        for (String itemTypeEntry : itemType.getItemTypeEntries()) {
            try {
                materials.add(Bukkit.createBlockData(itemTypeEntry).getMaterial());
            } catch (IllegalArgumentException ignored) {
                materials.add(Material.matchMaterial(itemTypeEntry));
            }
        }

        return materials;
    }
}
