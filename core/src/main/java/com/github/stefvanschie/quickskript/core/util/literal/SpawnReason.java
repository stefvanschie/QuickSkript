package com.github.stefvanschie.quickskript.core.util.literal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Represents different reasons a creature could have spawned
 *
 * @since 0.1.0
 */
public enum SpawnReason {

    /**
     * When a creature is spawned because of a beehive
     *
     * @since 0.1.0
     */
    BEEHIVE("beehive"),

    /**
     * When a creature is spawned because of breeding
     *
     * @since 0.1.0
     */
    BREEDING("breed", "breeding"),

    /**
     * When a creature is spawned because of building an iron golem
     *
     * @since 0.1.0
     */
    BUILD_IRON_GOLEM("built iron golem", "build iron golem"),

    /**
     * When a creature is spawned because of building a snow golem
     *
     * @since 0.1.0
     */
    BUILD_SNOW_GOLEM("built snowman", "build snowman"),

    /**
     * When a creature is spawned because of building a wither
     *
     * @since 0.1.0
     */
    BUILD_WITHER("built wither", "build wither"),

    /**
     * When a creature is spawned because of chunk generation
     *
     * @since 0.1.0
     */
    CHUNK_GENERATION("chunk gen"),

    /**
     * When a creature is spawned because of curing
     *
     * @since 0.1.0
     */
    CURED("cured"),

    /**
     * When a creature is spawned because of a custom reason
     *
     * @since 0.1.0
     */
    CUSTOM("customized", "customised", "custom"),

    /**
     * When a creature is spawned normally
     *
     * @since 0.1.0
     */
    DEFAULT("default"),

    /**
     * When a creature is spawned because of a dispenser dispensing an egg
     *
     * @since 0.1.0
     */
    DISPENSE_EGG("dispense egg", "dispensing egg"),

    /**
     * When a creature is spawned because of drowning
     *
     * @since 0.1.0
     */
    DROWNED("drowned"),

    /**
     * When a creature is spawned because of an egg
     *
     * @since 0.1.0
     */
    EGG("egg"),

    /**
     * When a creature is spawned because of an ender pearl
     *
     * @since 0.1.0
     */
    ENDER_PEARL("ender pearl"),

    /**
     * When a creature is spawned because of an explosion
     *
     * @since 0.1.0
     */
    EXPLOSION("explosion"),

    /**
     * When a creature is spawned because of an infection
     *
     * @since 0.1.0
     */
    INFECTION("infection", "infected"),

    /**
     * When a creature is spawned because of a jockey
     *
     * @since 0.1.0
     */
    JOCKEY("jockey"),
    /**
     * When a creature is spawned because of lightning
     *
     * @since 0.1.0
     */
    LIGHTNING("lightning"),

    /**
     * When a creature is spawned because of mounting
     *
     * @since 0.1.0
     */
    MOUNT("mount"),

    /**
     * When a creature is spawned naturally
     *
     * @since 0.1.0
     */
    NATURAL("natural"),

    /**
     * When a creature is spawned because of a nether portal
     *
     * @since 0.1.0
     */
    NETHER_PORTAL("nether portal"),

    /**
     * When a creature is spawned because of an ocelot baby
     *
     * @since 0.1.0
     */
    OCELOT_BABY("ocelot baby"),

    /**
     * When a creature is spawned because of a patrol
     *
     * @since 0.1.0
     */
    PATROL("patrol"),

    /**
     * When a creature is spawned because of a raid
     *
     * @since 0.1.0
     */
    RAID("raid"),

    /**
     * When a creature is spawned because of reinforcements
     *
     * @since 0.1.0
     */
    REINFORCEMENTS("reinforcements"),

    /**
     * When a creature is spawned because of shearing
     *
     * @since 0.1.0
     */
    SHEARED("sheared"),

    /**
     * When a creature is spawned because of perching
     *
     * @since 0.1.0
     */
    SHOULDER_ENTITY("perching", "shoulder"),

    /**
     * When a creature is spawned because of a silverfish block
     *
     * @since 0.1.0
     */
    SILVERFISH_BLOCK("silverfish reveal", "silverfish trap"),

    /**
     * When a creature is spawned because of a slime splitting
     *
     * @since 0.1.0
     */
    SLIME_SPLIT("slime split"),

    /**
     * When a creature is spawned because of a spawner
     *
     * @since 0.1.0
     */
    SPAWNER("mob spawner", "creature spawner", "spawner"),

    /**
     * When a creature is spawned because of a spawn egg
     *
     * @since 0.1.0
     */
    SPAWN_EGG("spawn egg"),

    /**
     * When a creature is spawned because of a trap
     *
     * @since 0.1.0
     */
    TRAP("trap"),

    /**
     * When a creature is spawned because of a village defense
     *
     * @since 0.1.0
     */
    VILLAGE_DEFENSE("village defense", "golem defense", "iron golem defense"),

    /**
     * When a creature is spawned because of a village invasion
     *
     * @since 0.1.0
     */
    VILLAGE_INVASION("village invasion", "village invading");

    /**
     * The aliases for the provided spawn reason
     *
     * @since 0.1.0
     */
    private final String[] aliases;

    /**
     * Creates a spawn reason with the provided aliases
     *
     * @param aliases the aliases
     * @since 0.1.0
     */
    SpawnReason(@NotNull String... aliases) {
        this.aliases = aliases;
    }

    /**
     * Returns a modifiable copy of the aliases of this spawn reason
     *
     * @return the aliases
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public String[] getAliases() {
        return Arrays.copyOf(aliases, aliases.length);
    }
}
