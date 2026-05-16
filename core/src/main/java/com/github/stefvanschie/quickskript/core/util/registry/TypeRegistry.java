package com.github.stefvanschie.quickskript.core.util.registry;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * A registry containing all the types.
 *
 * @since 0.1.0
 */
public class TypeRegistry implements Registry<TypeRegistry.Entry> {

    /**
     * A mapping between the name of an entry and the entry
     */
    @NotNull
    private final Map<String, Entry> mappingByName = new HashMap<>();

    /**
     * A mapping between the type of entry and the entries.
     */
    @NotNull
    private final Map<@NotNull Entry, @NotNull Collection<@NotNull Entry>> mappingByType = new HashMap<>();

    /**
     * Creates a new type registry populated with the default entries.
     *
     * @since 0.1.0
     */
    public TypeRegistry() {
        Entry objectEntry = new Entry(SkriptPattern.parse("object[s]"));
        Entry entityEntry = new Entry(SkriptPattern.parse("entit(y|ies)"), objectEntry);
        Entry livingEntityEntry = new Entry(SkriptPattern.parse("living[ ]entit(y|ies)"), entityEntry);
        Entry offlinePlayerEntry = new Entry(SkriptPattern.parse("offline[ ]player[s]"), objectEntry);

        addEntry(new Entry(SkriptPattern.parse("biome[s]"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("block[s]"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("block[ ]data[s]"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("boolean[s]"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("cat[ ](type|race)[s]"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("click[ ]type[s]"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("colo[u]r[s]"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("[command[s][ ]](sender|executor)[s]"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("damage[ ]cause[s]"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("date[s]"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("direction[s]"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("enchantment type[s]"), objectEntry));
        addEntry(entityEntry);
        addEntry(new Entry(SkriptPattern.parse("entity[ ]type[s]"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("experience[ ][point[s]]"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("firework[ ]type[s]"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("game[ ]mode[s]"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("[panda] gene[s]"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("inventor(y|ies)"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("inventory[ ]action[s]"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("inventory[ ]type[s]"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("(item[ ]type[s]|items|materials)"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("item stack[s]"), objectEntry));
        addEntry(livingEntityEntry);
        addEntry(new Entry(SkriptPattern.parse("location[s]"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("num[ber][s]"), objectEntry));
        addEntry(objectEntry);
        addEntry(offlinePlayerEntry);
        addEntry(new Entry(SkriptPattern.parse("player[s]"), offlinePlayerEntry, livingEntityEntry));
        addEntry(new Entry(SkriptPattern.parse("resource[ ]pack[ ]state[s]"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("script[s]"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("slot[s]"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("sound[ ]categor(y|ies)"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("spawn[ing][ ]reason[s]"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("potion[[ ]effect][ ]type[s]"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("(text|string)[s]"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("teleport[ ](cause|reason|type)[s]"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("time[s]"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("(time[ ]period|duration)[s]"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("time[ ]span[s]"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("(tree[ ]type|tree)[s]"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("type[s]"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("vector[s]"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("visual effect"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("weather([ ]type[s]|[ condition[s]])"), objectEntry));
        addEntry(new Entry(SkriptPattern.parse("world[s]"), objectEntry));
    }

    /**
     * Adds the specified entry to the registry
     *
     * @param entry the entry to add
     * @since 0.1.0
     */
    public void addEntry(@NotNull Entry entry) {
        for (String name : entry.names) {
            mappingByName.put(name, entry);
        }

        Queue<Entry> types = new ArrayDeque<>();
        types.add(entry);

        do {
            Entry type = types.poll();

            this.mappingByType.computeIfAbsent(type, key -> new HashSet<>()).add(entry);

            types.addAll(Arrays.asList(type.getParents()));
        } while (!types.isEmpty());
    }

    @Nullable
    @Contract(pure = true)
    @Override
    public Entry byName(@NotNull String name) {
        return mappingByName.get(name);
    }

    /**
     * Gets all entries that could belong to the given type. E.g., for an object, this will also return an entry for
     * text, since text is also an object (assuming this type is registered). If the returned {@link Collection} is
     * empty, there are no matching entries.
     *
     * @param entry the type to get all entries by
     * @return all entries
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public Collection<? extends Entry> byEntry(@NotNull Entry entry) {
        return this.mappingByType.getOrDefault(entry, Collections.emptySet());
    }

    /**
     * An entry for the type registry
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
         * The parent type, if it exists.
         */
        @NotNull
        private final Entry[] parents;

        /**
         * Creates a new entry with the pattern for the possible names and the class representing which literal this
         * entry represents.
         *
         * @param pattern the pattern for the names
         * @param parents the parent types
         * @since 0.1.0
         */
        public Entry(@NotNull SkriptPattern pattern, @NotNull Entry... parents) {
            this.names = pattern.unrollFully();
            this.parents = parents;
        }

        /**
         * Gets the parent type, or null if this type does not have a parent.
         *
         * @return the parent
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        public Entry[] getParents() {
            return this.parents;
        }
    }
}
