package com.github.stefvanschie.quickskript.core.context;

import com.github.stefvanschie.quickskript.core.file.FileSkript;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * An event context to indicate the code is being ran from an event.
 *
 * @since 0.1.0
 */
public interface EventContext extends Context {

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Contract(pure = true)
    @Override
    FileSkript getSkript();
}
