package com.github.stefvanschie.quickskript.bukkit.util;

import com.github.stefvanschie.quickskript.bukkit.plugin.QuickSkript;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

/**
 * A class exposing Bukkit's {@link CommandMap}, allowing the dynamic creation and registration of commands.
 *
 * @since 0.1.0
 */
public class CommandMapWrapper {

    /**
     * The constructor of {@link PluginCommand} wrapped into a {@link Function}.
     */
    @NotNull
    private final Function<String, PluginCommand> constructor;

    /**
     * The internally used instance of the {@link CommandMap}.
     */
    @NotNull
    private final CommandMap map;

    public CommandMapWrapper() {
        try {
            Constructor<PluginCommand> rawConstructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            rawConstructor.setAccessible(true);

            constructor = name -> {
                try {
                    return rawConstructor.newInstance(name, QuickSkript.getInstance());
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    throw new AssertionError("Error while constructing a PluginCommand instance:", e);
                }
            };

            Field commandMapField = SimplePluginManager.class.getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            map = (CommandMap) commandMapField.get(Bukkit.getPluginManager());

        } catch (ReflectiveOperationException e) {
            throw new AssertionError("Error while getting the CommandMap:", e);
        }
    }

    public PluginCommand create(String name) {
        return constructor.apply(name);
    }

    public void register(PluginCommand command) {
        map.register("quickskript", command);
    }
}
