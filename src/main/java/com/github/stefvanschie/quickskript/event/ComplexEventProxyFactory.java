package com.github.stefvanschie.quickskript.event;

import com.github.stefvanschie.quickskript.QuickSkript;
import com.github.stefvanschie.quickskript.skript.SkriptEventExecutor;
import com.github.stefvanschie.quickskript.util.Platform;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.EventExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A factory which is capable of registering complex event handlers.
 * A handler is considered complex if it shouldn't always gets called when the Bukkit event fires.
 *
 * @since 0.1.0
 */
public class ComplexEventProxyFactory extends EventProxyFactory {

    /**
     * The storage of registered event handlers.
     */
    @NotNull
    private static final Map<Class<? extends Event>, List<Map.Entry<SkriptEventExecutor, Predicate<Event>>>> REGISTERED_HANDLERS = new HashMap<>();

    /**
     * The executor which handles the execution of all event handlers in the storage.
     */
    @NotNull
    private static final EventExecutor HANDLER_EXECUTOR = (listener, event) -> {
        List<Map.Entry<SkriptEventExecutor, Predicate<Event>>> handlers = REGISTERED_HANDLERS.get(event.getClass());

        //yes this can be null, thank Bukkit for that
        if (handlers != null) {
            handlers.stream()
                .filter(handler -> handler.getValue().test(event))
                .forEach(handler -> handler.getKey().execute(event));
        }
    };

    /**
     * The storage of the registered event patterns.
     */
    @NotNull
    private final List<EventPattern> eventPatterns = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean tryRegister(@NotNull String text, @NotNull Supplier<SkriptEventExecutor> toRegisterSupplier) {
        for (EventPattern eventPattern : eventPatterns) {
            Matcher matcher = eventPattern.getMatcher(text);

            if (!matcher.matches())
                continue;

            if (eventPattern.getEvent() == null) {
                QuickSkript.getInstance().getLogger().warning(
                    "The event '" + matcher.group() + "' is not available on your platform."
                );
                continue;
            }

            REGISTERED_HANDLERS.computeIfAbsent(eventPattern.getEvent(), event -> {
                Bukkit.getPluginManager().registerEvent(event, EMPTY_LISTENER,
                        EventPriority.NORMAL, HANDLER_EXECUTOR, QuickSkript.getInstance());
                return new ArrayList<>();
            }).add(Map.entry(toRegisterSupplier.get(), eventPattern.getFilterCreator().apply(matcher)));
            return true;
        }

        return false;
    }

    /**
     * Maps the specified regex to the specified Bukkit event.
     *
     * @param event the event to register
     * @param regex the pattern of the event in Skript source
     * @param filterCreator the {@link Function} which creates a {@link Predicate}
     * based on the {@link Matcher} created by the specified regex and the Skript source
     * @return itself for chaining
     * @since 0.1.0
     */
    @NotNull
    public ComplexEventProxyFactory registerEvent(@NotNull Class<? extends Event> event, @NotNull String regex,
                                                  @NotNull Function<Matcher, Predicate<Event>> filterCreator) {
        eventPatterns.add(new EventPattern(event, regex, filterCreator));
        return this;
    }

    /**
     * Maps the specified regex to the specified Bukkit event.
     *
     * @param eventName the event to register's name. This is the full class name including the package
     * @param regex the pattern of the event in Skript source
     * @param filterCreator the {@link Function} which creates a {@link Predicate}
     * based on the {@link Matcher} created by the specified regex and the Skript source
     * @param platform the platform required for this event
     * @return itself for chaining
     * @since 0.1.0
     */
    @NotNull
    public ComplexEventProxyFactory registerEvent(@NotNull String eventName, @NotNull String regex,
                                                  @NotNull Function<Matcher, Predicate<Event>> filterCreator,
                                                  @NotNull Platform platform) {
        if (Platform.getPlatform().isAvailable(platform)) {
            try {
                eventPatterns.add(new EventPattern((Class<? extends Event>) Class.forName(eventName), regex,
                    filterCreator));
            } catch (ClassNotFoundException exception) {
                exception.printStackTrace();
            }
        } else {
            eventPatterns.add(new EventPattern(null, regex, filterCreator));
        }

        return this;
    }

    /**
     * A container class for registered event patterns.
     *
     * @since 0.1.0
     */
    private static class EventPattern {

        /**
         * The event to listen for. This is null if the event is not available on the current platform.
         */
        @Nullable
        private final Class<? extends Event> event;
        @NotNull
        private final Pattern pattern;
        @NotNull
        private final Function<Matcher, Predicate<Event>> filterCreator;

        EventPattern(@Nullable Class<? extends Event> event, @NotNull String regex,
                     @NotNull Function<Matcher, Predicate<Event>> filterCreator) {
            this.event = event;
            pattern = Pattern.compile(regex);
            this.filterCreator = filterCreator;
        }


        @Nullable
        Class<? extends Event> getEvent() {
            return event;
        }

        @NotNull
        Matcher getMatcher(String text) {
            return pattern.matcher(text);
        }

        @NotNull
        Function<Matcher, Predicate<Event>> getFilterCreator() {
            return filterCreator;
        }
    }
}
