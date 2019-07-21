package com.github.stefvanschie.quickskript.bukkit;

import com.github.stefvanschie.quickskript.bukkit.skript.BukkitSkriptLoader;
import com.github.stefvanschie.quickskript.bukkit.util.event.ExperienceOrbSpawnEvent;
import com.github.stefvanschie.quickskript.bukkit.util.event.QuickSkriptPostEnableEvent;
import com.github.stefvanschie.quickskript.core.file.SkriptFile;
import com.github.stefvanschie.quickskript.core.psi.exception.ParseException;
import com.github.stefvanschie.quickskript.core.skript.Skript;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * Main QuickSkript class
 *
 * @since 0.1.0
 */
public class QuickSkript extends JavaPlugin {

    /**
     * The current instance of this plugin or null if the plugin is not enabled.
     */
    private static QuickSkript instance;

    public static void main(String[] args) {
        new QuickSkript().onEnable(); //fake entry point for code analyzers
        throw new AssertionError("Plugins shouldn't be used as entry points!");
    }

    /**
     * Since plugins are singletons by design in Bukkit, this method
     * aims to be a short and efficient way of getting the plugin instance.
     *
     * @return the current instance of the plugin or null if the plugin is not enabled
     * @since 0.1.0
     */
    @Contract(pure = true)
    public static QuickSkript getInstance() {
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEnable() {
        instance = this;

        PluginManager pluginManager = Bukkit.getPluginManager();

        //load custom events
        pluginManager.registerEvents(new ExperienceOrbSpawnEvent.Listener(), this);

        //load scripts
        try (SkriptLoader ignored = new BukkitSkriptLoader()) {
            File skriptFolder = new File(getDataFolder(), "skripts");

            if (!skriptFolder.exists() && !skriptFolder.mkdirs()) {
                getLogger().severe("Unable to create skripts folder.");
                return;
            }

            List<Skript> skripts = Arrays.stream(Objects.requireNonNull(skriptFolder.listFiles()))
                    .parallel()
                    .filter(file -> file.isFile() && file.getName().endsWith(".sk"))
                    .map(file -> {
                        String skriptName = SkriptFile.getName(file);
                        try {
                            return new Skript(skriptName, SkriptFile.load(file));
                        } catch (IOException e) {
                            getLogger().log(Level.SEVERE, "Unable to load skript named " + skriptName, e);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            for (Skript skript : skripts) {
                try {
                    skript.registerCommands();
                    skript.registerEventExecutors();
                } catch (ParseException e) {
                    getLogger().log(Level.SEVERE, "Error while parsing:" + e.getExtraInfo(skript.getName()), e);
                }
            }
        }

        pluginManager.callEvent(new QuickSkriptPostEnableEvent());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDisable() {
        instance = null;
    }
}
