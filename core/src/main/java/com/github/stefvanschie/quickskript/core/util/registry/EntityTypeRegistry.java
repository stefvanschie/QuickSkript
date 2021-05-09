package com.github.stefvanschie.quickskript.core.util.registry;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Represents all possible entity types
 *
 * @since 0.1.0
 */
public class EntityTypeRegistry {

    /**
     * The entries of this registry
     */
    @NotNull
    private final Map<String, Entry> entries = new HashMap<>();

    /**
     * Creates a new entity type registry and initializes it with the default entity types
     *
     * @since 0.1.0
     */
    public EntityTypeRegistry() {
        addDefaultEntityTypes();
    }

    /**
     * Adds the specified entry to this registry
     * 
     * @param entry the entry to add
     * @since 0.1.0
     */
    public void addEntry(@NotNull Entry entry) {
        entries.put(entry.getName(), entry);
    }

    /**
     * Gets the entity type by name or null if no such entity type exists.
     *
     * @param name the name of the entity type
     * @return the entity type or null
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public Entry byName(@NotNull String name) {
        return entries.get(name);
    }

    /**
     * Add the default entity types to the registry
     *
     * @since 0.1.0
     */
    private void addDefaultEntityTypes() {
        addEntry(new Entry("area effect cloud", "minecraft:area_effect_cloud"));
        addEntry(new Entry("armor stand", "minecraft:armor_stand"));
        addEntry(new Entry("arrow", "minecraft:arrow"));
        addEntry(new Entry("bat", "minecraft:bat"));
        addEntry(new Entry("bee", "minecraft:bee"));
        addEntry(new Entry("blaze", "minecraft:blaze"));
        addEntry(new Entry("boat", "minecraft:boat"));
        addEntry(new Entry("cat", "minecraft:cat"));
        addEntry(new Entry("cave spider", "minecraft:cave_spider"));
        addEntry(new Entry("chicken", "minecraft:chicken"));
        addEntry(new Entry("cod", "minecraft:cod"));
        addEntry(new Entry("cow", "minecraft:cow"));
        addEntry(new Entry("creeper", "minecraft:creeper"));
        addEntry(new Entry("dolphin", "minecraft:dolphin"));
        addEntry(new Entry("donkey", "minecraft:donkey"));
        addEntry(new Entry("dragon fireball", "minecraft:dragon_fireball"));
        addEntry(new Entry("dropped item", "minecraft:item"));
        addEntry(new Entry("drowned", "minecraft:drowned"));
        addEntry(new Entry("egg", "minecraft:egg"));
        addEntry(new Entry("elder guardian", "minecraft:elder_guardian"));
        addEntry(new Entry("ender crystal", "minecraft:end_crystal"));
        addEntry(new Entry("ender dragon", "minecraft:ender_dragon"));
        addEntry(new Entry("ender pearl", "minecraft:ender_pearl"));
        addEntry(new Entry("ender signal", "minecraft:eye_of_ender"));
        addEntry(new Entry("enderman", "minecraft:enderman"));
        addEntry(new Entry("endermite", "minecraft:endermite"));
        addEntry(new Entry("evoker", "minecraft:evoker"));
        addEntry(new Entry("evoker fangs", "minecraft:evoker_fangs"));
        addEntry(new Entry("experience orb", "minecraft:experience_orb"));
        addEntry(new Entry("falling block", "minecraft:falling_block"));
        addEntry(new Entry("fireball", "minecraft:fireball"));
        addEntry(new Entry("firework", "minecraft:firework_rocket"));
        addEntry(new Entry("fishing hook", "minecraft:fishing_bobber"));
        addEntry(new Entry("fox", "minecraft:fox"));
        addEntry(new Entry("ghast", "minecraft:ghast"));
        addEntry(new Entry("giant", "minecraft:giant"));
        addEntry(new Entry("guardian", "minecraft:guardian"));
        addEntry(new Entry("horse", "minecraft:horse"));
        addEntry(new Entry("husk", "minecraft:husk"));
        addEntry(new Entry("illusioner", "minecraft:illusioner"));
        addEntry(new Entry("iron golem", "minecraft:iron_golem"));
        addEntry(new Entry("item frame", "minecraft:item_frame"));
        addEntry(new Entry("leash hitch", "minecraft:leash_knot"));
        addEntry(new Entry("lightning", "minecraft:lightning_bolt"));
        addEntry(new Entry("llama", "minecraft:llama"));
        addEntry(new Entry("llama spit", "minecraft:llama_spit"));
        addEntry(new Entry("magma cube", "minecraft:magma_cube"));
        addEntry(new Entry("minecart", "minecraft:minecart"));
        addEntry(new Entry("minecart chest", "minecraft:chest_minecart"));
        addEntry(new Entry("minecart command", "minecraft:command_block_minecart"));
        addEntry(new Entry("minecart furnace", "minecraft:furnace_minecart"));
        addEntry(new Entry("minecart hopper", "minecraft:hopper_minecart"));
        addEntry(new Entry("minecart mob spawner", "minecraft:spawner_minecart"));
        addEntry(new Entry("minecart tnt", "minecraft:tnt_minecart"));
        addEntry(new Entry("mule", "minecraft:mule"));
        addEntry(new Entry("mushroom cow", "minecraft:mooshroom"));
        addEntry(new Entry("ocelot", "minecraft:ocelot"));
        addEntry(new Entry("painting", "minecraft:painting"));
        addEntry(new Entry("panda", "minecraft:panda"));
        addEntry(new Entry("parrot", "minecraft:parrot"));
        addEntry(new Entry("phantom", "minecraft:phantom"));
        addEntry(new Entry("pig", "minecraft:pig"));
        addEntry(new Entry("pig zombie", "minecraft:zombified_piglin"));
        addEntry(new Entry("pillager", "minecraft:pillager"));
        addEntry(new Entry("player", "minecraft:player"));
        addEntry(new Entry("polar bear", "minecraft:polar_bear"));
        addEntry(new Entry("primed tnt", "minecraft:tnt"));
        addEntry(new Entry("pufferfish", "minecraft:pufferfish"));
        addEntry(new Entry("rabbit", "minecraft:rabbit"));
        addEntry(new Entry("ravager", "minecraft:ravager"));
        addEntry(new Entry("salmon", "minecraft:salmon"));
        addEntry(new Entry("sheep", "minecraft:sheep"));
        addEntry(new Entry("shulker", "minecraft:shulker"));
        addEntry(new Entry("shulker bullet", "minecraft:shulker_bullet"));
        addEntry(new Entry("silverfish", "minecraft:silverfish"));
        addEntry(new Entry("skeleton", "minecraft:skeleton"));
        addEntry(new Entry("skeleton horse", "minecraft:skeleton_horse"));
        addEntry(new Entry("slime", "minecraft:slime"));
        addEntry(new Entry("small fireball", "minecraft:small_fireball"));
        addEntry(new Entry("snowball", "minecraft:snowball"));
        addEntry(new Entry("snowman", "minecraft:snow_golem"));
        addEntry(new Entry("spectral arrow", "minecraft:spectral_arrow"));
        addEntry(new Entry("spider", "minecraft:spider"));
        addEntry(new Entry("splash potion", "minecraft:potion"));
        addEntry(new Entry("squid", "minecraft:squid"));
        addEntry(new Entry("stray", "minecraft:stray"));
        addEntry(new Entry("thrown exp bottle", "minecraft:experience_bottle"));
        addEntry(new Entry("trader llama", "minecraft:trader_llama"));
        addEntry(new Entry("trident", "minecraft:trident"));
        addEntry(new Entry("tropical fish", "minecraft:tropical_fish"));
        addEntry(new Entry("turtle", "minecraft:turtle"));
        addEntry(new Entry("unknown", null));
        addEntry(new Entry("vex", "minecraft:vex"));
        addEntry(new Entry("villager", "minecraft:villager"));
        addEntry(new Entry("vindicator", "minecraft:vindicator"));
        addEntry(new Entry("wandering trader", "minecraft:wandering_trader"));
        addEntry(new Entry("witch", "minecraft:witch"));
        addEntry(new Entry("wither", "minecraft:wither"));
        addEntry(new Entry("wither skeleton", "minecraft:wither_skeleton"));
        addEntry(new Entry("wither skull", "minecraft:wither_skull"));
        addEntry(new Entry("wolf", "minecraft:wolf"));
        addEntry(new Entry("zombie", "minecraft:zombie"));
        addEntry(new Entry("zombie horse", "minecraft:zombie_horse"));
        addEntry(new Entry("zombie villager", "minecraft:zombie_villager"));
    }

    /**
     * An entry for the entity type registry
     *
     * @since 0.1.0
     */
    public static class Entry {

        /**
         * The name of the entity type
         */
        @NotNull
        private final String name;

        /**
         * The key of this entity type. Null if this represents an unknown entity type.
         */
        @Nullable
        private final String key;

        /**
         * Creates a new entity type entry by the given name. The key should only be null for unknown entities.
         *
         * @param name the name of the entity type
         * @param key the key of the entity type
         * @since 0.1.0
         */
        public Entry(@NotNull String name, @Nullable String key) {
            this.name = name;
            this.key = key;
        }

        /**
         * Gets the name of this entry.
         *
         * @return the name
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public String getName() {
            return name;
        }

        /**
         * Gets the key of this entry or null if this entity type is unknown.
         *
         * @return the key or null
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        public String getKey() {
            return key;
        }
    }
}
