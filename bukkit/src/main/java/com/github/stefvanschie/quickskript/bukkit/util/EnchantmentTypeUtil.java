package com.github.stefvanschie.quickskript.bukkit.util;

import com.github.stefvanschie.quickskript.core.util.literal.Enchantment;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * A utility class for enchantment types.
 *
 * @since 0.1.0
 */
public class EnchantmentTypeUtil {

    /**
     * A mapping between QuickSkript enchantment types and Bukkit enchantment types.
     */
    @NotNull
    private static final Map<Enchantment.@NotNull Type, org.bukkit.enchantments.@NotNull Enchantment> MAPPINGS = new HashMap<>();

    /**
     * A private constructor to prevent instantiation.
     */
    private EnchantmentTypeUtil() {}

    /**
     * Converts a QuickSkript enchantment to a Bukkit enchantment. Throws an {@link IllegalStateException} if the
     * provided enchantment type does not have a mapping.
     *
     * @param enchantmentType th enchantment type to convert
     * @return the corresponding Bukkit enchantment
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public static org.bukkit.enchantments.Enchantment convert(@NotNull Enchantment.Type enchantmentType) {
        org.bukkit.enchantments.Enchantment enchantment = MAPPINGS.get(enchantmentType);

        if (enchantment == null) {
            throw new IllegalStateException("Enchantment doesn't have a mapping");
        }

        return enchantment;
    }

    static {
        MAPPINGS.put(Enchantment.Type.AQUA_AFFINITY, org.bukkit.enchantments.Enchantment.WATER_WORKER);
        MAPPINGS.put(Enchantment.Type.BANE_OF_ARTHROPODS, org.bukkit.enchantments.Enchantment.DAMAGE_ARTHROPODS);
        MAPPINGS.put(Enchantment.Type.BLAST_PROTECTION, org.bukkit.enchantments.Enchantment.PROTECTION_EXPLOSIONS);
        MAPPINGS.put(Enchantment.Type.CHANNELING, org.bukkit.enchantments.Enchantment.CHANNELING);
        MAPPINGS.put(Enchantment.Type.CURSE_OF_BINDING, org.bukkit.enchantments.Enchantment.BINDING_CURSE);
        MAPPINGS.put(Enchantment.Type.CURSE_OF_VANISHING, org.bukkit.enchantments.Enchantment.VANISHING_CURSE);
        MAPPINGS.put(Enchantment.Type.DEPTH_STRIDER, org.bukkit.enchantments.Enchantment.DEPTH_STRIDER);
        MAPPINGS.put(Enchantment.Type.EFFICIENCY, org.bukkit.enchantments.Enchantment.DIG_SPEED);
        MAPPINGS.put(Enchantment.Type.FEATHER_FALLING, org.bukkit.enchantments.Enchantment.PROTECTION_FALL);
        MAPPINGS.put(Enchantment.Type.FIRE_ASPECT, org.bukkit.enchantments.Enchantment.FIRE_ASPECT);
        MAPPINGS.put(Enchantment.Type.FIRE_PROTECTION, org.bukkit.enchantments.Enchantment.PROTECTION_FIRE);
        MAPPINGS.put(Enchantment.Type.FLAME, org.bukkit.enchantments.Enchantment.ARROW_FIRE);
        MAPPINGS.put(Enchantment.Type.FORTUNE, org.bukkit.enchantments.Enchantment.LOOT_BONUS_BLOCKS);
        MAPPINGS.put(Enchantment.Type.FROST_WALKER, org.bukkit.enchantments.Enchantment.FROST_WALKER);
        MAPPINGS.put(Enchantment.Type.IMPALING, org.bukkit.enchantments.Enchantment.IMPALING);
        MAPPINGS.put(Enchantment.Type.INFINITY, org.bukkit.enchantments.Enchantment.ARROW_INFINITE);
        MAPPINGS.put(Enchantment.Type.KNOCKBACK, org.bukkit.enchantments.Enchantment.KNOCKBACK);
        MAPPINGS.put(Enchantment.Type.LOOTING, org.bukkit.enchantments.Enchantment.LOOT_BONUS_MOBS);
        MAPPINGS.put(Enchantment.Type.LOYALTY, org.bukkit.enchantments.Enchantment.LOYALTY);
        MAPPINGS.put(Enchantment.Type.LUCK_OF_THE_SEA, org.bukkit.enchantments.Enchantment.LUCK);
        MAPPINGS.put(Enchantment.Type.LURE, org.bukkit.enchantments.Enchantment.LURE);
        MAPPINGS.put(Enchantment.Type.MENDING, org.bukkit.enchantments.Enchantment.MENDING);
        MAPPINGS.put(Enchantment.Type.MULTISHOT, org.bukkit.enchantments.Enchantment.MULTISHOT);
        MAPPINGS.put(Enchantment.Type.PIERCING, org.bukkit.enchantments.Enchantment.PIERCING);
        MAPPINGS.put(Enchantment.Type.POWER, org.bukkit.enchantments.Enchantment.ARROW_DAMAGE);
        MAPPINGS.put(Enchantment.Type.PROTECTION, org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL);
        MAPPINGS.put(Enchantment.Type.PROJECTILE_PROTECTION, org.bukkit.enchantments.Enchantment.PROTECTION_PROJECTILE);
        MAPPINGS.put(Enchantment.Type.PUNCH, org.bukkit.enchantments.Enchantment.ARROW_KNOCKBACK);
        MAPPINGS.put(Enchantment.Type.QUICK_CHARGE, org.bukkit.enchantments.Enchantment.QUICK_CHARGE);
        MAPPINGS.put(Enchantment.Type.RESPIRATION, org.bukkit.enchantments.Enchantment.OXYGEN);
        MAPPINGS.put(Enchantment.Type.RIPTIDE, org.bukkit.enchantments.Enchantment.RIPTIDE);
        MAPPINGS.put(Enchantment.Type.SHARPNESS, org.bukkit.enchantments.Enchantment.DAMAGE_ALL);
        MAPPINGS.put(Enchantment.Type.SILK_TOUCH, org.bukkit.enchantments.Enchantment.SILK_TOUCH);
        MAPPINGS.put(Enchantment.Type.SMITE, org.bukkit.enchantments.Enchantment.DAMAGE_UNDEAD);
        MAPPINGS.put(Enchantment.Type.SWEEPING_EDGE, org.bukkit.enchantments.Enchantment.SWEEPING_EDGE);
        MAPPINGS.put(Enchantment.Type.SWIFT_SNEAK, org.bukkit.enchantments.Enchantment.SWIFT_SNEAK);
        MAPPINGS.put(Enchantment.Type.THORNS, org.bukkit.enchantments.Enchantment.THORNS);
        MAPPINGS.put(Enchantment.Type.UNBREAKING, org.bukkit.enchantments.Enchantment.DURABILITY);
    }
}