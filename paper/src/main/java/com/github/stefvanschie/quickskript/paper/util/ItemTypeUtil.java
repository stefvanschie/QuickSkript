package com.github.stefvanschie.quickskript.paper.util;

import com.github.stefvanschie.quickskript.core.util.literal.EnchantmentType;
import com.github.stefvanschie.quickskript.core.util.literal.ItemType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
     * Converts an {@link ItemType} to an item stack. If the item type can be represented by multiple item stacks, an
     * arbitrary item stack is returned. If the item type doesn't represent any item, this returns null.
     *
     * @param itemType the item type to convert
     * @return a collection of item stacks
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public static ItemStack convertToItemStack(@NotNull ItemType itemType) {
        Map<Enchantment, Integer> enchantments = new HashMap<>();

        for (EnchantmentType enchantmentType : itemType.getEnchantments()) {
            org.bukkit.enchantments.Enchantment bukkitEnchantment = EnchantmentUtil.convert(enchantmentType.getEnchantment());

            enchantments.put(bukkitEnchantment, enchantmentType.getLevel());
        }

        Optional<? extends String> optionalItemTypeEntry = itemType.getItemTypeEntries().stream().findAny();

        if (optionalItemTypeEntry.isEmpty()) {
            return null;
        }

        String itemTypeEntry = optionalItemTypeEntry.get();

        Material material;

        try {
            material = Bukkit.createBlockData(itemTypeEntry).getMaterial();
        } catch (IllegalArgumentException ignored) {
            material = Material.matchMaterial(itemTypeEntry);
        }

        if (material == null) {
            return null;
        }

        ItemStack itemStack = new ItemStack(material, itemType.getAmount());

        enchantments.forEach(itemStack::addEnchantment);

        return itemStack;
    }

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

        for (EnchantmentType enchantmentType : itemType.getEnchantments()) {
            org.bukkit.enchantments.Enchantment bukkitEnchantment = EnchantmentUtil.convert(enchantmentType.getEnchantment());

            enchantments.put(bukkitEnchantment, enchantmentType.getLevel());
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
