package com.github.stefvanschie.quickskript.core.skript.profiler;

import com.github.stefvanschie.quickskript.core.context.Context;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * A complex profiler: stores each individual call and its elapsed time
 *
 * @since 0.1.0
 */
public class WholeSkriptProfiler extends SkriptProfiler<WholeSkriptProfiler.Entry> {

    /**
     * The storage of the profiler entries
     */
    private final Map<Class<? extends Context>, Map<Identifier, Entry>> storage = new IdentityHashMap<>();

    @Override
    public void onTimeMeasured(@NotNull Class<? extends Context> contextType, @NotNull Identifier identifier, long elapsedTime) {
        storage.computeIfAbsent(contextType, type -> new HashMap<>())
                .computeIfAbsent(identifier, id -> new Entry())
                .add(elapsedTime);
    }

    @Nullable
    @Override
    public Entry getEntry(@NotNull Class<? extends Context> contextType, @NotNull Identifier identifier) {
        Map<Identifier, Entry> entries = storage.get(contextType);
        return entries == null ? null : entries.get(identifier);
    }

    @NotNull
    @Override
    public Collection<Identifier> getEntryIdentifiers(@NotNull Class<? extends Context> contextType) {
        Map<Identifier, Entry> entries = storage.get(contextType);
        return entries == null ? Collections.emptySet() : entries.keySet();
    }

    /**
     * The entry {@link WholeSkriptProfiler} uses.
     */
    public static class Entry {

        /**
         * The elapsed times.
         */
        private final List<Long> times = new ArrayList<>();

        /**
         * Stores one more execution of the entry point associated with this entry.
         *
         * @param elapsedTime the time in nanoseconds which elapsed during execution this time
         */
        void add(long elapsedTime) {
            times.add(elapsedTime);
        }

        /**
         * Gets the individual elapsed times in nanoseconds.
         *
         * @return the individual elapsed times in nanoseconds
         * @since 0.1.0
         */
        public List<Long> getTimes() {
            return Collections.unmodifiableList(times);
        }
    }
}
