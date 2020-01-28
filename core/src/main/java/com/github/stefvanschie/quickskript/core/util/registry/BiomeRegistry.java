package com.github.stefvanschie.quickskript.core.util.registry;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents all possible biomes
 *
 * @since 0.1.0
 */
public class BiomeRegistry {

    /**
     * The entries in this biome registry
     */
    private final Set<Entry> entries = new HashSet<>();

    /**
     * Creates a new biome registry, with the default biomes added to it
     *
     * @since 0.1.0
     */
    public BiomeRegistry() {
        addDefaultBiomes();
    }

    /**
     * Adds an entry to this biome registry
     *
     * @param entry the entry to add
     * @since 0.1.0
     */
    public void addEntry(@NotNull Entry entry) {
        entries.add(entry);
    }

    /**
     * Gets all the biome entries currently in this registry. The returned collection is unmodifiable.
     *
     * @return all biome entries
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public Collection<Entry> getEntries() {
        return Collections.unmodifiableSet(entries);
    }

    /**
     * Adds the default biomes to this biome registry
     *
     * @since 0.1.0
     */
    private void addDefaultBiomes() {
        addEntry(new Entry("badlands"));
        addEntry(new Entry("badlands plateau"));
        addEntry(new Entry("bamboo jungle"));
        addEntry(new Entry("beach"));
        addEntry(new Entry("birch forest"));
        addEntry(new Entry("birch forest hills"));
        addEntry(new Entry("cold ocean"));
        addEntry(new Entry("dark forest"));
        addEntry(new Entry("dark forest hills"));
        addEntry(new Entry("deep cold ocean"));
        addEntry(new Entry("deep frozen ocean"));
        addEntry(new Entry("deep lukewarm ocean"));
        addEntry(new Entry("deep ocean"));
        addEntry(new Entry("desert"));
        addEntry(new Entry("desert hills"));
        addEntry(new Entry("desert lakes"));
        addEntry(new Entry("end barrens"));
        addEntry(new Entry("end highlands"));
        addEntry(new Entry("end midlands"));
        addEntry(new Entry("eroded badlands"));
        addEntry(new Entry("flower forest"));
        addEntry(new Entry("forest"));
        addEntry(new Entry("frozen ocean"));
        addEntry(new Entry("frozen river"));
        addEntry(new Entry("giant spruce taiga"));
        addEntry(new Entry("giant spruce taiga hills"));
        addEntry(new Entry("giant tree taiga"));
        addEntry(new Entry("giant tree taiga hills"));
        addEntry(new Entry("gravelly mountains"));
        addEntry(new Entry("gravelly mountains+"));
        addEntry(new Entry("ice spikes"));
        addEntry(new Entry("jungle"));
        addEntry(new Entry("jungle edge"));
        addEntry(new Entry("jungle hills"));
        addEntry(new Entry("lukewarm ocean"));
        addEntry(new Entry("modified badlands plateau"));
        addEntry(new Entry("modified jungle"));
        addEntry(new Entry("modified jungle edge"));
        addEntry(new Entry("modified wooded badlands plateau"));
        addEntry(new Entry("mountains"));
        addEntry(new Entry("mushroom field shore"));
        addEntry(new Entry("mushroom fields"));
        addEntry(new Entry("nether"));
        addEntry(new Entry("ocean"));
        addEntry(new Entry("plains"));
        addEntry(new Entry("river"));
        addEntry(new Entry("savanna"));
        addEntry(new Entry("savanna plateau"));
        addEntry(new Entry("shattered savanna"));
        addEntry(new Entry("shattered savanna plateau"));
        addEntry(new Entry("small end islands"));
        addEntry(new Entry("snowy beach"));
        addEntry(new Entry("snowy mountains"));
        addEntry(new Entry("snowy taiga"));
        addEntry(new Entry("snowy taiga hills"));
        addEntry(new Entry("snowy taiga mountains"));
        addEntry(new Entry("snowy tundra"));
        addEntry(new Entry("stone shore"));
        addEntry(new Entry("sunflower plains"));
        addEntry(new Entry("swamp"));
        addEntry(new Entry("swamp hills"));
        addEntry(new Entry("taiga"));
        addEntry(new Entry("taiga hills"));
        addEntry(new Entry("taiga mountains"));
        addEntry(new Entry("tall birch forest"));
        addEntry(new Entry("tall birch hills"));
        addEntry(new Entry("the end"));
        addEntry(new Entry("the void"));
        addEntry(new Entry("warm ocean"));
        addEntry(new Entry("wooded badlands plateau"));
        addEntry(new Entry("wooded hills"));
        addEntry(new Entry("wooded mountains"));
    }

    /**
     * An entry for the biome registry
     *
     * @since 0.1.0
     */
    public static class Entry {

        /**
         * The name of the biome
         */
        @NotNull
        private String name;

        /**
         * An entry for the biome registry.
         *
         * @param name the name of the biome to add
         * @since 0.1.0
         */
        public Entry(@NotNull String name) {
            this.name = name;
        }

        /**
         * Gets the name of the biome
         *
         * @return the biome's name
         * @since 0.1.0
         */
        @NotNull
        public String getName() {
            return name;
        }
    }
}
