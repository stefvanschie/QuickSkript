package com.github.stefvanschie.quickskript.core.util.registry;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.util.literal.*;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * A registry containing all the literals
 *
 * @since 0.1.0
 */
public class LiteralRegistry {

    /**
     * A mapping between the name of an entry and the entry
     */
    @NotNull
    private final Map<String, Entry> mapping = new HashMap<>();

    /**
     * Creates a new literal registry populated with the default entries.
     *
     * @since 0.1.0
     */
    public LiteralRegistry() {
        addEntry(new Entry(SkriptPattern.parse("biome[s]"), BiomeRegistry.Entry.class));
        addEntry(new Entry(SkriptPattern.parse("boolean[s]"), boolean.class));
        addEntry(new Entry(SkriptPattern.parse("cat[ ](type|race)[s]"), CatType.class));
        addEntry(new Entry(SkriptPattern.parse("click[ ]type[s]"), ClickType.class));
        addEntry(new Entry(SkriptPattern.parse("colo[u]r[s]"), Color.class));
        addEntry(new Entry(SkriptPattern.parse("damage[ ]cause[s]"), DamageCause.class));
        addEntry(new Entry(SkriptPattern.parse("enchantment[s]"), Enchantment.class));
        addEntry(new Entry(SkriptPattern.parse("experience[ ][point[s]]"), Experience.class));
        addEntry(new Entry(SkriptPattern.parse("firework[ ]type[s]"), FireworkType.class));
        addEntry(new Entry(SkriptPattern.parse("game[ ]mode[s]"), GameMode.class));
        addEntry(new Entry(SkriptPattern.parse("[panda] gene[s]"), Gene.class));
        addEntry(new Entry(SkriptPattern.parse("inventory[ ]action[s]"), InventoryAction.class));
        addEntry(new Entry(SkriptPattern.parse("inventory[ ]type[s]"), InventoryTypeRegistry.Entry.class));
        addEntry(new Entry(SkriptPattern.parse("(item[ ]type[s]|items|materials)"), ItemCategory.class));
        addEntry(new Entry(SkriptPattern.parse("(item|material)"), Item.class));
        addEntry(new Entry(SkriptPattern.parse("num[ber][s]"), Number.class));
        addEntry(new Entry(SkriptPattern.parse("resource[ ]pack[ ]state[s]"), ResourcePackStatus.class));
        addEntry(new Entry(SkriptPattern.parse("sound[ ]categor(y|ies)"), SoundCategory.class));
        addEntry(new Entry(SkriptPattern.parse("spawn[ing][ ]reason[s]"), SpawnReason.class));
        addEntry(new Entry(SkriptPattern.parse("potion[[ ]effect][ ]type[s]"), StatusEffectType.class));
        addEntry(new Entry(SkriptPattern.parse("(text|string)[s]"), Text.class));
        addEntry(new Entry(SkriptPattern.parse("teleport[ ](cause|reason|type)[s]"), TeleportCause.class));
        addEntry(new Entry(SkriptPattern.parse("time[s]"), Time.class));
        addEntry(new Entry(SkriptPattern.parse("(time[ ]period[s]|duration[s])"), TimePeriod.class));
        addEntry(new Entry(SkriptPattern.parse("time[ ]span[s]"), TimeSpan.class));
        addEntry(new Entry(SkriptPattern.parse("(tree[ ]type[s]|tree[s])"), TreeType.class));
    }

    /**
     * Adds the specified entry to the registry
     *
     * @param entry the entry to add
     * @since 0.1.0
     */
    public void addEntry(@NotNull Entry entry) {
        for (String name : entry.names) {
            mapping.put(name, entry);
        }
    }

    /**
     * Gets an entry in this registry by the given name. If the provided name doesn't have an entry in this registry,
     * null will be returned.
     *
     * @param name the name of the entry
     * @return the entry
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public Entry byName(@NotNull String name) {
        return mapping.get(name);
    }

    /**
     * An entry for the literal registry
     *
     * @since 0.1.0
     */
    public static class Entry {

        /**
         * The names for this entry
         */
        @NotNull
        private final Collection<String> names;

        /**
         * The class that this entry represents
         */
        @NotNull
        private final Class<?> clazz;

        /**
         * Creates a new entry with the pattern for the possible names and the clazz representing which literal this
         * entry represents.
         *
         * @param pattern the pattern for the names
         * @param clazz the class
         * @since 0.1.0
         */
        public Entry(@NotNull SkriptPattern pattern, @NotNull Class<?> clazz) {
            this.names = pattern.unrollFully();
            this.clazz = clazz;
        }

        /**
         * Gets the class of this entry (the class this entry represents, not LiteralRegistry.Entry.class).
         *
         * @return the class
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public Class<?> getType() {
            return clazz;
        }
    }
}
