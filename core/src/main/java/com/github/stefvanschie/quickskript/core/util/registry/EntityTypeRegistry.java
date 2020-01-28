package com.github.stefvanschie.quickskript.core.util.registry;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents all possible entity types
 *
 * @since 0.1.0
 */
public class EntityTypeRegistry {

    /**
     * The entries of this registry
     */
    private Set<Entry> entries = new HashSet<>();

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
        entries.add(entry);
    }

    /**
     * Gets all the entries currently in this registry. The returned collection is unmodifiable.
     *
     * @return the entries
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public Collection<Entry> getEntries() {
        return Collections.unmodifiableSet(entries);
    }

    /**
     * Add the default entity types to the registry
     *
     * @since 0.1.0
     */
    private void addDefaultEntityTypes() {
        addEntry(new Entry("area effect cloud"));
        addEntry(new Entry("armor stand"));
        addEntry(new Entry("arrow"));
        addEntry(new Entry("bat"));
        addEntry(new Entry("bee"));
        addEntry(new Entry("blaze"));
        addEntry(new Entry("boat"));
        addEntry(new Entry("cat"));
        addEntry(new Entry("cave spider"));
        addEntry(new Entry("chicken"));
        addEntry(new Entry("cod"));
        addEntry(new Entry("cow"));
        addEntry(new Entry("creeper"));
        addEntry(new Entry("dolphin"));
        addEntry(new Entry("donkey"));
        addEntry(new Entry("dragon fireball"));
        addEntry(new Entry("dropped item"));
        addEntry(new Entry("drowned"));
        addEntry(new Entry("egg"));
        addEntry(new Entry("elder guardian"));
        addEntry(new Entry("ender crystal"));
        addEntry(new Entry("ender dragon"));
        addEntry(new Entry("ender pearl"));
        addEntry(new Entry("ender signal"));
        addEntry(new Entry("enderman"));
        addEntry(new Entry("endermite"));
        addEntry(new Entry("evoker"));
        addEntry(new Entry("evoker fangs"));
        addEntry(new Entry("experience orb"));
        addEntry(new Entry("falling block"));
        addEntry(new Entry("fireball"));
        addEntry(new Entry("firework"));
        addEntry(new Entry("fishing hook"));
        addEntry(new Entry("fox"));
        addEntry(new Entry("ghast"));
        addEntry(new Entry("giant"));
        addEntry(new Entry("guardian"));
        addEntry(new Entry("horse"));
        addEntry(new Entry("husk"));
        addEntry(new Entry("illusioner"));
        addEntry(new Entry("iron golem"));
        addEntry(new Entry("item frame"));
        addEntry(new Entry("leash hitch"));
        addEntry(new Entry("lightning"));
        addEntry(new Entry("llama"));
        addEntry(new Entry("llama spit"));
        addEntry(new Entry("magma cube"));
        addEntry(new Entry("minecart"));
        addEntry(new Entry("minecart chest"));
        addEntry(new Entry("minecart command"));
        addEntry(new Entry("minecart furnace"));
        addEntry(new Entry("minecart hopper"));
        addEntry(new Entry("minecart mob spawner"));
        addEntry(new Entry("minecart tnt"));
        addEntry(new Entry("mule"));
        addEntry(new Entry("mushroom cow"));
        addEntry(new Entry("ocelot"));
        addEntry(new Entry("painting"));
        addEntry(new Entry("panda"));
        addEntry(new Entry("parrot"));
        addEntry(new Entry("phantom"));
        addEntry(new Entry("pig"));
        addEntry(new Entry("pig zombie"));
        addEntry(new Entry("pillager"));
        addEntry(new Entry("player"));
        addEntry(new Entry("polar bear"));
        addEntry(new Entry("primed tnt"));
        addEntry(new Entry("pufferfish"));
        addEntry(new Entry("rabbit"));
        addEntry(new Entry("ravager"));
        addEntry(new Entry("salmon"));
        addEntry(new Entry("sheep"));
        addEntry(new Entry("shulker"));
        addEntry(new Entry("shulker bullet"));
        addEntry(new Entry("silverfish"));
        addEntry(new Entry("skeleton"));
        addEntry(new Entry("skeleton horse"));
        addEntry(new Entry("slime"));
        addEntry(new Entry("small fireball"));
        addEntry(new Entry("snowball"));
        addEntry(new Entry("snowman"));
        addEntry(new Entry("spectral arrow"));
        addEntry(new Entry("spider"));
        addEntry(new Entry("splash potion"));
        addEntry(new Entry("squid"));
        addEntry(new Entry("stray"));
        addEntry(new Entry("thrown exp bottle"));
        addEntry(new Entry("trader llama"));
        addEntry(new Entry("trident"));
        addEntry(new Entry("tropical fish"));
        addEntry(new Entry("turtle"));
        addEntry(new Entry("unknown"));
        addEntry(new Entry("vex"));
        addEntry(new Entry("villager"));
        addEntry(new Entry("vindicator"));
        addEntry(new Entry("wandering trader"));
        addEntry(new Entry("witch"));
        addEntry(new Entry("wither"));
        addEntry(new Entry("wither skeleton"));
        addEntry(new Entry("wither skull"));
        addEntry(new Entry("wolf"));
        addEntry(new Entry("zombie"));
        addEntry(new Entry("zombie horse"));
        addEntry(new Entry("zombie villager"));
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
        private String name;

        /**
         * Creates a new entity type entry by the given name
         *
         * @param name the name of the entity type
         * @since 0.1.0
         */
        public Entry(@NotNull String name) {
            this.name = name;
        }

        /**
         * Gets the entity type of this entry
         *
         * @return the entity type
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public String getName() {
            return name;
        }
    }
}
