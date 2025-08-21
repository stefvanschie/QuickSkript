package com.github.stefvanschie.quickskript.core.util.registry;

import com.github.stefvanschie.quickskript.core.util.MalformedFileException;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Contains all possible block data
 *
 * @since 0.1.0
 */
public class BlockDataRegistry implements Registry<BlockDataRegistry.Entry> {

    /**
     * All possible block data entries
     */
    @NotNull
    private final Map<String, Entry> entries = new HashMap<>();

    /**
     * Creates a new block data registry with the default block data added to it.
     *
     * @since 0.1.0
     */
    public BlockDataRegistry() {
        addDefaultBlockData();
    }

    /**
     * Adds the default block data to this registry
     *
     * @since 0.1.0
     */
    private void addDefaultBlockData() {
        InputStream stream = getClass().getResourceAsStream("/registry-data/blocks.json");

        if (stream == null) {
            throw new UncheckedIOException(new IOException("Unable to find resource blocks.json"));
        }

        var parser = new JsonStreamParser(new InputStreamReader(stream));

        if (!parser.hasNext()) {
            throw new MalformedFileException("Expected JSON object, but found nothing");
        }

        JsonElement element = parser.next();

        if (!element.isJsonObject()) {
            throw new MalformedFileException("Expected JSON object, but found " + element);
        }

        for (Map.Entry<String, JsonElement> block : element.getAsJsonObject().entrySet()) {
            String blockName = block.getKey();

            if (blockName.indexOf(':') == -1) {
                blockName = "minecraft:" + blockName;
            }

            JsonElement blockStates = block.getValue();

            var blockData = new Entry(blockName);

            if (!blockStates.isJsonObject()) {
                throw new MalformedFileException("Expected JSON object, but found " + blockStates);
            }

            for (Map.Entry<String, JsonElement> state : blockStates.getAsJsonObject().entrySet()) {
                JsonElement array = state.getValue();
                var values = new HashSet<String>();

                if (!array.isJsonArray()) {
                    throw new MalformedFileException("Expected JSON array, but found " + array);
                }

                for (JsonElement value : array.getAsJsonArray()) {
                    values.add(value.getAsString());
                }

                blockData.addPossibleState(state.getKey(), values);
            }

            this.entries.put(blockName, blockData);
        }
    }

    @Nullable
    @Contract(pure = true)
    @Override
    public Entry byName(@NotNull String name) {
        return this.entries.get(name);
    }

    /**
     * Gets the names of all the blocks in this registry. This includes the namespace. If this registry has no blocks,
     * then the return iterable will have no elements.
     *
     * @return an iterable of the names of all blocks
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public Iterable<? extends String> getNames() {
        return this.entries.keySet();
    }

    /**
     * An entry for the block data registry.
     *
     * @since 0.1.0
     */
    public static class Entry {

        /**
         * The block this entry represents
         */
        @NotNull
        private final String block;

        /**
         * The possible states this block can have
         */
        @NotNull
        private final Map<? super String, ? super Collection<? extends String>> possibleStates = new HashMap<>();

        /**
         * Creates an entry with the given block
         *
         * @param block the name of the block of this entry
         * @since 0.1.0
         */
        public Entry(@NotNull String block) {
            this.block = block;
        }

        /**
         * Adds this possible state to this entry. If a state with the given state name already exists, it will be
         * overridden.
         *
         * @param state the name of the state to add
         * @param values the possible values this state may have
         * @since 0.1.0
         */
        public void addPossibleState(@NotNull String state, @NotNull Collection<? extends String> values) {
            this.possibleStates.put(state, values);
        }
    }
}
