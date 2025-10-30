package com.github.stefvanschie.quickskript.paper.util.event.region;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.util.literal.Region;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * An event fired whenever a player enters a region.
 *
 * @since 0.1.0
 */
public class RegionEnterEvent extends RegionEvent {

    /**
     * A HandlerList to please Bukkit
     */
    @NotNull
    private static final HandlerList HANDLER_LIST = new HandlerList();

    /**
     * Creates a new region enter event.
     *
     * @param region the region that was entered
     * @since 0.1.0
     */
    private RegionEnterEvent(@NotNull Region region) {
        super(region);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    /**
     * A static method to please Bukkit.
     *
     * @return the handler list for this event
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    /**
     * A way to listen for the necessary events to call {@link RegionEnterEvent}.
     *
     * @since 0.1.0
     */
    public static class Listener implements org.bukkit.event.Listener {

        /**
         * The skript loader for this listener.
         */
        @NotNull
        private final SkriptLoader skriptLoader;

        /**
         * Creates a new listener for firing region enter events with the given skript loader.
         *
         * @param skriptLoader the skript loader
         * @since 0.1.0
         */
        public Listener(@NotNull SkriptLoader skriptLoader) {
            this.skriptLoader = skriptLoader;
        }

        /**
         * Called whenever a players moves. This will fire a region enter event if a player entered a region.
         *
         * @param event the fired event
         * @since 0.1.0
         */
        @EventHandler(ignoreCancelled = true)
        public void onPlayerMove(@NotNull PlayerMoveEvent event) {
            Location from = event.getFrom();
            Location to = event.getTo();

            int fromBlockX = from.getBlockX();
            int fromBlockY = from.getBlockY();
            int fromBlockZ = from.getBlockZ();

            int toBlockX = to.getBlockX();
            int toBlockY = to.getBlockY();
            int toBlockZ = to.getBlockZ();

            if (fromBlockX == toBlockX && fromBlockY == toBlockY && fromBlockZ == toBlockZ) {
                return;
            }

            for (Region region : this.skriptLoader.getRegionRegistry().getRegions()) {
                boolean nowInside = region.contains(toBlockX, toBlockY, toBlockZ);
                boolean wasInside = region.contains(fromBlockX, fromBlockY, fromBlockZ);

                if (nowInside && !wasInside) {
                    Bukkit.getPluginManager().callEvent(new RegionEnterEvent(region));
                }
            }
        }
    }
}
