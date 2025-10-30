package com.github.stefvanschie.quickskript.spigot.plugin;

import com.github.stefvanschie.quickskript.spigot.context.ExecuteContextImpl;
import com.github.stefvanschie.quickskript.spigot.util.CommandMapWrapper;
import com.github.stefvanschie.quickskript.core.skript.SingleLineSkript;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.jetbrains.annotations.NotNull;

/**
 * A command that lets {@link CommandSender}s execute single-line Skript code from the chat/console.
 */
public class ExecuteCommand implements CommandExecutor {

    /**
     * The skript loader
     */
    private final SkriptLoader skriptLoader;

    /**
     * The skript run environment
     */
    private final SkriptRunEnvironment environment;

    /**
     * Creates a new execute command
     *
     * @param skriptLoader the associated skript loader
     * @param environment the associated run environment
     * @since 0.1.0
     */
    private ExecuteCommand(@NotNull SkriptLoader skriptLoader,
            @NotNull SkriptRunEnvironment environment) {
        this.skriptLoader = skriptLoader;
        this.environment = environment;
    }

    /**
     * Registers this {@link CommandExecutor} into Bukkit's command system.
     *
     * @param skriptLoader the skript loader to parse with
     */
    public static void register(@NotNull SkriptLoader skriptLoader,
            @NotNull SkriptRunEnvironment environment) {
        var wrapper = new CommandMapWrapper();
        PluginCommand command = wrapper.create("skexec");
        command.setPermission("quickskript.exec");
        command.setDescription("Allows the execution of single line Skripts from the chat.");
        command.setExecutor(new ExecuteCommand(skriptLoader, environment));
        wrapper.register(command);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
            @NotNull String label, @NotNull String[] args) {
        String input = String.join(" ", args);

        long startTime = System.nanoTime();
        var skript = new SingleLineSkript(skriptLoader, input);
        Object result = skript.getParsedElement() == null
                ? null
                : skript.execute(environment, new ExecuteContextImpl(skript, sender));
        long deltaMillis = (System.nanoTime() - startTime) / 1000000;

        String output = ChatColor.YELLOW + "Output: " + (skript.getParsedElement() == null
                ? ChatColor.RED + "ERROR: Parsing failed."
                : ChatColor.WHITE + String.valueOf(result));

        TextComponent text = new TextComponent(output);
        text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("")
                .append(ChatColor.YELLOW + "Input: " + ChatColor.WHITE + input)
                .append(ChatColor.YELLOW + "Evaluation took: " + ChatColor.WHITE
                        + deltaMillis + ChatColor.GRAY + " ms")
                .create()));
        sender.spigot().sendMessage(text);

        return true;
    }
}
