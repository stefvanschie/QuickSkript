package com.github.stefvanschie.quickskript.core.util.registry;

import com.github.stefvanschie.quickskript.core.util.MalformedFileException;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonStreamParser;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
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
        InputStream stream = getClass().getResourceAsStream("/registry-data/entities.json");

        if (stream == null) {
            throw new UncheckedIOException(new IOException("Unable to find resource entities.json"));
        }

        var parser = new JsonStreamParser(new InputStreamReader(stream));

        if (!parser.hasNext()) {
            throw new MalformedFileException("Expected JSON object, but found nothing");
        }

        JsonElement element = parser.next();

        if (!element.isJsonObject()) {
            throw new MalformedFileException("Expected JSON object, but found " + element);
        }

        for (Map.Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
            JsonElement nameElement = entry.getValue();

            if (!nameElement.isJsonPrimitive()) {
                throw new MalformedFileException("Expected a JSON primitive, but found " + nameElement);
            }

            JsonPrimitive namePrimitive = nameElement.getAsJsonPrimitive();

            if (!namePrimitive.isString()) {
                throw new MalformedFileException("Expected a JSON string, but found " + namePrimitive);
            }

            addEntry(new Entry(namePrimitive.getAsString(), entry.getKey()));
        }

        addEntry(new Entry("unknown", null));
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
