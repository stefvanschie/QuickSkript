package com.github.stefvanschie.quickskript.bukkit.event;

import com.github.stefvanschie.quickskript.bukkit.plugin.QuickSkript;
import com.github.stefvanschie.quickskript.bukkit.skript.SkriptEventExecutor;
import com.github.stefvanschie.quickskript.bukkit.util.Platform;
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
        List<Map.Entry<SkriptEventExecutor, BiPredicate<Skript, Event>>> handlers = REGISTERED_HANDLERS.get(event.getClass());

        //yes this can be null, thank Bukkit for that
        if (handlers != null) {
            handlers.stream()
                .filter(handler -> handler.getValue().test(handler.getKey().getScript(), event))
                .forEach(handler -> handler.getKey().execute(event));
        }
    };

    /**
     * The storage of the registered event patterns.
     */
    @NotNull
    private final List<EventPattern<?>> eventPatterns = new ArrayList<>();

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

        for (EventPattern<?> eventPattern : eventPatterns) {
            if (eventPattern.getEvent() == null) {
                QuickSkript.getInstance().getLogger().warning(
                    "The event '" + text + "' is not available on your platform."
                );
                continue;
            }

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

            BiPredicate<Skript, ? extends Event> predicate;

            if (eventPattern.getFilterCreator() == null) {
                predicate = (script, event) -> true;
            } else {
                predicate = eventPattern.getFilterCreator().apply(possibleMatches);
            }

            //no predicate, so no good match found
            if (predicate == null) {
                continue;
            }

            REGISTERED_HANDLERS.computeIfAbsent(eventPattern.getEvent(), event -> {
                Bukkit.getPluginManager().registerEvent(event, EMPTY_LISTENER,
                    EventPriority.NORMAL, HANDLER_EXECUTOR, QuickSkript.getInstance());
                return new ArrayList<>();
            }).add(Map.entry(toRegisterSupplier.get(), (BiPredicate<Skript, Event>) predicate));

            found = true;
        }

        return found;
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
        Function<Iterable<SkriptMatchResult>, BiPredicate<Skript, T>> filter = matches -> (script, e) -> filterCreator.apply(matches).test(e);

        eventPatterns.add(new EventPattern<>(event, pattern, filter));
        return this;
    }

    @NotNull
    public <T extends Event> ComplexEventProxyFactory registerEvent(@NotNull Class<T> event, @NotNull String pattern,
        @NotNull BiPredicate<Skript, T> filterCreator) {
        Function<Iterable<SkriptMatchResult>, BiPredicate<Skript, T>> filter = (matches -> filterCreator);

        eventPatterns.add(new EventPattern<>(event, pattern, filter));
        return this;
    }

    /**
     * Maps the specified regex to the specified Bukkit event.
     *
     * @param eventName the event to register's name. This is the full class name including the package
     * @param pattern the pattern of the event in Skript source
     * @param filterCreator the {@link Function} which creates a {@link Predicate}
     * based on the {@link Matcher} created by the specified regex and the Skript source
     * @param platform the platform required for this event
     * @param <T> the event that was registered
     * @return itself for chaining
     * @since 0.1.0
     */
    @NotNull
    public <T extends Event> ComplexEventProxyFactory registerEvent(@NotNull String eventName, @NotNull String pattern,
        @NotNull Function<Iterable<SkriptMatchResult>, Predicate<T>> filterCreator,
        @NotNull Platform platform) {
        if (!Platform.getPlatform().isAvailable(platform)) {
            eventPatterns.add(new EventPattern<>(null, pattern, null));
            return this;
        }

        Function<Iterable<SkriptMatchResult>, BiPredicate<Skript, T>> transformed =
            matches -> ((script, event) -> filterCreator.apply(matches).test(event));

        try {
            eventPatterns.add(new EventPattern<>(Class.forName(eventName).asSubclass(Event.class), pattern, transformed));
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return this;
    }

    /**
     * A container class for registered event patterns.
     *
     * @since 0.1.0
     */
    private static class EventPattern<T extends Event> {

        /**
         * The event to listen for. This is null if the event is not available on the current platform.
         * This is needed so events which exist in the Skript but are not supported on the current platform
         * can be detected and therefore a warning can be displayed.
         */
        @Nullable
        private final Class<T> event;

        /**
         * The skript pattern for matching this event pattern
         */
        @NotNull
        private final SkriptPattern pattern;

        /**
         * Whether the listener should be executed when the specified event is fired.
         * This is null when {@link #event} is null.
         */
        @Nullable
        private final Function<Iterable<SkriptMatchResult>, BiPredicate<Skript, T>> filterCreator;

        EventPattern(@Nullable Class<T> event, @NotNull String pattern,
                @Nullable Function<Iterable<SkriptMatchResult>, BiPredicate<Skript, T>> filterCreator) {
            this.event = event;
            this.pattern = SkriptPattern.parse(pattern);
            this.filterCreator = filterCreator;
        }


        @Nullable
        Class<? extends Event> getEvent() {
            return event;
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

        @Nullable
        Function<Iterable<SkriptMatchResult>, BiPredicate<Skript, T>> getFilterCreator() {
            return filterCreator;
        }
    }
}
