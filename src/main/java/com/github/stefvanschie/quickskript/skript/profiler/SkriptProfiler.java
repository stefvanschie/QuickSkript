package com.github.stefvanschie.quickskript.skript.profiler;

import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.skript.Skript;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * A profiler capable of measuring the execution times of each Skript entry-point.
 *
 * @since 0.1.0
 */
public abstract class SkriptProfiler {

    /**
     * Creates an identifier for an entry point.
     *
     * @param skript the container of the entry point
     * @param lineNumber the line number of entry point
     * @return the identifier created for the entry point
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public static String getIdentifier(@NotNull Skript skript, int lineNumber) {
        return skript.getName() + "#" + lineNumber;
    }

    /**
     * Called whenever the code inside an entry point was (successfully) executed.
     *
     * @param context the context of the entry point
     * @param identifier the identifier of the entry point
     * @param elapsedTime the time it took to execute the code in the entry point
     * @since 0.1.0
     */
    public abstract void onTimeMeasured(@NotNull Context context, @NotNull String identifier, long elapsedTime);

    /**
     * Gets the entry associated with the specified entry point.
     *
     * @param contextType the type of the entry we want to retrieve
     * @param identifier the identifier of the entry point
     * @return the entry associated with the parameters, if any
     * @since 0.1.0
     */
    @Nullable
    public abstract TimingEntry getTimingEntry(@NotNull Class<? extends Context> contextType, @NotNull String identifier);

    /**
     * Gets all entry identifiers which have entries associated with them.
     *
     * @param contextType the type of the entry identifiers we want to list
     * @return the entry identifiers
     * @since 0.1.0
     */
    @NotNull
    public abstract Collection<String> getTimingEntryIdentifiers(@NotNull Class<? extends Context> contextType);


    /**
     * A profiler empty which stores the timings associated with an entry point.
     *
     * @since 0.1.0
     */
    public interface TimingEntry {

        /**
         * Gets the number of times the elapsed time was recorded.
         *
         * @return the number of times the elapsed time was recorded.
         * @since 0.1.0
         */
        int getCalledCount();

        /**
         * Gets the sum of all recorded elapsed times in nanoseconds.
         *
         * @return the elapsed time sum in nanoseconds.
         * @since 0.1.0
         */
        long getTotalElapsedTime();
    }
}
