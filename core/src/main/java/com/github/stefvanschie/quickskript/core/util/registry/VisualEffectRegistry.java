package com.github.stefvanschie.quickskript.core.util.registry;

import com.github.stefvanschie.quickskript.core.file.alias.AliasFile;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Represents all possible visual effects
 *
 * @since 0.1.0
 */
public class VisualEffectRegistry {

    /**
     * The entries of this registry
     */
    @NotNull
    private final Set<Entry> entries = new HashSet<>();

    /**
     * Creates a new visual effect registry with the default visual effects.
     *
     * @since 0.1.0
     */
    public VisualEffectRegistry() {
        addDefaultVisualEffects();
    }

    /**
     * Adds an entry to this registry
     *
     * @param entry the entry to add
     * @since 0.1.0
     */
    public void addEntry(@NotNull Entry entry) {
        entries.add(entry);
    }

    /**
     * Adds the default visual effects to this registry.
     *
     * @since 0.1.0
     */
    private void addDefaultVisualEffects() {
        URL url = getClass().getResource("/registry-data/visual-effect.alias");

        if (url == null) {
            throw new IllegalStateException("Visual effects file missing");
        }

        AliasFile file;

        try {
            file = AliasFile.parse(url);
        } catch (IOException exception) {
            exception.printStackTrace();
            return;
        }

        for (SkriptPattern entry : file.getEntries()) {
            addEntry(new VisualEffectRegistry.Entry(entry));
        }
    }

    /**
     * Gets all entries this registry contains. The returned collection is immutable.
     *
     * @return all entries
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public Collection<Entry> getEntries() {
        return Collections.unmodifiableSet(entries);
    }

    /**
     * An entry for the visual effect registry
     *
     * @since 0.1.0
     */
    public static class Entry {

        /**
         * The pattern of the visual effect
         */
        @NotNull
        private SkriptPattern pattern;

        /**
         * An entry for the visual effect registry.
         *
         * @param pattern the pattern of the visual effect to add
         * @since 0.1.0
         */
        public Entry(@NotNull SkriptPattern pattern) {
            this.pattern = pattern;
        }

        /**
         * Gets the name of the visual effect
         *
         * @return the visual effect's name
         * @since 0.1.0
         */
        @NotNull
        public SkriptPattern getPattern() {
            return pattern;
        }
    }
}
