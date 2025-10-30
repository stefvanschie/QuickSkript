package com.github.stefvanschie.quickskript.spigot.event;

import com.github.stefvanschie.quickskript.spigot.plugin.QuickSkript;
import com.github.stefvanschie.quickskript.spigot.skript.SkriptEventExecutor;
import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.skript.Skript;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.EventExecutor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;

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
    private final Map<Class<? extends Event>, List<Map.Entry<SkriptEventExecutor,
        BiPredicate<Skript, Event>>>> REGISTERED_HANDLERS = new HashMap<>();

    /**
     * The executor which handles the execution of all event handlers in the storage.
     */
    @NotNull
    private final EventExecutor HANDLER_EXECUTOR = (listener, event) -> {
        Class<?> clazz = event.getClass();
        List<Map.Entry<SkriptEventExecutor, BiPredicate<Skript, Event>>> handlers = REGISTERED_HANDLERS.get(clazz);

        while (handlers == null) {
            clazz = clazz.getSuperclass();

            if (clazz == null) {
                return;
            }

            handlers = REGISTERED_HANDLERS.get(clazz);
        }

        handlers.stream()
            .filter(handler -> handler.getValue().test(handler.getKey().getScript(), event))
            .forEach(handler -> handler.getKey().execute(event));
    };

    /**
     * The storage of the registered event patterns.
     */
    @NotNull
    private final List<EventPattern> eventPatterns = new ArrayList<>();

    /**
     * The skript loader for which this event proxy factory is used.
     */
    @NotNull
    private final SkriptLoader skriptLoader;

    /**
     * Constructs a new complex event proxy factory with the specified skript loader
     *
     * @param skriptLoader the skript loader for which this event proxy factory registers events
     * @since 0.1.0
     */
    public ComplexEventProxyFactory(@NotNull SkriptLoader skriptLoader) {
        this.skriptLoader = skriptLoader;
    }

    @Override
    public boolean tryRegister(@NotNull String text, @NotNull Supplier<SkriptEventExecutor> toRegisterSupplier) {
        boolean found = false;

        for (EventPattern eventPattern : eventPatterns) {
            List<SkriptMatchResult> matches = eventPattern.getPattern().match(text);
            Set<SkriptMatchResult> possibleMatches = new HashSet<>();

            for (SkriptMatchResult match : matches) {
                if (match.hasUnmatchedParts()) {
                    continue;
                }

                possibleMatches.add(match);
            }

            if (possibleMatches.isEmpty()) {
                continue;
            }

            Collection<? extends EventFilter<?>> eventFilters = eventPattern.getEventFilters();

            if (eventFilters.isEmpty()) {
                QuickSkript.getInstance().getLogger().warning(
                    "The event '" + text + "' is not available on your platform."
                );
                continue;
            }

            for (EventFilter<?> eventFilter : eventFilters) {
                BiPredicate<Skript, ? extends Event> predicate = eventFilter.getFilterCreator().apply(possibleMatches);

                //no predicate, so no good match found
                if (predicate == null) {
                    continue;
                }

                REGISTERED_HANDLERS.computeIfAbsent(eventFilter.getEvent(), event -> {
                    Bukkit.getPluginManager().registerEvent(event, EMPTY_LISTENER,
                        EventPriority.NORMAL, HANDLER_EXECUTOR, QuickSkript.getInstance());
                    return new ArrayList<>();
                }).add(Map.entry(toRegisterSupplier.get(), (BiPredicate<Skript, Event>) predicate));

                found = true;
            }
        }

        return found;
    }

    /**
     * Maps the specified regex to the specified Bukkit event.
     *
     * @param eventOne the first event to register
     * @param eventTwo the second event to register
     * @param pattern the pattern of the event in Skript source
     * @param filterCreatorOne the {@link Function} which creates a {@link Predicate}
     * based on the {@link Matcher} created by the specified regex and the Skript source
     * @param filterCreatorTwo the {@link Function} which creates a {@link Predicate}
     * based on the {@link Matcher} created by the specified regex and the Skript source
     * @param <T> the event that was registered
     * @return itself for chaining
     * @since 0.1.0
     */
    @NotNull
    public <T extends Event, U extends Event> ComplexEventProxyFactory registerEvent(
        @NotNull Class<T> eventOne,
        @NotNull Class<U> eventTwo,
        @NotNull String pattern,
        @NotNull Function<Iterable<SkriptMatchResult>, Predicate<T>> filterCreatorOne,
        @NotNull Function<Iterable<SkriptMatchResult>, Predicate<U>> filterCreatorTwo
    ) {
        Function<Iterable<SkriptMatchResult>, BiPredicate<Skript, T>> filterOne = matches -> (script, e) -> filterCreatorOne.apply(matches).test(e);
        Function<Iterable<SkriptMatchResult>, BiPredicate<Skript, U>> filterTwo = matches -> (script, e) -> filterCreatorTwo.apply(matches).test(e);

        EventFilter<T> eventFilterOne = new EventFilter<>(eventOne, filterOne);
        EventFilter<U> eventFilterTwo = new EventFilter<>(eventTwo, filterTwo);

        eventPatterns.add(new EventPattern(pattern, Set.of(eventFilterOne, eventFilterTwo)));
        return this;
    }

    /**
     * Maps the specified regex to the specified Bukkit event.
     *
     * @param event the event to register
     * @param pattern the pattern of the event in Skript source
     * @param filterCreator the {@link Function} which creates a {@link Predicate}
     * based on the {@link Matcher} created by the specified regex and the Skript source
     * @param <T> the event that was registered
     * @return itself for chaining
     * @since 0.1.0
     */
    @NotNull
    public <T extends Event> ComplexEventProxyFactory registerEvent(@NotNull Class<T> event, @NotNull String pattern,
            @NotNull Function<Iterable<SkriptMatchResult>, Predicate<T>> filterCreator) {
        Function<Iterable<SkriptMatchResult>, BiPredicate<Skript, T>> filter = matches -> {
            Predicate<T> predicate = filterCreator.apply(matches);

            if (predicate == null) {
                return null;
            }

            return (script, e) -> predicate.test(e);
        };

        eventPatterns.add(new EventPattern(event, pattern, filter));
        return this;
    }

    @NotNull
    public <T extends Event> ComplexEventProxyFactory registerEvent(@NotNull Class<T> event, @NotNull String pattern,
        @NotNull BiPredicate<Skript, T> filterCreator) {
        Function<Iterable<SkriptMatchResult>, BiPredicate<Skript, T>> filter = (matches -> filterCreator);

        eventPatterns.add(new EventPattern(event, pattern, filter));
        return this;
    }

    /**
     * A container class for registered event patterns.
     *
     * @since 0.1.0
     */
    private static class EventPattern {

        /**
         * The events to listen for. This is null if the event is not available on the current platform.
         * This is needed so events which exist in the Skript but are not supported on the current platform
         * can be detected and therefore a warning can be displayed.
         */
        @NotNull
        private final Collection<EventFilter<?>> events;

        /**
         * The skript pattern for matching this event pattern
         */
        @NotNull
        private final SkriptPattern pattern;

        /**
         * Creates a new event pattern.
         *
         * @param event the event to listen for
         * @param pattern the pattern to match against this event pattern
         * @param filterCreator the filter to be applied during matching
         * @param <T> the type of the event
         * @since 0.1.0
         */
        private <T extends Event> EventPattern(@Nullable Class<T> event, @NotNull String pattern,
                @Nullable Function<Iterable<SkriptMatchResult>, BiPredicate<Skript, T>> filterCreator) {
            if (event == null || filterCreator == null) {
                this.events = Collections.emptySet();
            } else {
                this.events = Set.of(new EventFilter<>(event, filterCreator));
            }

            this.pattern = SkriptPattern.parse(pattern);
        }

        /**
         * Creates a new event pattern.
         *
         * @param pattern the pattern to match against this event pattern
         * @param eventFilters the event and their filters to be applied during matching
         * @since 0.1.0
         */
        private EventPattern(@NotNull String pattern, @NotNull Set<@NotNull EventFilter<?>> eventFilters) {
            this.events = eventFilters;
            this.pattern = SkriptPattern.parse(pattern);
        }

        /**
         * Gets the events that need to be listened to for this event pattern. The returned collection is immutable.
         *
         * @return the events
         * @since 0.1.0
         */
        @NotNull
        private Collection<? extends EventFilter<?>> getEventFilters() {
            return this.events;
        }

        /**
         * Gets the skript pattern of this event pattern.
         *
         * @return the skript pattern
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        private SkriptPattern getPattern() {
            return pattern;
        }
    }

    /**
     * Represents a filter for a specific class of event.
     *
     * @param <T> the type of the event
     * @since 0.1.0
     */
    private static class EventFilter<T extends Event> {

        /**
         * The event this event filter represents
         */
        @NotNull
        private final Class<T> event;

        /**
         * The filter that must match in order to register an event for this event filter.
         */
        @NotNull
        private final Function<Iterable<SkriptMatchResult>, BiPredicate<Skript, T>> filterCreator;

        /**
         * Creates a new event filter.
         *
         * @param event the event class
         * @param filterCreator the filter
         * @since 0.1.0
         */
        EventFilter(
            @NotNull Class<T> event,
            @NotNull Function<Iterable<SkriptMatchResult>, BiPredicate<Skript, T>> filterCreator
        ) {
            this.event = event;
            this.filterCreator = filterCreator;
        }

        /**
         * The class of event this event filter represents.
         *
         * @return the event class
         * @since 0.1.0
         */
        @NotNull
        private Class<T> getEvent() {
            return event;
        }

        /**
         * Gets the filter for the associated event. Only if this filter matches should the event be registered.
         *
         * @return the filter
         * @since 0.1.0
         */
        @NotNull
        private Function<Iterable<SkriptMatchResult>, BiPredicate<Skript, T>> getFilterCreator() {
            return filterCreator;
        }
    }
}
