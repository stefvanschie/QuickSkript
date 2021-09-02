package com.github.stefvanschie.quickskript.core.util.literal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a block with data attached to it.
 *
 * @since 0.1.0
 */
public class BlockData {

    /**
     * The material of the block.
     */
    @NotNull
    private final String block;

    /**
     * The data attached to the block.
     */
    @NotNull
    private final Map<String, String> data = new HashMap<>();

    /**
     * Creates a new block data for the given block. The block provided should be the text representation of the block,
     * including namespace.
     *
     * @param block the block
     * @since 0.1.0
     */
    public BlockData(@NotNull String block) {
        int colonIndex = block.indexOf(':');

        if (colonIndex == -1 || colonIndex == 0 || colonIndex == block.length() - 1) {
            throw new IllegalArgumentException("Provided block does not have a namespace or key");
        }

        this.block = block;
    }

    /**
     * Converts this block data to a string. This includes the full namespaced key as well as all additional data. If
     * this block data has no data, then the square brackets intended for the data, will not be included.
     *
     * @return a textual representation of this block data
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public String convertToString() {
        if (data.isEmpty()) {
            return block;
        }

        var builder = new StringBuilder(block);

        builder.append('[');

        for (Map.Entry<String, String> entry : data.entrySet()) {
            builder.append(entry.getKey());
            builder.append('=');
            builder.append(entry.getValue());
            builder.append(',');
        }

        builder.deleteCharAt(builder.length() - 1);

        builder.append(']');

        return builder.toString();
    }

    /**
     * Adds the given data to the block data. If data with the given key already exists, the old key/value pair will be
     * overridden.
     *
     * @param key the key
     * @param value the value
     * @since 0.1.0
     */
    public void addData(@NotNull String key, @NotNull String value) {
        this.data.put(key, value);
    }
}
