package com.github.stefvanschie.quickskript.core.skript.profiler;

import com.github.stefvanschie.quickskript.core.context.Context;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;

/**
 * A profiler which does not store any data.
 *
 * @since 0.1.0
 */
public class EmptySkriptProfiler extends SkriptProfiler {

    /**
     * {@inheritDoc}
     */
    @Override
    public void onTimeMeasured(@NotNull Class<? extends Context> contextType, @NotNull Identifier identifier, long elapsedTime) {}

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public TimingEntry getTimingEntry(@NotNull Class<? extends Context> contextType, @NotNull Identifier identifier) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public Collection<Identifier> getTimingEntryIdentifiers(@NotNull Class<? extends Context> contextType) {
        return Collections.emptySet();
    }
}
