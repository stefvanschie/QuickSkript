package com.github.stefvanschie.quickskript.bukkit.util.event;

import org.bukkit.Bukkit;
import org.bukkit.event.*;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * An event called just before an experience orb is spawned
 *
 * @since 0.1.0
 */
public class ExperienceOrbSpawnEvent extends Event implements Cancellable {

    /**
     * The amount of experience that will be spawned
     */
    private int xp;

    /**
     * Whether this event has been cancelled or not
     */
    private boolean cancelled;

    /**
     * Creates a new instance
     *
     * @param xp the amount of experience that will be spawned
     * @since 0.1.0
     */
    private ExperienceOrbSpawnEvent(int xp) {
        this.xp = xp;
    }

    /**
     * Gets the amount of experience that will be spawned
     *
     * @return the amount of experience
     * @since 0.1.0
     */
    @Contract(pure = true)
    public int getXp() {
        return xp;
    }

    /**
     *
     *
     * @since 0.1.0
     */
    @Override
    @Contract(pure = true)
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     *
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
    @NotNull
    private static final HandlerList HANDLER_LIST = new HandlerList();

    /**
     *
     *
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    /**
     * A static method to please Bukkit
     *
     * @return {@link #HANDLER_LIST}
     */
    @NotNull
    @Contract(pure = true)
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
        public void onBlockExp(@NotNull BlockExpEvent event) {
            ExperienceOrbSpawnEvent experienceOrbSpawnEvent = new ExperienceOrbSpawnEvent(event.getExpToDrop());

            Bukkit.getPluginManager().callEvent(experienceOrbSpawnEvent);

            event.setExpToDrop(experienceOrbSpawnEvent.getXp());

            if (experienceOrbSpawnEvent.isCancelled()) {
                event.setExpToDrop(0);
            }
        }

        @EventHandler
        public void onEntityDeath(@NotNull EntityDeathEvent event) {
            ExperienceOrbSpawnEvent experienceOrbSpawnEvent = new ExperienceOrbSpawnEvent(event.getDroppedExp());

            Bukkit.getPluginManager().callEvent(experienceOrbSpawnEvent);

            event.setDroppedExp(experienceOrbSpawnEvent.getXp());

            if (experienceOrbSpawnEvent.isCancelled()) {
                event.setDroppedExp(0);
            }
        }

        @EventHandler
        public void onExpBottle(@NotNull ExpBottleEvent event) {
            ExperienceOrbSpawnEvent experienceOrbSpawnEvent = new ExperienceOrbSpawnEvent(event.getExperience());

            Bukkit.getPluginManager().callEvent(experienceOrbSpawnEvent);

            event.setExperience(experienceOrbSpawnEvent.getXp());

            if (experienceOrbSpawnEvent.isCancelled()) {
                event.setExperience(0);
            }
        }

        @EventHandler
        public void onPlayerFish(@NotNull PlayerFishEvent event) {
            ExperienceOrbSpawnEvent experienceOrbSpawnEvent = new ExperienceOrbSpawnEvent(event.getExpToDrop());

            Bukkit.getPluginManager().callEvent(experienceOrbSpawnEvent);

            event.setExpToDrop(experienceOrbSpawnEvent.getXp());

            if (experienceOrbSpawnEvent.isCancelled()) {
                event.setExpToDrop(0);
            }
        }
    }
}
