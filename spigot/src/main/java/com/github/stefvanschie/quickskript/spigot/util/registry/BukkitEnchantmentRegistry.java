package com.github.stefvanschie.quickskript.spigot.util.registry;

import com.github.stefvanschie.quickskript.core.util.literal.Enchantment;
import com.github.stefvanschie.quickskript.core.util.registry.Registry;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Registry for looking up enchantments.
 *
 * @since 0.1.0
 */
public class BukkitEnchantmentRegistry implements Registry<Enchantment> {

    /**
     * The parent registry.
     */
    @NotNull
    private final Registry<Enchantment> registry;

    /**
     * Creates a new enchantment registry with the provided parent registry.
     *
     * @param registry the parent registry
     * @since 0.1.0
     */
    public BukkitEnchantmentRegistry(@NotNull Registry<Enchantment> registry) {
        this.registry = registry;
    }

    @Nullable
    @Contract(pure = true)
    @Override
    public Enchantment byName(@NotNull String name) {
        NamespacedKey namespacedKey = NamespacedKey.fromString(name);

        if (namespacedKey == null) {
            return this.registry.byName(name);
        }

        org.bukkit.enchantments.Enchantment enchantment = org.bukkit.Registry.ENCHANTMENT.get(namespacedKey);

        if (enchantment == null) {
            return this.registry.byName(name);
        }

        return new Enchantment(name);
    }
}
