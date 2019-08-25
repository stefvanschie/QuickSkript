package com.github.stefvanschie.quickskript.bukkit.util.event;

import org.bukkit.Bukkit;
import org.bukkit.event.*;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.jetbrains.annotations.NotNull;

public class ExperienceOrbSpawnEvent extends Event implements Cancellable {

    /**
     * Whether this event has been cancelled or not
     */
    private boolean cancelled;

    /**
     * {@inheritDoc}
     *
     * @since 0.1.0
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * {@inheritDoc}
     *
     * @since 0.1.0
     */
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Sets the amount of experience that should be dropped
     *
     * @param xp the amount of experience
     * @since 0.1.0
     */
    public void setXp(int xp) {
        this.xp = xp;
    }

    /**
     * A HandlerList to please Bukkit
     */
    private static final HandlerList HANDLER_LIST = new HandlerList();

    /**
     * {@inheritDoc}
     *
     * @since 0.1.0
     */
    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    /**
     * A static method to please Bukkit
     *
     * @return {@link #HANDLER_LIST}
     */
    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    /**
     * A way to listen for the necessary events to call {@link ExperienceOrbSpawnEvent}
     *
     * @since 0.1.0
     */
    public static class Listener implements org.bukkit.event.Listener {

        @EventHandler
        public void onBlockExp(BlockExpEvent event) {
            ExperienceOrbSpawnEvent experienceOrbSpawnEvent = new ExperienceOrbSpawnEvent();

            Bukkit.getPluginManager().callEvent(experienceOrbSpawnEvent);

            event.setExpToDrop(experienceOrbSpawnEvent.getXp());

            if (experienceOrbSpawnEvent.isCancelled()) {
                event.setExpToDrop(0);
            }
        }

        @EventHandler
        public void onEntityDeath(EntityDeathEvent event) {
            ExperienceOrbSpawnEvent experienceOrbSpawnEvent = new ExperienceOrbSpawnEvent();

            Bukkit.getPluginManager().callEvent(experienceOrbSpawnEvent);

            event.setDroppedExp(experienceOrbSpawnEvent.getXp());

            if (experienceOrbSpawnEvent.isCancelled()) {
                event.setDroppedExp(0);
            }
        }

        @EventHandler
        public void onExpBottle(ExpBottleEvent event) {
            ExperienceOrbSpawnEvent experienceOrbSpawnEvent = new ExperienceOrbSpawnEvent();

            Bukkit.getPluginManager().callEvent(experienceOrbSpawnEvent);

            event.setExperience(experienceOrbSpawnEvent.getXp());

            if (experienceOrbSpawnEvent.isCancelled()) {
                event.setExperience(0);
            }
        }

        @EventHandler
        public void onPlayerFish(PlayerFishEvent event) {
            ExperienceOrbSpawnEvent experienceOrbSpawnEvent = new ExperienceOrbSpawnEvent();

            Bukkit.getPluginManager().callEvent(experienceOrbSpawnEvent);

            event.setExpToDrop(experienceOrbSpawnEvent.getXp());

            if (experienceOrbSpawnEvent.isCancelled()) {
                event.setExpToDrop(0);
            }
        }
    }
}
