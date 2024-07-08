package com.github.stefvanschie.quickskript.core.util.literal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an enchantment either with or without a level
 *
 * @since 0.1.0
 */
public class Enchantment {

    /**
     * The type of enchantment
     */
    @NotNull
    private Type type;

    /**
     * The level of the enchantment or null if not specified
     */
    @Nullable
    private Integer level;

    /**
     * Creates a new enchantment with an optional level
     *
     * @param type the enchantment type
     * @param level the level or null if the enchantment has no level
     * @since 0.1.0
     */
    public Enchantment(@NotNull Type type, @Nullable Integer level) {
        this.type = type;
        this.level = level;
    }

    /**
     * Gets the type of this enchantment.
     *
     * @return the enchantment type
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public Type getType() {
        return this.type;
    }

    /**
     * Gets the level of this enchantment. If the level was not set, this returns 1.
     *
     * @return the level
     * @since 0.1.0
     */
    @Contract(pure = true)
    public int getLevel() {
        return this.level == null ? 1 : this.level;
    }

    /**
     * Possible types of enchantments
     *
     * @since 0.1.0
     */
    public enum Type {

        /**
         * Represents the aqua affinity enchantment
         *
         * @since 0.1.0
         */
        AQUA_AFFINITY,

        /**
         * Represents the bane of arthropods enchantment
         *
         * @since 0.1.0
         */
        BANE_OF_ARTHROPODS,

        /**
         * Represents the blast protection enchantment
         *
         * @since 0.1.0
         */
        BLAST_PROTECTION,

        /**
         * Represents the channeling enchantment
         *
         * @since 0.1.0
         */
        CHANNELING,

        /**
         * Represents the curse of binding enchantment
         *
         * @since 0.1.0
         */
        CURSE_OF_BINDING,

        /**
         * Represents the curse of vanishing enchantment
         *
         * @since 0.1.0
         */
        CURSE_OF_VANISHING,

        /**
         * Represents the depth strider enchantment
         *
         * @since 0.1.0
         */
        DEPTH_STRIDER,

        /**
         * Represents the efficiency enchantment
         *
         * @since 0.1.0
         */
        EFFICIENCY,

        /**
         * Represents the feather falling enchantment
         *
         * @since 0.1.0
         */
        FEATHER_FALLING,

        /**
         * Represents the fire aspect enchantment
         *
         * @since 0.1.0
         */
        FIRE_ASPECT,

        /**
         * Represents the fire protection enchantment
         *
         * @since 0.1.0
         */
        FIRE_PROTECTION,

        /**
         * Represents the flame enchantment
         *
         * @since 0.1.0
         */
        FLAME,

        /**
         * Represents the fortune enchantment
         *
         * @since 0.1.0
         */
        FORTUNE,

        /**
         * Represents the frost walker enchantment
         *
         * @since 0.1.0
         */
        FROST_WALKER,

        /**
         * Represents the impaling enchantment
         *
         * @since 0.1.0
         */
        IMPALING,

        /**
         * Represents the infinity enchantment
         *
         * @since 0.1.0
         */
        INFINITY,

        /**
         * Represents the knockback enchantment
         *
         * @since 0.1.0
         */
        KNOCKBACK,

        /**
         * Represents the looting enchantment
         *
         * @since 0.1.0
         */
        LOOTING,

        /**
         * Represents the loyalty enchantment
         *
         * @since 0.1.0
         */
        LOYALTY,

        /**
         * Represents the luck of the sea enchantment
         *
         * @since 0.1.0
         */
        LUCK_OF_THE_SEA,

        /**
         * Represents the lure enchantment
         *
         * @since 0.1.0
         */
        LURE,

        /**
         * Represents the mending enchantment
         *
         * @since 0.1.0
         */
        MENDING,

        /**
         * Represents the multishot enchantment
         *
         * @since 0.1.0
         */
        MULTISHOT,

        /**
         * Represents the piercing enchantment
         *
         * @since 0.1.0
         */
        PIERCING,

        /**
         * Represents the power enchantment
         *
         * @since 0.1.0
         */
        POWER,

        /**
         * Represents the protection enchantment
         *
         * @since 0.1.0
         */
        PROTECTION,

        /**
         * Represents the projectile protection enchantment
         *
         * @since 0.1.0
         */
        PROJECTILE_PROTECTION,

        /**
         * Represents the punch enchantment
         *
         * @since 0.1.0
         */
        PUNCH,

        /**
         * Represents the quick charge enchantment
         *
         * @since 0.1.0
         */
        QUICK_CHARGE,

        /**
         * Represents the respiration enchantment
         *
         * @since 0.1.0
         */
        RESPIRATION,

        /**
         * Represents the riptide enchantment
         *
         * @since 0.1.0
         */
        RIPTIDE,

        /**
         * Represents the sharpness enchantment
         *
         * @since 0.1.0
         */
        SHARPNESS,

        /**
         * Represents the silk touch enchantment
         *
         * @since 0.1.0
         */
        SILK_TOUCH,

        /**
         * Represents the smite enchantment
         *
         * @since 0.1.0
         */
        SMITE,

        /**
         * Represents the sweeping edge enchantment
         *
         * @since 0.1.0
         */
        SWEEPING_EDGE,

        /**
         * Represents the swift sneak enchantment
         *
         * @since 0.1.0
         */
        SWIFT_SNEAK,

        /**
         * Represents the thorns enchantment
         *
         * @since 0.1.0
         */
        THORNS,

        /**
         * Represents the unbreaking enchantment
         *
         * @since 0.1.0
         */
        UNBREAKING;

        /**
         * All entries in this enum by their name
         */
        @NotNull
        private static final Map<String, Type> ENTRIES = new HashMap<>();

        /**
         * Gets the enchantment type by name or null if no such type exists.
         *
         * @param name the name of the enchantment type
         * @return the enchantment type or null
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        public static Type byName(@NotNull String name) {
            return ENTRIES.get(name);
        }

        static {
            for (Type type : Type.values()) {
                ENTRIES.put(type.name().replace('_', ' ').toLowerCase(), type);
            }
        }
    }
}
