package com.github.stefvanschie.quickskript.bukkit.event;

import com.github.stefvanschie.quickskript.bukkit.skript.SkriptEventExecutor;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * An abstract factory for registering event handlers.
 * Every handler type needs at least one factory, but may have more.
 *
 * @since 0.1.0
 */
public abstract class EventProxyFactory {

    @NotNull
    protected static final Listener EMPTY_LISTENER = new Listener() {};

    /**
     * Tries to parse the given text for this specified factory.
     *
     * @param text the text to be parsed
     * @param toRegisterSupplier a {@link Supplier} which can create one {@link SkriptEventExecutor} instance to register
     * @return whether a registration took place
     * @since 0.1.0
     */
    public abstract boolean tryRegister(@NotNull String text, @NotNull Supplier<SkriptEventExecutor> toRegisterSupplier);
}
