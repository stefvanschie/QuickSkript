package com.github.stefvanschie.quickskript;

import com.github.stefvanschie.quickskript.file.SkriptFile;
import com.github.stefvanschie.quickskript.psi.exception.ParseException;
import com.github.stefvanschie.quickskript.skript.Skript;
import com.github.stefvanschie.quickskript.skript.SkriptLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;

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
     * {@inheritDoc}
     */
    @Override
    public void onEnable() {
        instance = this;

        try (SkriptLoader ignored = new SkriptLoader()) {

            File skriptFolder = new File(getDataFolder(), "skripts");

            if (!skriptFolder.exists() && !skriptFolder.mkdirs()) {
                getLogger().severe("Unable to create skripts folder.");
                return;
            }

            for (File file : Objects.requireNonNull(skriptFolder.listFiles())) {
                if (!file.isFile() || !file.getName().endsWith(".sk"))
                    continue;

                String skriptName = SkriptFile.getName(file);
                SkriptFile skriptFile;

                try {
                    skriptFile = SkriptFile.load(file);
                } catch (IOException e) {
                    getLogger().log(Level.SEVERE, "Unable to load skript named " + skriptName, e);
                    continue;
                }

                Skript skript = new Skript(skriptName, skriptFile);

                try {
                    skript.registerCommands();
                } catch (ParseException e) {
                    getLogger().log(Level.SEVERE, "Error while parsing commands of skript named " + skriptName, e);
                }

                try {
                    skript.registerEventExecutors();
                } catch (ParseException e) {
                    getLogger().log(Level.SEVERE, "Error while parsing event executors of skript named " + skriptName, e);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDisable() {
        instance = null;
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
}
