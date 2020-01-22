package com.github.stefvanschie.quickskript.core.util.literal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Represents possible ways an entity can be damaged
 *
 * @since 0.1.0
 */
public enum DamageCause {

    /**
     * Represents an entity taking damage from an attack
     *
     * @since 0.1.0
     */
    ATTACK("attack"),

    /**
     * Represents an entity taking damage from the explosion of a block
     *
     * @since 0.1.0
     */
    BLOCK_EXPLOSION("block explosion"),

    /**
     * Represents an entity taking damage from being on fire
     *
     * @since 0.1.0
     */
    BURNING("burning"),

    /**
     * Represents an entity taking damage from contact
     *
     * @since 0.1.0
     */
    CONTACT("contact"),

    /**
     * Represents an entity taking damage from the entity cramming game rule
     *
     * @since 0.1.0
     */
    CRAMMING("cramming"),

    /**
     * Represents an entity taking damage from the ender dragon's breath
     *
     * @since 0.1.0
     */
    DRAGON_BREATH("dragon's breath"),

    /**
     * Represents an entity taking damage because of drowning
     *
     * @since 0.1.0
     */
    DROWNING("drowning"),

    /**
     * Represents an entity taking damage due to drying out
     *
     * @since 0.1.0
     */
    DRYOUT("dryout"),

    /**
     * Represents an entity taking damage from an entity exploding
     *
     * @since 0.1.0
     */
    ENTITY_EXPLOSION("entity explosion"),

    /**
     * Represents an entity taking damage from landing from too high
     *
     * @since 0.1.0
     */
    FALL("fall"),

    /**
     * Represents an entity taking damage from a block that has fallen on it
     *
     * @since 0.1.0
     */
    FALLING_BLOCK("falling block"),

    /**
     * Represents an entity taking damage from standing in fire
     *
     * @since 0.1.0
     */
    FIRE("fire"),

    /**
     * Represents an entity taking damage from hitting a wall while it's flying
     *
     * @since 0.1.0
     */
    HITTING_WALL_WHILE_FLYING("hitting wall while flying"),

    /**
     * Represents an entity taking damage from being in lava
     *
     * @since 0.1.0
     */
    LAVA("lava"),

    /**
     * Represents an entity taking damage from being struck by lightning
     *
     * @since 0.1.0
     */
    LIGHTNING("lightning"),

    /**
     * Represents an entity taking damage from magma blocks
     *
     * @since 0.1.0
     */
    MAGMA("magma"),

    /**
     * Represents an entity taking damage because of melting
     *
     * @since 0.1.0
     */
    MELTING("melting"),

    /**
     * Represents an entity taking damage due to poison
     *
     * @since 0.1.0
     */
    POISON("poison"),

    /**
     * Represents an entity taking damage because of a potion
     *
     * @since 0.1.0
     */
    POTION("potion"),

    /**
     * Represents an entity taking damage because it's been hit by a projectile
     *
     * @since 0.1.0
     */
    PROJECTILE("projectile"),

    /**
     * Represents an entity taking damage because of starvation
     *
     * @since 0.1.0
     */
    STARVATION("starvation"),

    /**
     * Represents an entity taking damage because it is suffocating inside a block
     *
     * @since 0.1.0
     */
    SUFFOCATION("suffocation"),

    /**
     * Represents an entity killing itself
     *
     * @since 0.1.0
     */
    SUICIDE("suicide"),

    /**
     * Represents an entity being hit by the sweep of a sweep attack
     *
     * @since 0.1.0
     */
    SWEEP_ATTACK("sweep attack"),

    /**
     * Represents an entity being damaged by the thorns enchantment
     *
     * @since 0.1.0
     */
    THORNS("thorns"),

    /**
     * Represents an entity being damaged by an unknown cause
     *
     * @since 0.1.0
     */
    UNKNOWN("unknown"),

    /**
     * Represents an entity being damaged due to falling in the void
     *
     * @since 0.1.0
     */
    VOID("void"),

    /**
     * Represents an entity being damaged because of the wither
     *
     * @since 0.1.0
     */
    WITHER("wither");

    /**
     * The normalized name of the enum
     */
    @NotNull
    private String name;

    /**
     * Creates a damage cause with the normalized name of the enum value
     *
     * @param name the normalized name
     * @since 0.1.0
     */
    DamageCause(@NotNull String name) {
        this.name = name;
    }

    /**
     * Gets the normalized name of this enum value
     *
     * @return the normalized name
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public String getName() {
        return name;
    }
}
