package com.github.stefvanschie.quickskript.bukkit.plugin;

import com.github.stefvanschie.quickskript.bukkit.context.ExecuteContextImpl;
import com.github.stefvanschie.quickskript.bukkit.util.CommandMapWrapper;
import com.github.stefvanschie.quickskript.core.skript.SingleLineSkript;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.StringUtils;
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

    private ExecuteCommand() {
    }

    /**
     * Registers this {@link CommandExecutor} into Bukkit's command system.
     */
    public static void register() {
        var wrapper = new CommandMapWrapper();
        PluginCommand command = wrapper.create("skexec");
        command.setPermission("quickskript.exec");
        command.setDescription("Allows the execution of single line Skripts from the chat.");
        command.setExecutor(new ExecuteCommand());
        wrapper.register(command);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
            @NotNull String label, @NotNull String[] args) {
        String input = StringUtils.join(args, ' ');

        long startTime = System.nanoTime();
        var skript = new SingleLineSkript(input);
        Object result = skript.getParsedElement() == null
                ? null
                : skript.execute(new ExecuteContextImpl(skript, sender));
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
        sender.sendMessage(text);
        return true;
    }
}
