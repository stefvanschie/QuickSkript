package com.github.stefvanschie.quickskript.bukkit.util.event;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * An event called when the time in a world changes. This is for the time of day, not the full time of the world.
 *
 * @since 0.1.0
 */
public class WorldTimeChangeEvent extends Event {

    /**
     * The world whose time was changed
     */
    @NotNull
    private final World world;

    /**
     * The new time of the world
     */
    private final long time;

    /**
     * A HandlerList to please Bukkit
     */
    @NotNull
    private static final HandlerList HANDLER_LIST = new HandlerList();

    /**
     * Creates a new world time change event
     *
     * @param world the world whose time changed
     * @param time the new time
     * @since 0.1.0
     */
    private WorldTimeChangeEvent(@NotNull World world, long time) {
        this.world = world;
        this.time = time;
    }

    /**
     * Gets the world whose time was changed
     *
     * @return the world
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public World getWorld() {
        return world;
    }

    /**
     * The new time of the world
     *
     * @return the time
     * @since 0.1.0
     */
    @Contract(pure = true)
    public long getTime() {
        return time;
    }

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
     * Listens to changes in worlds' time
     *
     * @since 0.1.0
     */
    public static class Listener {

        /**
         * The plugin for which this class will be listening to changes
         */
        @NotNull
        private final Plugin plugin;

        /**
         * The maximum amount with which the time may have changed within one tick
         */
        private static final int MAX_TICK_DIFFERENCE = 2;

        /**
         * The maximum time in a world
         */
        private static final int MAX_TIME = 23999;

        /**
         * Creates a new listener to listen to world time changes.
         *
         * @param plugin the plugin for which to listen to changes
         * @since 0.1.0
         */
        public Listener(@NotNull Plugin plugin) {
            this.plugin = plugin;
        }

        /**
         * Starts listening to changes in time for each world. Once a change is found, this will fire a
         * {@link WorldTimeChangeEvent}.
         *
         * @since 0.1.0
         */
        public void listenToTimeChanges() {
            Map<World, Long> previousTimes = new HashMap<>();

            Bukkit.getScheduler().runTaskTimer(this.plugin, () -> {
                for (World world : Bukkit.getWorlds()) {
                    long time = world.getTime();

                    if (previousTimes.containsKey(world)) {
                        long previousTime = previousTimes.get(world);

                        boolean normalTimeElapsed = isInBounds(time, previousTime, previousTime + MAX_TICK_DIFFERENCE);
                        boolean timeWrapAround = time < previousTime && isInBounds(previousTime, MAX_TIME - MAX_TICK_DIFFERENCE, MAX_TIME);

                        //if this isn't hit, time was probably changed manually
                        if (normalTimeElapsed || timeWrapAround) {
                            while (previousTime != world.getTime()) {
                                Bukkit.getPluginManager().callEvent(new WorldTimeChangeEvent(world, previousTime + 1));

                                previousTime++;

                                if (previousTime > MAX_TIME) {
                                    previousTime = 0;
                                }
                            }
                        }
                    } else {
                        Bukkit.getPluginManager().callEvent(new WorldTimeChangeEvent(world, time));
                    }

                    previousTimes.put(world, world.getTime());
                }
            }, 0, 1);
        }

        /**
         * Checks whether the given value is within the given boundary. The lower boundary value is exclusive, while the
         * upper boundary value is inclusive. If the value is in the boundary, true is returned; false otherwise.
         *
         * @param value the value to check
         * @param lowerBound the exclusive lower boundary
         * @param upperBound the inclusive upper boundary
         * @return true if the value is in the boundary
         * @since 0.1.0
         */
        @Contract(pure = true)
        private boolean isInBounds(long value, long lowerBound, long upperBound) {
            return value > lowerBound && value <= upperBound;
        }
    }
}
