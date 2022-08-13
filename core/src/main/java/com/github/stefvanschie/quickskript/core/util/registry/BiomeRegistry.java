package com.github.stefvanschie.quickskript.core.util.registry;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Represents all possible biomes
 *
 * @since 0.1.0
 */
public class BiomeRegistry {

    /**
     * The entries in this biome registry
     */
    private final Map<String, Entry> entries = new HashMap<>();

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
        for (String name : entry.getNames()) {
            entries.put(name, entry);
        }
    }

    /**
     * Gets the biome entry by the given name.
     *
     * @return the biome or null if no biome by the provided name exists
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public Entry byName(@NotNull String name) {
        return entries.get(name);
    }

    /**
     * Adds the default biomes to this biome registry
     *
     * @since 0.1.0
     */
    private void addDefaultBiomes() {
        addEntry(new Entry("mesa", "badlands"));
        addEntry(new Entry("bamboo jungle"));
        addEntry(new Entry("basalt deltas"));
        addEntry(new Entry("beach"));
        addEntry(new Entry("birch forest"));
        addEntry(new Entry("cold ocean"));
        addEntry(new Entry("crimson forest"));
        addEntry(new Entry("custom", "unknown"));
        addEntry(new Entry("roofed forest", "black forest", "dark forest"));
        addEntry(new Entry("deep cold ocean"));
        addEntry(new Entry("deep dark"));
        addEntry(new Entry("deep frozen ocean"));
        addEntry(new Entry("deep lukewarm ocean"));
        addEntry(new Entry("deep ocean"));
        addEntry(new Entry("desert"));
        addEntry(new Entry("dripstone caves"));
        addEntry(new Entry("end barrens"));
        addEntry(new Entry("end highlands"));
        addEntry(new Entry("end midlands"));
        addEntry(new Entry("eroded badlands", "eroded mesa"));
        addEntry(new Entry("flower forest"));
        addEntry(new Entry("forest"));
        addEntry(new Entry("frozen ocean"));
        addEntry(new Entry("frozen peaks"));
        addEntry(new Entry("frozen river"));
        addEntry(new Entry("grove"));
        addEntry(new Entry("ice plains spikes", "spiked ice plains", "ice plains with spikes", "ice spikes"));
        addEntry(new Entry("jagged peaks"));
        addEntry(new Entry("jungle"));
        addEntry(new Entry("lukewarm ocean"));
        addEntry(new Entry("lush caves"));
        addEntry(new Entry("mangrove swamp"));
        addEntry(new Entry("meadow"));
        addEntry(new Entry("mushroom island", "mushroom fields"));
        addEntry(new Entry("nether wastes", "nether", "hell"));
        addEntry(new Entry("ocean, sea"));
        addEntry(new Entry("old growth birch forest", "tall birch forest"));
        addEntry(new Entry("old growth pine taiga", "giant tree taiga"));
        addEntry(new Entry("old growth spruce taiga", "giant spruce taiga"));
        addEntry(new Entry("plains"));
        addEntry(new Entry("river"));
        addEntry(new Entry("savanna"));
        addEntry(new Entry("savanna plateau"));
        addEntry(new Entry("small end islands"));
        addEntry(new Entry("cold beach", "snowy beach"));
        addEntry(new Entry("snowy plains", "snowy tundra"));
        addEntry(new Entry("snowy slopes"));
        addEntry(new Entry("cold taiga", "snowy taiga"));
        addEntry(new Entry("soul sand valley"));
        addEntry(new Entry("sparse jungle", "jungle edge"));
        addEntry(new Entry("stony peaks"));
        addEntry(new Entry("stony shore", "stone shore"));
        addEntry(new Entry("sunflower plains"));
        addEntry(new Entry("swampland, swamp, marsh"));
        addEntry(new Entry("taiga"));
        addEntry(new Entry("sky", "the end"));
        addEntry(new Entry("void", "the void"));
        addEntry(new Entry("warm ocean"));
        addEntry(new Entry("warped forest"));
        addEntry(new Entry("windswept forest", "wooded mountains"));
        addEntry(new Entry("windswept gravelly hills", "gravelly mountains"));
        addEntry(new Entry("windswept hills", "mountains"));
        addEntry(new Entry("windswept savanna", "shattered savanna"));
        addEntry(new Entry("wooded badlands", "wooded mesa", "mesa forest", "badlands forest"));
    }

    /**
     * An entry for the biome registry
     *
     * @since 0.1.0
     */
    public static class Entry {

        /**
         * The names of the biome
         */
        @NotNull
        private final String[] names;

        /**
         * An entry for the biome registry.
         *
         * @param names the names of the biome to add
         * @since 0.1.0
         */
        public Entry(@NotNull String... names) {
            this.names = names;
        }

        /**
         * Gets the names of the biome
         *
         * @return the biome's names
         * @since 0.1.0
         */
        @NotNull
        public String[] getNames() {
            return names;
        }
    }
}
