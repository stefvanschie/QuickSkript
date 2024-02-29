package com.github.stefvanschie.quickskript.bukkit.plugin;

import com.github.stefvanschie.quickskript.bukkit.integration.money.VaultIntegration;
import com.github.stefvanschie.quickskript.bukkit.integration.region.RegionIntegration;
import com.github.stefvanschie.quickskript.bukkit.skript.BukkitSkriptLoader;
import com.github.stefvanschie.quickskript.bukkit.util.event.ExperienceOrbSpawnEvent;
import com.github.stefvanschie.quickskript.bukkit.util.event.QuickSkriptPostEnableEvent;
import com.github.stefvanschie.quickskript.bukkit.util.event.ServerTickEvent;
import com.github.stefvanschie.quickskript.bukkit.util.event.WorldTimeChangeEvent;
import com.github.stefvanschie.quickskript.bukkit.util.event.region.RegionEnterEvent;
import com.github.stefvanschie.quickskript.bukkit.util.event.region.RegionLeaveEvent;
import com.github.stefvanschie.quickskript.bukkit.util.event.script.ScriptLoadEvent;
import com.github.stefvanschie.quickskript.bukkit.util.event.script.ScriptUnloadEvent;
import com.github.stefvanschie.quickskript.core.file.skript.FileSkript;
import com.github.stefvanschie.quickskript.core.psi.exception.ParseException;
import com.github.stefvanschie.quickskript.core.skript.ScriptManager;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.skript.profiler.BasicSkriptProfiler;
import com.github.stefvanschie.quickskript.core.skript.profiler.NoOpSkriptProfiler;
import com.github.stefvanschie.quickskript.core.skript.profiler.WholeSkriptProfiler;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Main QuickSkript class
 *
 * @since 0.1.0
 */
public class QuickSkript extends JavaPlugin implements Listener {

    /**
     * The current instance of this plugin or null if the plugin is not enabled.
     */
    private static QuickSkript instance;

    /**
     * The script manager for all script files.
     */
    private ScriptManager manager;

    /**
     * Integration with vault
     */
    private VaultIntegration vault;

    /**
     * Integration with WorldGuard and GriefPrevention
     */
    @NotNull
    private final RegionIntegration regions = new RegionIntegration();

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

        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(this, this);

        //load custom events
        pluginManager.registerEvents(new ExperienceOrbSpawnEvent.Listener(), this);

        new WorldTimeChangeEvent.Listener(this).listenToTimeChanges();

        if (pluginManager.isPluginEnabled("Vault")) {
            vault = new VaultIntegration();

            if (!vault.isEnabled()) {
                vault = null;
            }
        }

        this.regions.loadIntegrations();

        printIntegrations();

        var environment = new SkriptRunEnvironment();
        updateProfilerImplementation(environment);

        var skriptLoader = new BukkitSkriptLoader(environment);

        pluginManager.registerEvents(new RegionEnterEvent.Listener(skriptLoader), this);
        pluginManager.registerEvents(new RegionLeaveEvent.Listener(skriptLoader), this);

        this.manager = new ScriptManager(skriptLoader);

        loadScripts();

        if (getConfig().getBoolean("enable-execute-command")) {
            ExecuteCommand.register(skriptLoader, environment);
        }

        ServerTickEvent serverTickEvent = new ServerTickEvent();
        Bukkit.getScheduler().runTaskTimer(this, () -> pluginManager.callEvent(serverTickEvent), 0L, 1L);

        pluginManager.callEvent(new QuickSkriptPostEnableEvent());
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    /**
     * Calls the unload event for all script files if this plugin disables.
     *
     * @param event the plugin disable event
     */
    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        /* We can't use onDisable, because then we're no longer allowed to fire events. This event is fired just before
           this plugin gets disabled. */

        if (!event.getPlugin().equals(this)) {
            return;
        }

        for (FileSkript script : this.manager.getLoadedScripts()) {
            Bukkit.getPluginManager().callEvent(new ScriptUnloadEvent(script));
        }
    }

    private void updateProfilerImplementation(@NotNull SkriptRunEnvironment environment) {
        switch (getConfig().getString("profiler-implementation").toLowerCase()) {
            case "noop":
                environment.setProfiler(new NoOpSkriptProfiler());
                break;
            case "basic":
                environment.setProfiler(new BasicSkriptProfiler());
                break;
            case "whole":
                environment.setProfiler(new WholeSkriptProfiler());
                break;
            default:
                getLogger().severe("Invalid profiler implementation in config.yml, using default one.");
                break;
        }
    }

    /**
     * Loads all available scripts
     *
     * @since 0.1.0
     */
    private void loadScripts() {
        Path scriptFolder = getDataFolder().toPath().resolve("skripts");

        if (Files.notExists(scriptFolder)) {
            try {
                Files.createDirectories(scriptFolder);
            } catch (IOException exception) {
                getLogger().severe("Unable to create skripts folder.");
                exception.printStackTrace();
                return;
            }
        }

        try (Stream<Path> files = Files.list(scriptFolder)
                .filter(path -> Files.isRegularFile(path) && path.getFileName().toString().endsWith(".sk"))) {
            Collection<Path> scripts = files.collect(Collectors.toUnmodifiableSet());

            for (Path scriptPath : scripts) {
                try {
                    FileSkript script = this.manager.loadScript(scriptPath);

                    Bukkit.getPluginManager().callEvent(new ScriptLoadEvent(script));
                } catch (IOException exception) {
                    getLogger().severe("Unable to read script file.");
                    exception.printStackTrace();
                    return;
                } catch (ParseException exception) {
                    getLogger().severe("Error while parsing.");
                    exception.printStackTrace();
                    return;
                }
            }
        } catch (IOException exception) {
            getLogger().severe("Unable to read skripts folder.");
            exception.printStackTrace();
        }
    }

    /**
     * Prints the status of integrations with the plugin.
     *
     * @since 0.1.0
     */
    private void printIntegrations() {
        if (vault == null) {
            getLogger().warning("Vault has not been detected, certain functionality may be unavailable");
        }

        if (!this.regions.hasRegionIntegration()) {
            getLogger().warning("No region plugin has been detected, certain functionality may be unavailable");
        }
    }

    /**
     * Gets the integration with vault. This is null if vault is not available.
     *
     * @return vault integration or null if vault doesn't exist
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public VaultIntegration getVaultIntegration() {
        return vault;
    }

    /**
     * Gets the integration with region plugins.
     *
     * @return region integration
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public RegionIntegration getRegionIntegration() {
        return regions;
    }
}
