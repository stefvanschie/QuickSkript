package com.github.stefvanschie.quickskript;

import com.github.stefvanschie.quickskript.file.SkriptFile;
import com.github.stefvanschie.quickskript.skript.Skript;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

/**
 * Main QuickSkript class
 *
 * @since 0.1.0
 */
public class QuickSkript extends JavaPlugin {

    public static void main(String[] args) {
        new QuickSkript().onEnable(); //fake entry point for code analyzers
        throw new AssertionError("Plugins shouldn't be used as entry points!");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEnable() {
        File skriptFolder = new File(getDataFolder(), "skripts");

        if (!skriptFolder.exists() && !skriptFolder.mkdirs())
            getLogger().warning("Unable to create skripts folder.");

        for (File file : Objects.requireNonNull(skriptFolder.listFiles())) {
            if (!file.isFile())
                continue;

            SkriptFile skriptFile = SkriptFile.load(file);

            if (skriptFile == null) {
                getLogger().warning("Unable to load skript named " + file.getName());
                continue;
            }

            Skript skript = new Skript(skriptFile);

            skript.registerCommands();
            skript.registerEvents();
        }
    }
}
