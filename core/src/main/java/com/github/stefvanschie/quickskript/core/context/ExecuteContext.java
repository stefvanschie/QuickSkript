package com.github.stefvanschie.quickskript.core.context;

import com.github.stefvanschie.quickskript.core.skript.SingleLineSkript;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface ExecuteContext extends Context {

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Contract(pure = true)
    @Override
    SingleLineSkript getSkript();
}
