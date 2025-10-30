package com.github.stefvanschie.quickskript.paper.util;

import com.github.stefvanschie.quickskript.core.util.literal.Enchantment;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A utility class for enchantment types.
 *
 * @since 0.1.0
 */
public class EnchantmentUtil {

    /**
     * A private constructor to prevent instantiation.
     */
    private EnchantmentUtil() {}

    /**
     * Converts a QuickSkript enchantment to a Bukkit enchantment. Throws an {@link IllegalStateException} if the
     * provided enchantment type does not have a mapping.
     *
     * @param enchantment th enchantment type to convert
     * @return the corresponding Bukkit enchantment
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public static org.bukkit.enchantments.Enchantment convert(@NotNull Enchantment enchantment) {
        return Registry.ENCHANTMENT.get(NamespacedKey.fromString(enchantment.getKey()));
    }
}