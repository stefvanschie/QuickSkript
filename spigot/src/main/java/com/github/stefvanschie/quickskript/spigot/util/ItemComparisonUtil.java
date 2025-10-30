package com.github.stefvanschie.quickskript.spigot.util;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;

/**
 * A utility class for performing comparisons on items.
 *
 * @since 0.1.0
 */
public class ItemComparisonUtil {

    /**
     * A map of comparators and the string for which they compare.
     */
    private final static Map<String, BiPredicate<ItemStack, String>> EXTRA_DATA_COMPARATORS = new HashMap<>();

    /**
     * A mapping of potion types and their internal name.
     */
    private final static Map<PotionType, String> POTION_MAPPING = new HashMap<>();

    /**
     * Compares the specified item with the given data. This returns true if the item and data matches and false
     * otherwise.
     *
     * @param item the item to compare with
     * @param data the data to compare with
     * @return true if both arguments are the same, false otherwise
     * @since 0.1.0
     */
    @Contract(pure = true)
    public static boolean compare(ItemStack item, String data) {
        int openingBracket = data.indexOf('[');
        int closingBracket = data.indexOf(']');

        if (openingBracket < closingBracket && openingBracket != -1) {
            data = data.substring(0, openingBracket) + data.substring(closingBracket + 1);
        }

        int openingCurly = data.indexOf('{');
        int closingCurly = data.indexOf('}');

        if (openingCurly < closingCurly && openingCurly != -1) {
            String extraData = data.substring(openingCurly + 1, closingCurly);

            var dataStack = new ArrayDeque<String>();
            int comma = extraData.indexOf(',');

            while (comma != -1) {
                dataStack.add(extraData.substring(0, comma));

                extraData = extraData.substring(comma + 1);
                comma = extraData.indexOf(',');
            }

            //above loop doesn't add last data to the stack
            dataStack.add(extraData);

            for (String dataPiece : dataStack) {
                int colon = dataPiece.indexOf(':');

                if (colon == -1) {
                    throw new IllegalArgumentException("Specified data is malformed, missing colon in extra data");
                }

                BiPredicate<ItemStack, String> comparator = EXTRA_DATA_COMPARATORS.get(dataPiece.substring(0, colon));

                if (!comparator.test(item, dataPiece.substring(colon + 1))) {
                    return false;
                }
            }
        }

        if (openingCurly != -1) {
            data = data.substring(0, openingCurly);
        }

        NamespacedKey key = item.getType().getKey();

        return data.trim().equals(key.getNamespace() + ':' + key.getKey());
    }

    /**
     * Converts a given potion data to a namespaced key. For example, potion data representing extended water breathing
     * will be converted to "minecraft:long_water_breathing". The specified potion data will not be modified.
     *
     * @param potionData the potion data to convert to a namespaced key
     * @return the namespaced key
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    private static String convertToNamespacedKey(@NotNull PotionData potionData) {
        var namespacedKey = "minecraft:";

        if (potionData.isExtended()) {
            namespacedKey += "long_";
        } else if (potionData.isUpgraded()) {
            namespacedKey += "strong_";
        }

        String potion = POTION_MAPPING.get(potionData.getType());

        if (potion == null) {
            throw new IllegalStateException("Unknown potion detected, unable to convert to namespaced key");
        }

        return namespacedKey + potion;
    }

    static {
        POTION_MAPPING.put(PotionType.WATER, "water");
        POTION_MAPPING.put(PotionType.THICK, "thick");
        POTION_MAPPING.put(PotionType.MUNDANE, "mundane");
        POTION_MAPPING.put(PotionType.AWKWARD, "awkward");
        POTION_MAPPING.put(PotionType.NIGHT_VISION, "night_vision");
        POTION_MAPPING.put(PotionType.INVISIBILITY, "invisibility");
        POTION_MAPPING.put(PotionType.LEAPING, "leaping");
        POTION_MAPPING.put(PotionType.FIRE_RESISTANCE, "fire_resistance");
        POTION_MAPPING.put(PotionType.SWIFTNESS, "swiftness");
        POTION_MAPPING.put(PotionType.SLOWNESS, "slowness");
        POTION_MAPPING.put(PotionType.TURTLE_MASTER, "turtle_master");
        POTION_MAPPING.put(PotionType.WATER_BREATHING, "water_breathing");
        POTION_MAPPING.put(PotionType.HEALING, "healing");
        POTION_MAPPING.put(PotionType.HARMING, "harming");
        POTION_MAPPING.put(PotionType.POISON, "poison");
        POTION_MAPPING.put(PotionType.REGENERATION, "regeneration");
        POTION_MAPPING.put(PotionType.STRENGTH, "strength");
        POTION_MAPPING.put(PotionType.WEAKNESS, "weakness");
        POTION_MAPPING.put(PotionType.SLOW_FALLING, "slow_falling");
        POTION_MAPPING.put(PotionType.LUCK, "luck");

        EXTRA_DATA_COMPARATORS.put("Potion", (item, data) -> {
            if (!item.hasItemMeta()) {
                return false;
            }

            ItemMeta itemMeta = item.getItemMeta();

            if (!(itemMeta instanceof PotionMeta)) {
                return false;
            }

            PotionData potionData = ((PotionMeta) itemMeta).getBasePotionData();

            return convertToNamespacedKey(potionData).equals(data);
        });
    }
}
