package com.github.stefvanschie.quickskript.bukkit.context;

import com.github.stefvanschie.quickskript.core.context.EventContext;
import com.github.stefvanschie.quickskript.core.skript.Skript;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * An event context to indicate the code is being ran from an event.
 *
 * @since 0.1.0
 */
public class EventContextImpl implements ContextImpl, EventContext {

    /**
     * The skript that is executing this code
     */
    @NotNull
    private final Skript skript;

    /**
     * The event that was being executed
     */
    @NotNull
    private final Event event;

    /**
     * Constructs a new event context
     *
     * @param skript the skript file this context belongs to
     * @param event the event that was executed
     * @since 0.1.0
     */
    public EventContextImpl(@NotNull Skript skript, @NotNull Event event) {
        this.skript = skript;
        this.event = event;
    }

    /**
     * Gets the {@link #event}
     *
     * @return the event
     * @since 0.1.0
     */
    @NotNull
    public Event getEvent() {
        return event;
    }

    @Nullable
    @Contract(pure = true)
    @Override
    public CommandSender getCommandSender() {
        if (getEvent() instanceof PlayerEvent) {
            return ((PlayerEvent) getEvent()).getPlayer();
        }

        if (getEvent() instanceof EntityEvent) {
            return ((EntityEvent) getEvent()).getEntity();
        }

        if (getEvent() instanceof BlockDamageEvent) {
            return ((BlockDamageEvent) getEvent()).getPlayer();
        }

        if (getEvent() instanceof BlockCanBuildEvent) {
            return ((BlockCanBuildEvent) getEvent()).getPlayer();
        }

        //TODO there are still missing events, eg. VehicleEnterEvent
        //TODO is the right value returned? eg. AreaEffectCloudApplyEvent returns the cloud entity, not the affected entities
        //TODO reflection-based approach maybe? and eg. prioritize player instances over other entities
        return null;
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public Skript getSkript() {
        return skript;
    }
}
