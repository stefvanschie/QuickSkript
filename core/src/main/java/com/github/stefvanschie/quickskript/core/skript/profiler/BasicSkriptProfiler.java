package com.github.stefvanschie.quickskript.core.skript.profiler;

import com.github.stefvanschie.quickskript.core.context.Context;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * A simple profiler: only stores the call counts and the total elapsed times
 *
 * @since 0.1.0
 */
public class BasicSkriptProfiler extends SkriptProfiler<BasicSkriptProfiler.Entry> {

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
    public BasicSkriptProfiler.Entry getEntry(@NotNull Class<? extends Context> contextType, @NotNull Identifier identifier) {
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
     * The entry {@link BasicSkriptProfiler} uses.
     */
    public static class Entry {

        /**
         * The number of times the elapsed time was recorded
         */
        private int count;

        /**
         * The sum of the elapsed times, in nanoseconds
         */
        private long totalTime;

        /**
         * Stores one more execution of the entry point associated with this entry.
         *
         * @param elapsedTime the time in nanoseconds which elapsed during execution this time
         */
        void add(long elapsedTime) {
            count++;
            totalTime += elapsedTime;
        }

        /**
         * Gets the number of times the elapsed time was recorded.
         *
         * @return the number of times the elapsed time was recorded
         * @since 0.1.0
         */
        public int getCalledCount() {
            return count;
        }

        /**
         * Gets the sum of all recorded elapsed times in nanoseconds.
         *
         * @return the elapsed time sum in nanoseconds
         * @since 0.1.0
         */
        public long getTotalElapsedTime() {
            return totalTime;
        }
    }
}
