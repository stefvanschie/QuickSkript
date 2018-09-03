package com.github.stefvanschie.quickskript.event;

import com.github.stefvanschie.quickskript.QuickSkript;
import com.github.stefvanschie.quickskript.skript.SkriptEvent;
import javafx.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.EventExecutor;
import org.jetbrains.annotations.NotNull;

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
    private static final Map<Class<? extends Event>, List<Pair<SkriptEvent, Predicate<Event>>>> REGISTERED_HANDLERS = new HashMap<>();

    /**
     * The executor which handles the execution of all event handlers in the storage.
     */
    @NotNull
    private static final EventExecutor HANDLER_EXECUTOR = (listener, event) ->
            REGISTERED_HANDLERS.get(event.getClass()).stream()
                    .filter(handler -> handler.getValue().test(event))
                    .forEach(handler -> handler.getKey().execute(event));

    /**
     * The storage of the registered event patterns.
     */
    @NotNull
    private final List<EventPattern> eventPatterns = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean tryRegister(@NotNull String text, @NotNull Supplier<SkriptEvent> toRegisterSupplier) {
        for (EventPattern eventPattern : eventPatterns) {
            Matcher matcher = eventPattern.getMatcher(text);

            if (!matcher.matches())
                continue;

            REGISTERED_HANDLERS.computeIfAbsent(eventPattern.getEvent(), event -> {
                Bukkit.getPluginManager().registerEvent(event, EMPTY_LISTENER,
                        EventPriority.NORMAL, HANDLER_EXECUTOR, QuickSkript.getInstance());
                return new ArrayList<>();
            }).add(new Pair<>(toRegisterSupplier.get(), eventPattern.getFilterCreator().apply(matcher)));
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
     * A container class for registered event patterns.
     *
     * @since 0.1.0
     */
    private static class EventPattern {
        @NotNull
        private final Class<? extends Event> event;
        @NotNull
        private final Pattern pattern;
        @NotNull
        private final Function<Matcher, Predicate<Event>> filterCreator;

        EventPattern(@NotNull Class<? extends Event> event, @NotNull String regex,
                     @NotNull Function<Matcher, Predicate<Event>> filterCreator) {
            this.event = event;
            pattern = Pattern.compile(regex);
            this.filterCreator = filterCreator;
        }


        @NotNull
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
