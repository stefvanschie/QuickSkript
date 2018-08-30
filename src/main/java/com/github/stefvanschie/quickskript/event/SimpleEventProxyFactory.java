package com.github.stefvanschie.quickskript.event;

import com.github.stefvanschie.quickskript.QuickSkript;
import com.github.stefvanschie.quickskript.skript.SkriptEvent;
import javafx.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.EventExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.regex.Pattern;

/**
 * A factory which is capable of registering simple event handlers.
 * A handler is considered simple if it should always gets called when the Bukkit event fires.
 *
 * @since 0.1.0
 */
public class SimpleEventProxyFactory extends EventProxyFactory {

    /**
     * The storage of registered event handlers.
     */
    @NotNull
    private static final Map<Class<? extends Event>, Set<SkriptEvent>> REGISTERED_HANDLERS = new HashMap<>();

    /**
     * The executor which handles the execution of all event handlers in the storage.
     */
    @NotNull
    private static final EventExecutor HANDLER_EXECUTOR = (listener, event) ->
            REGISTERED_HANDLERS.get(event.getClass())
                    .forEach(handler -> handler.execute(event));

    /**
     * The storage of the registered event patterns.
     */
    @NotNull
    private final Set<Pair<Class<? extends Event>, Pattern>> eventPatterns = new HashSet<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean tryRegister(@NotNull String text, @NotNull Supplier<SkriptEvent> toRegisterSupplier) {
        for (Pair<Class<? extends Event>, Pattern> eventPattern : eventPatterns) {
            if (!eventPattern.getValue().matcher(text).matches())
                continue;

            REGISTERED_HANDLERS.computeIfAbsent(eventPattern.getKey(), event -> {
                Bukkit.getPluginManager().registerEvent(event, EMPTY_LISTENER,
                        EventPriority.NORMAL, HANDLER_EXECUTOR, QuickSkript.getPlugin(QuickSkript.class));
                return new HashSet<>();
            }).add(toRegisterSupplier.get());
            return true;
        }

        return false;
    }

    /**
     * Maps the specified regex to the specified Bukkit event.
     *
     * @param event the event to register
     * @param regex the pattern of the event in Skript source
     * @return itself for chaining
     *
     * @since 0.1.0
     */
    @NotNull
    public SimpleEventProxyFactory registerEvent(@NotNull Class<? extends Event> event, @NotNull String regex) {
        eventPatterns.add(new Pair<>(event, Pattern.compile(regex)));
        return this;
    }
}
