package com.github.stefvanschie.quickskript.core.skript;

import com.github.stefvanschie.quickskript.core.skript.profiler.NoOpSkriptProfiler;
import com.github.stefvanschie.quickskript.core.skript.profiler.SkriptProfiler;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * The runtime environment (context) in which Skripts can be executed.
 */
public class SkriptRunEnvironment {

    /**
     * The currently active profiler instance.
     */
    @NotNull
    private SkriptProfiler<?> profiler = new NoOpSkriptProfiler();

    /**
     * Creates and initializes a new instance.
     */
    public SkriptRunEnvironment() {}

    /**
     * Gets the currently active profiler instance.
     *
     * @return the current profiler instance
     */
    @NotNull
    @Contract(pure = true)
    public final SkriptProfiler<?> getProfiler() {
        return profiler;
    }

    /**
     * Sets the profiler to use from now on.
     * Data from the previous profiler is not transferred to the new one.
     *
     * @param profiler the new profiler
     * @return the previous profiler
     */
    @NotNull
    public SkriptProfiler<?> setProfiler(@NotNull SkriptProfiler<?> profiler) {
        SkriptProfiler<?> previous = this.profiler;
        this.profiler = profiler;
        return previous;
    }
}
