package com.github.stefvanschie.quickskript.core.util.registry;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * A registry that contains all item types
 *
 * @since 0.1.0
 */
public class ItemTypeRegistry implements Registry<String> {

    /**
     * The entries of this registry
     */
    @NotNull
    private final Map<@NotNull String, @NotNull String> entries = new HashMap<>();

    /**
     * The name of the Minecraft namespace.
     */
    @NotNull
    private static final String MINECRAFT_NAMESPACE = "minecraft";

    /**
     * The character that separates a namespace from the key.
     */
    private static final char NAMESPACE_KEY_SEPARATOR = ':';

    /**
     * Creates a new item type registry and adds the default item types to it
     *
     * @param registry the registry containing block data
     * @since 0.1.0
     */
    public ItemTypeRegistry(@NotNull BlockDataRegistry registry) {
        addDefaultItemTypes(registry);
    }

    @Nullable
    @Contract(pure = true)
    @Override
    public String byName(@NotNull String name) {
        return entries.get(name);
    }

    /**
     * Gets all the entries in this registry
     *
     * @return all entries
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public Map<@NotNull String, @NotNull String> getEntries() {
        return Collections.unmodifiableMap(this.entries);
    }

    /**
     * Adds all the default item types to this registry
     *
     * @since 0.1.0
     */
    private void addDefaultItemTypes(@NotNull BlockDataRegistry blockDataRegistry) {
        for (String name : blockDataRegistry.getNames()) {
            int index = name.indexOf(NAMESPACE_KEY_SEPARATOR);

            if (index == -1) {
                throw new IllegalStateException("Returned block name is not a proper namespaced key");
            }

            String key = name.substring(index + 1).replace('_', ' ');

            this.entries.put(key, name);
            this.entries.put(key + 's', name);
        }

        URL items = getClass().getResource("/registry-data/items.txt");

        if (items == null) {
            throw new IllegalStateException("Unable to find items.txt");
        }

        try (InputStream inputStream = items.openStream()) {
            String[] lines = new String(inputStream.readAllBytes()).split("[\r\n]+");

            for (String line : lines) {
                String name = line.replace('_', ' ');

                this.entries.put(name, MINECRAFT_NAMESPACE + NAMESPACE_KEY_SEPARATOR + line);
                this.entries.put(name + 's', MINECRAFT_NAMESPACE + NAMESPACE_KEY_SEPARATOR + line);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
