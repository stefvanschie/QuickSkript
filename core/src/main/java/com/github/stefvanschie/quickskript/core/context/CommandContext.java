package com.github.stefvanschie.quickskript.core.context;

import com.github.stefvanschie.quickskript.core.file.FileSkript;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A command context to indicate this code is being ran from a command and to provide information about said command.
 *
 * @since 0.1.0
 */
public interface CommandContext extends Context {

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Contract(pure = true)
    @Override
    FileSkript getSkript();
}
