package com.github.stefvanschie.quickskript.core.skript.profiler;

import com.github.stefvanschie.quickskript.core.context.Context;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * A simple profiler: it just implements the interface.
 * Should be good enough in most cases.
 *
 * @since 0.1.0
 */
public class SimpleSkriptProfiler extends SkriptProfiler {

    /**
     * The storage of the profiler entries
     */
    private final Map<Class<? extends Context>, Map<Identifier, SimpleEntry>> storage = new IdentityHashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void onTimeMeasured(@NotNull Class<? extends Context> contextType, @NotNull Identifier identifier, long elapsedTime) {
        storage.computeIfAbsent(contextType, type -> new HashMap<>())
                .computeIfAbsent(identifier, id -> new SimpleEntry())
                .add(elapsedTime);
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public TimingEntry getTimingEntry(@NotNull Class<? extends Context> contextType, @NotNull Identifier identifier) {
        Map<Identifier, SimpleEntry> entries = storage.get(contextType);
        return entries == null ? null : entries.get(identifier);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public Collection<Identifier> getTimingEntryIdentifiers(@NotNull Class<? extends Context> contextType) {
        Map<Identifier, SimpleEntry> entries = storage.get(contextType);
        return entries == null ? Collections.emptySet() : entries.keySet();
    }


    /**
     * A simple implementation of a profiler entry.
     */
    private static class SimpleEntry implements TimingEntry {

        /**
         * The number of times the elapsed time was recorded
         */
        private int count;

        /**
         * The sum of the elapsed times
         */
        private long totalTime;

        /**
         * Stores one more execution of the entry point associated with this entry.
         *
         * @param elapsedTime the time which elapsed during execution this time
         */
        void add(long elapsedTime) {
            count++;
            totalTime += elapsedTime;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getCalledCount() {
            return count;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public long getTotalElapsedTime() {
            return totalTime;
        }
    }
}
