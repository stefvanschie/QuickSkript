package com.github.stefvanschie.quickskript.skript;

import com.github.stefvanschie.quickskript.QuickSkript;
import com.github.stefvanschie.quickskript.file.SkriptFile;
import com.github.stefvanschie.quickskript.file.SkriptFileNode;
import com.github.stefvanschie.quickskript.file.SkriptFileSection;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * A class for loading and containing skript files
 */
public class Skript {

    /**
     * The internal skript file
     */
    @NotNull
    private SkriptFile file;

    /**
     * Constructs a new skript object
     *
     * @param file the file this skript belongs to
     * @since 0.1.0
     */
    public Skript(@NotNull SkriptFile file) {
        this.file = file;
    }

    /**
     * Registers all commands in this skript
     *
     * @since 0.1.0
     */
    public void registerCommands() {
        file.getNodes().stream().filter(node -> {
            String text = node.getText();

            return text != null && text.startsWith("command") && node instanceof SkriptFileSection;
        }).forEach(node -> registerCommand((SkriptFileSection) node));
    }

    /**
     * Registers an individual commands from the given section
     *
     * @param section the command section which starts with 'command'
     * @since 0.1.0
     */
    private void registerCommand(@NotNull SkriptFileSection section) {
        System.out.println("Registering command " + section.getText());

        String text = section.getText();

        if (text == null)
            throw new IllegalStateException("Command is file itself, which isn't possible");

        //noinspection HardcodedFileSeparator
        String commandName = section.getText().substring("command /".length());

        System.out.println("Command name " + commandName);

        PluginCommand command = createCommand(commandName, QuickSkript.getPlugin(QuickSkript.class));

        if (command == null) {
            QuickSkript.getPlugin(QuickSkript.class).getLogger()
                .warning("Command " + commandName + " failed to load.");
            return;
        }

        SkriptFileSection trigger = null;

        for (SkriptFileNode node : section.getNodes()) {
            if (node instanceof SkriptFileSection && node.getText() != null &&
                node.getText().equalsIgnoreCase("trigger")) {
                trigger = (SkriptFileSection) node;
                break;
            }
        }

        if (trigger == null) {
            QuickSkript.getPlugin(QuickSkript.class).getLogger()
                .warning("Command " + commandName + " failed to load, because no trigger is set");
            return;
        }

        command.setExecutor(new SkriptCommand(trigger));

        CommandMap commandMap = getCommandMap();

        if (commandMap == null) {
            QuickSkript.getPlugin(QuickSkript.class).getLogger()
                .warning("Unable to locate command map, command " + commandName + " failed to load.");
            return;
        }

        getCommandMap().register("quickskript", command);

        System.out.println("Registered command");
    }

    /**
     * Creates a new command from the given name, belonging to the specified plugin
     *
     * @param name the name of the commands
     * @param plugin the plugin this command belongs to
     * @return the newly created command
     * @since 0.1.0
     */
    @Nullable
    private PluginCommand createCommand(String name, Plugin plugin) {
        try {
            Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            c.setAccessible(true);

            return c.newInstance(name, plugin);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Gets the command map internally used
     *
     * @return the command map
     * @since 0.1.0
     */
    @Nullable
    private CommandMap getCommandMap() {
        try {
            Field commandMap = SimplePluginManager.class.getDeclaredField("commandMap");
            commandMap.setAccessible(true);

            return (CommandMap) commandMap.get(Bukkit.getPluginManager());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}
