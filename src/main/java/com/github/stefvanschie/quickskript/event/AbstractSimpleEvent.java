package com.github.stefvanschie.quickskript.event;

import javafx.util.Pair;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An event parser for parsing simple events.
 * A simple event is an event which gets fired whenever the Bukkit event is fired.
 * This means that simple events are proxies between Bukkit and Skript events.
 *
 * @since 0.1.0
 */
public abstract class AbstractSimpleEvent extends AbstractEvent {

    /**
     * A factory for creating abstract simple events.
     *
     * @since 0.1.0
     */
    public abstract static class Factory implements AbstractEventFactory<AbstractSimpleEvent> {

        /**
         * A set containing the pattern and the class of all registered simple events.
         */
        private final Set<Pair<Pattern, Class<? extends Event>>> eventPatterns = new HashSet<>();

        protected Factory() {
            registerEvents();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public AbstractSimpleEvent tryParse(@NotNull String text) {
            for (Pair<Pattern, Class<? extends Event>> pair : eventPatterns) {
                Matcher matcher = pair.getKey().matcher(text);

                if (!matcher.matches())
                    continue;

                return new AbstractSimpleEvent() {
                    @Override
                    protected Class<? extends Event> getEventClass() {
                        return pair.getValue();
                    }
                };
            }

            return null;
        }

        /**
         * A method which is called once during construction,
         * designed to force all implementations of this class to register events.
         *
         * @see #registerEvent(String, Class)
         * @since 0.1.0
         */
        protected abstract void registerEvents();

        /**
         * Maps a regex pattern to an event.
         *
         * @param regex the pattern of the event
         * @param event the event to register
         * @since 0.1.0
         */
        protected void registerEvent(String regex, Class<? extends Event> event) {
            eventPatterns.add(new Pair<>(Pattern.compile(regex), event));
        }
    }
}
