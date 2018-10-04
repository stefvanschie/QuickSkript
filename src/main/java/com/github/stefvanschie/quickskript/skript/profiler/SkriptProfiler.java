package com.github.stefvanschie.quickskript.skript.profiler;

import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.skript.Skript;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Objects;

/**
 * A profiler capable of measuring the execution times of each Skript entry-point.
 *
 * @since 0.1.0
 */
public abstract class SkriptProfiler {

    /**
     * Called whenever the code inside an entry point was (successfully) executed.
     *
     * @param contextType the type of context of the entry point
     * @param identifier the identifier of the entry point
     * @param elapsedTime the time in nanoseconds it took to execute the code in the entry point
     * @since 0.1.0
     */
    public abstract void onTimeMeasured(@NotNull Class<? extends Context> contextType, @NotNull Identifier identifier, long elapsedTime);

    /**
     * Gets the entry associated with the specified entry point.
     *
     * @param contextType the type of the entry we want to retrieve
     * @param identifier the identifier of the entry point
     * @return the entry associated with the parameters, if any
     * @since 0.1.0
     */
    @Nullable
    public abstract TimingEntry getTimingEntry(@NotNull Class<? extends Context> contextType, @NotNull Identifier identifier);

    /**
     * Gets all entry identifiers which have entries associated with them.
     *
     * @param contextType the type of the entry identifiers we want to list
     * @return the entry identifiers
     * @since 0.1.0
     */
    @NotNull
    public abstract Collection<Identifier> getTimingEntryIdentifiers(@NotNull Class<? extends Context> contextType);


    /**
     * A profiler empty which stores the timings associated with an entry point.
     *
     * @since 0.1.0
     */
    public interface TimingEntry {

        /**
         * Gets the number of times the elapsed time was recorded.
         *
         * @return the number of times the elapsed time was recorded
         * @since 0.1.0
         */
        int getCalledCount();

        /**
         * Gets the sum of all recorded elapsed times in nanoseconds.
         *
         * @return the elapsed time sum in nanoseconds
         * @since 0.1.0
         */
        long getTotalElapsedTime();
    }

    /**
     * An identifier which is given to each Skript code entry point.
     * Two identifiers are viewed as equal if they both point to the
     * same line number of the same {@link Skript} instance.
     *
     * @since 0.1.0
     */
    public static class Identifier {

        /**
         * The container of the entry point.
         */
        @NotNull
        private final Skript skript;

        /**
         * The location of the entry point.
         */
        private final int lineNumber;

        /**
         * Creates a new instance for the specified entry point.
         *
         * @param skript the container of the entry point
         * @param lineNumber the location of the entry point
         * @since 0.1.0
         */
        public Identifier(@NotNull Skript skript, int lineNumber) {
            this.skript = skript;
            this.lineNumber = lineNumber;
        }


        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object other) {
            if (other == this)
                return true;

            if (!(other instanceof Identifier))
                return false;

            Identifier id = (Identifier) other;
            return id.skript == skript && id.lineNumber == lineNumber;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return Objects.hash(skript, lineNumber);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return skript.getName() + ":" + lineNumber;
        }
    }
}
