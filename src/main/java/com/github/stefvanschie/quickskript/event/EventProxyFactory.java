package com.github.stefvanschie.quickskript.event;

import com.github.stefvanschie.quickskript.skript.SkriptEvent;
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

    protected static final Listener EMPTY_LISTENER = new Listener() {};

    protected EventProxyFactory() {
        registerEvents();
    }

    /**
     * Tries to parse the given text for this specified factory.
     *
     * @param text the text to be parsed
     * @param toRegisterSupplier a {@link Supplier} which can create one {@link SkriptEvent} instance to register
     * @return whether a registration took place
     * @since 0.1.0
     */
    public abstract boolean tryRegister(@NotNull String text, Supplier<SkriptEvent> toRegisterSupplier);

    /**
     * A method which is called once during construction,
     * designed to force all implementations of this class to register events.
     *
     * @since 0.1.0
     */
    protected abstract void registerEvents();
}
