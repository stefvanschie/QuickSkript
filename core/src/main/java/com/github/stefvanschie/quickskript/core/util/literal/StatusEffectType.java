package com.github.stefvanschie.quickskript.core.util.literal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Represents all different potion effect types.
 *
 * @since 0.1.0
 */
public enum StatusEffectType {

    /**
     * The absorption status effect
     *
     * @since 0.1.0
     */
    ABSORPTION("absorption"),

    /**
     * The bad luck status effect
     *
     * @since 0.1.0
     */
    BAD_LUCK("bad luck"),

    /**
     * The bad omen status effect
     *
     * @since 0.1.0
     */
    BAD_OMEN("bad omen"),

    /**
     * The blindness status effect
     *
     * @since 0.1.0
     */
    BLINDNESS("blindness"),

    /**
     * The conduit power status effect
     *
     * @since 0.1.0
     */
    CONDUIT_POWER("conduit power"),

    /**
     * The dolphin's grace status effect
     *
     * @since 0.1.0
     */
    DOLPHINS_GRACE("dolphins grace"),

    /**
     * The dire resistance status effect
     *
     * @since 0.1.0
     */
    FIRE_RESISTANCE("fire resistance"),

    /**
     * The glowing status effect
     *
     * @since 0.1.0
     */
    GLOWING("glowing"),

    /**
     * The haste status effect
     *
     * @since 0.1.0
     */
    HASTE("haste"),

    /**
     * The health boost status effect
     *
     * @since 0.1.0
     */
    HEALTH_BOOST("health boost"),

    /**
     * The hero of the village status effect
     *
     * @since 0.1.0
     */
    HERO_OF_THE_VILLAGE("hero of the village"),

    /**
     * The hunger status effect
     *
     * @since 0.1.0
     */
    HUNGER("hunger"),

    /**
     * The instant damage status effect
     *
     * @since 0.1.0
     */
    INSTANT_DAMAGE("instant damage"),

    /**
     * The instant health status effect
     *
     * @since 0.1.0
     */
    INSTANT_HEALTH("instant health"),

    /**
     * The invisibility status effect
     *
     * @since 0.1.0
     */
    INVISIBILITY("invisibility"),

    /**
     * The jump boost status effect
     *
     * @since 0.1.0
     */
    JUMP_BOOST("jump boost"),

    /**
     * The levitation status effect
     *
     * @since 0.1.0
     */
    LEVITATION("levitation"),

    /**
     * The luck status effect
     *
     * @since 0.1.0
     */
    LUCK("luck"),

    /**
     * The mining fatigue status effect
     *
     * @since 0.1.0
     */
    MINING_FATIGUE("mining fatigue"),

    /**
     * The nausea status effect
     *
     * @since 0.1.0
     */
    NAUSEA("nausea"),

    /**
     * The night vision status effect
     *
     * @since 0.1.0
     */
    NIGHT_VISION("night vision"),

    /**
     * The poison status effect
     *
     * @since 0.1.0
     */
    POISON("poison"),

    /**
     * The regeneration status effect
     *
     * @since 0.1.0
     */
    REGENERATION("regeneration"),

    /**
     * The resistance status effect
     *
     * @since 0.1.0
     */
    RESISTANCE("resistance"),

    /**
     * The saturation status effect
     *
     * @since 0.1.0
     */
    SATURATION("saturation"),

    /**
     * The slow falling status effect
     *
     * @since 0.1.0
     */
    SLOW_FALLING("slow falling"),

    /**
     * The slowness status effect
     *
     * @since 0.1.0
     */
    SLOWNESS("slowness"),

    /**
     * The strength status effect
     *
     * @since 0.1.0
     */
    STRENGTH("strength"),

    /**
     * The swiftness (speed) status effect
     *
     * @since 0.1.0
     */
    SWIFTNESS("speed"),

    /**
     * The water breathing status effect
     *
     * @since 0.1.0
     */
    WATER_BREATHING("water breathing"),

    /**
     * The weakness status effect
     *
     * @since 0.1.0
     */
    WEAKNESS("weakness"),

    /**
     * The wither status effect
     *
     * @since 0.1.0
     */
    WITHER("wither");

    /**
     * The name of the status effect type
     */
    @NotNull
    private String name;

    /**
     * Creates a status effect type with the given name
     *
     * @param name the name of the status effect type
     * @since 0.1.0
     */
    StatusEffectType(@NotNull String name) {
        this.name = name;
    }

    /**
     * Gets the name of the status effect type
     *
     * @return the status effect type's name
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public String getName() {
        return name;
    }
}
