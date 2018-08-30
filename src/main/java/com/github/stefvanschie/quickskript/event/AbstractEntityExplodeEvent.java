package com.github.stefvanschie.quickskript.event;

import com.github.stefvanschie.quickskript.QuickSkript;
import com.github.stefvanschie.quickskript.skript.SkriptEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An event parser for parsing entity explode events.
 *
 * @since 0.1.0
 */
public class AbstractEntityExplodeEvent extends AbstractEvent {

    /**
     * The pattern for matching this class
     */
    private static final Pattern PATTERN = Pattern.compile("on explo(?:(?:d(?:e|ing))|(?:sion))");

    /**
     * {@inheritDoc}
     */
    @Override
    public void register(@NotNull SkriptEvent event) {
        this.event = event;

        Bukkit.getPluginManager().registerEvent(EntityExplodeEvent.class,
            new Listener() {}, EventPriority.NORMAL, this, QuickSkript.getPlugin(QuickSkript.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(Listener listener, Event event) {
        this.event.execute(event);
    }

    /**
     * A factory for creating abstract entity explode events.
     *
     * @since 0.1.0
     */
    public static class Factory implements AbstractEventFactory<AbstractEntityExplodeEvent> {

        /**
         * {@inheritDoc}
         */
        @Override
        public AbstractEntityExplodeEvent tryParse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            return new AbstractEntityExplodeEvent();
        }
    }
}
