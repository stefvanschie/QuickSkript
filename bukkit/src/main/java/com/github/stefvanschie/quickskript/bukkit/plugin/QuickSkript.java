package com.github.stefvanschie.quickskript.bukkit.plugin;

import com.github.stefvanschie.quickskript.bukkit.skript.BukkitSkriptLoader;
import com.github.stefvanschie.quickskript.bukkit.util.event.ExperienceOrbSpawnEvent;
import com.github.stefvanschie.quickskript.bukkit.util.event.QuickSkriptPostEnableEvent;
import com.github.stefvanschie.quickskript.core.file.FileSkript;
import com.github.stefvanschie.quickskript.core.psi.exception.ParseException;
import com.github.stefvanschie.quickskript.core.skript.profiler.BasicSkriptProfiler;
import com.github.stefvanschie.quickskript.core.skript.profiler.NoOpSkriptProfiler;
import com.github.stefvanschie.quickskript.core.skript.profiler.SkriptProfiler;
import com.github.stefvanschie.quickskript.core.skript.profiler.WholeSkriptProfiler;
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

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        setProfilerImplementation();

        PluginManager pluginManager = Bukkit.getPluginManager();

        //load custom events
        pluginManager.registerEvents(new ExperienceOrbSpawnEvent.Listener(), this);

        var skriptLoader = new BukkitSkriptLoader();
        loadScripts();

        if (getConfig().getBoolean("enable-execute-command")) {
            ExecuteCommand.register();
        } else {
            skriptLoader.close();
        }

        pluginManager.callEvent(new QuickSkriptPostEnableEvent());
    }

    @Override
    public void onDisable() {
        instance = null;

        var skriptLoader = BukkitSkriptLoader.get();
        if (skriptLoader != null) skriptLoader.close();
    }

    private void setProfilerImplementation() {
        switch (getConfig().getString("profiler-implementation").toLowerCase()) {
            case "noop":
                SkriptProfiler.setActive(new NoOpSkriptProfiler());
                break;
            case "basic":
                SkriptProfiler.setActive(new BasicSkriptProfiler());
                break;
            case "whole":
                SkriptProfiler.setActive(new WholeSkriptProfiler());
                break;
            default:
                getLogger().severe("Invalid profiler implementation in config.yml, using default one.");
                break;
        }
    }

    private void loadScripts() {
        File skriptFolder = new File(getDataFolder(), "skripts");
        if (!skriptFolder.exists() && !skriptFolder.mkdirs()) {
            getLogger().severe("Unable to create skripts folder.");
            return;
        }

        List<FileSkript> skripts = Arrays.stream(Objects.requireNonNull(skriptFolder.listFiles()))
                .parallel()
                .filter(file -> file.isFile() && file.getName().endsWith(".sk"))
                .map(file -> {
                    String skriptName = FileSkript.getName(file);
                    try {
                        return FileSkript.load(skriptName, file);
                    } catch (IOException e) {
                        getLogger().log(Level.SEVERE, "Unable to load skript named " + skriptName, e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        for (FileSkript skript : skripts) {
            try {
                skript.registerCommands();
                skript.registerEventExecutors();
            } catch (ParseException e) {
                getLogger().log(Level.SEVERE, "Error while parsing:" + e.getExtraInfo(skript), e);
            }
        }
    }
}
