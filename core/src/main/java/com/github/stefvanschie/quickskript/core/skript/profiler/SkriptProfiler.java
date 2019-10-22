package com.github.stefvanschie.quickskript.core.skript.profiler;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.skript.Skript;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Objects;

/**
 * A profiler capable of measuring the execution times of each Skript entry-point.
 *
 * @param <T> the type of entries this profiler holds
 * @since 0.1.0
 */
public abstract class SkriptProfiler<T> {

    /**
     * A {@link SkriptProfiler} to be used by all skripts
     */
    @NotNull
    private static SkriptProfiler<?> active = new NoOpSkriptProfiler();

    /**
     * Gets the current profiler instance.
     *
     * @return the profiler instance that is to be used
     */
    @NotNull
    @Contract(pure = true)
    public static SkriptProfiler<?> getActive() {
        return active;
    }

    /**
     * Sets the current profiler instance.
     *
     * @param active the new profiler instance
     */
    public static void setActive(@NotNull SkriptProfiler<?> active) {
        SkriptProfiler.active = active;
    }

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
    public abstract T getEntry(@NotNull Class<? extends Context> contextType, @NotNull Identifier identifier);

    /**
     * Gets all entry identifiers which have entries associated with them.
     *
     * @param contextType the type of the entry identifiers we want to list
     * @return the entry identifiers
     * @since 0.1.0
     */
    @NotNull
    public abstract Collection<Identifier> getEntryIdentifiers(@NotNull Class<? extends Context> contextType);

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


        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof Identifier)) {
                return false;
            }

            Identifier id = (Identifier) other;
            return id.skript == skript && id.lineNumber == lineNumber;
        }

        @Override
        public int hashCode() {
            return Objects.hash(skript, lineNumber);
        }

        @Override
        public String toString() {
            return skript.getName() + ":" + lineNumber;
        }
    }
}
