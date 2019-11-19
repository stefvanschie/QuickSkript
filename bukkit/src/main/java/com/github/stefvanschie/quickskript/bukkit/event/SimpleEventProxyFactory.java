package com.github.stefvanschie.quickskript.bukkit.event;

import com.github.stefvanschie.quickskript.bukkit.plugin.QuickSkript;
import com.github.stefvanschie.quickskript.bukkit.skript.SkriptEventExecutor;
import com.github.stefvanschie.quickskript.bukkit.util.Platform;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.EventExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Supplier;
import java.util.regex.Matcher;
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
    private final Map<Class<? extends Event>, List<SkriptEventExecutor>> REGISTERED_HANDLERS = new HashMap<>();

    /**
     * The executor which handles the execution of all event handlers in the storage.
     */
    @NotNull
    private final EventExecutor HANDLER_EXECUTOR = (listener, event) -> {
        /*
        Paper has a custom implementation of the PaperServerListPingEvent which will be returned in here, so we need to
        accommodate for that.
         */
        if (event.getClass().getCanonicalName()
            .equals("com.destroystokyo.paper.network.StandardPaperServerListPingEventImpl")) {
            for (Map.Entry<Class<? extends Event>, List<SkriptEventExecutor>> entry : REGISTERED_HANDLERS.entrySet()) {
                if (!entry.getKey().getCanonicalName()
                    .equals("com.destroystokyo.paper.event.server.PaperServerListPingEvent")) {
                    continue;
                }

                entry.getValue().forEach(handler -> handler.execute(event));
                break;
            }
        }

        /*
        Bukkit on the other hand may register multiple events to the same handler, causing issues. This ensures that
        every type and parent of an event is properly called.
         */

        Class<?> clazz = event.getClass();

        do {
            List<SkriptEventExecutor> handlers = REGISTERED_HANDLERS.get(clazz);

            if (handlers != null) {
                handlers.forEach(handler -> handler.execute(event));
            }

            clazz = clazz.getSuperclass();
        } while (clazz != null);

        //Which other surprises will be found in the event system? Find out the next time this file gets changed.
    };

    /**
     * The storage of the registered event patterns.
     */
    @NotNull
    private final List<Map.Entry<Class<? extends Event>, Pattern>> eventPatterns = new ArrayList<>();

    @Override
    public boolean tryRegister(@NotNull String text, @NotNull Supplier<SkriptEventExecutor> toRegisterSupplier) {
        for (Map.Entry<Class<? extends Event>, Pattern> eventPattern : eventPatterns) {
            Matcher matcher = eventPattern.getValue().matcher(text);

            if (!matcher.matches()) {
                continue;
            }

            if (eventPattern.getKey() == null) {
                QuickSkript.getInstance().getLogger().warning(
                    "The event '" + matcher.group() + "' is not available on your platform."
                );
                continue;
            }

            REGISTERED_HANDLERS.computeIfAbsent(eventPattern.getKey(), event -> {
                Bukkit.getPluginManager().registerEvent(event, EMPTY_LISTENER,
                        EventPriority.NORMAL, HANDLER_EXECUTOR, QuickSkript.getInstance());
                return new ArrayList<>();
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
        eventPatterns.add(Map.entry(event, Pattern.compile(regex)));
        return this;
    }

    /**
     * Maps the specified regex to the specified Bukkit event.
     *
     * @param eventName the event to register's name
     * @param regex the pattern of the event in Skript source
     * @param platform the platform required for this event
     * @return itself for chaining
     *
     * @since 0.1.0
     */
    @NotNull
    public SimpleEventProxyFactory registerEvent(@NotNull String eventName, @NotNull String regex,
                                                 @NotNull Platform platform) {
        if (Platform.getPlatform().isAvailable(platform)) {
            try {
                eventPatterns.add(Map.entry((Class<? extends Event>) Class.forName(eventName), Pattern.compile(regex)));
            } catch (ClassNotFoundException exception) {
                exception.printStackTrace();
            }
        } else {
            eventPatterns.add(new AbstractMap.SimpleEntry<>(null, Pattern.compile(regex)));
        }

        return this;
    }
}
