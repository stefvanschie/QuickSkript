package com.github.stefvanschie.quickskript.core.util.registry;

import com.github.stefvanschie.quickskript.core.util.MalformedFileException;
import com.github.stefvanschie.quickskript.core.util.literal.Enchantment;
import com.google.gson.JsonArray;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Registry for looking up enchantments.
 *
 * @since 0.1.0
 */
public class EnchantmentRegistry implements Registry<Enchantment> {

    /**
     * The entries in this registry.
     */
    private final Map<String, Enchantment> entries = new HashMap<>();

    /**
     * The name of the Minecraft namespace.
     */
    private static final String MINECRAFT_NAMESPACE = "minecraft";

    /**
     * The character that divides the namespace and the key.
     */
    private static final char NAMESPACE_SEPARATOR = ':';

    /**
     * Creates a new enchantment registry. This initialises the registry by reading the enchantments from a file into
     * memory.
     *
     * @since 0.1.0
     */
    public EnchantmentRegistry() {
        addDefaultEnchantments();
    }

    @Nullable
    @Contract(pure = true)
    @Override
    public Enchantment byName(@NotNull String name) {
        return entries.get(name);
    }

    /**
     * Add the default enchantments to the registry.
     *
     * @since 0.1.0
     */
    private void addDefaultEnchantments() {
        InputStream stream = getClass().getResourceAsStream("/registry-data/enchantments.json");

        if (stream == null) {
            throw new UncheckedIOException(new IOException("Unable to find resource enchantments.json"));
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
            JsonElement names = entry.getValue();

            if (!names.isJsonArray()) {
                throw new MalformedFileException("Expected a JSON array, but found " + names);
            }

            JsonArray nameArray = names.getAsJsonArray();

            String key = entry.getKey();

            String namespacedKey = MINECRAFT_NAMESPACE + NAMESPACE_SEPARATOR + key;

            for (JsonElement name : nameArray) {
                if (!name.isJsonPrimitive()) {
                    throw new MalformedFileException("Expected a JSON primitive, but found " + name);
                }

                JsonPrimitive namePrimitive = name.getAsJsonPrimitive();

                if (!namePrimitive.isString()) {
                    throw new MalformedFileException("Expected a JSON string, but found " + namePrimitive);
                }

                addEntry(namespacedKey, namePrimitive.getAsString());
            }

            addEntry(namespacedKey, namespacedKey);
            addEntry(namespacedKey, key);
        }
    }

    /**
     * Adds the specified entry to this registry
     *
     * @param key the namespaced key of the enchantment
     * @param entry the entry to add
     * @since 0.1.0
     */
    private void addEntry(@NotNull String key, @NotNull String entry) {
        this.entries.put(entry, new Enchantment(key));
    }
}
