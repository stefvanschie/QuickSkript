package com.github.stefvanschie.quickskript.skript;

import com.github.stefvanschie.quickskript.QuickSkript;
import com.github.stefvanschie.quickskript.file.SkriptFile;
import com.github.stefvanschie.quickskript.file.SkriptFileLine;
import com.github.stefvanschie.quickskript.file.SkriptFileNode;
import com.github.stefvanschie.quickskript.file.SkriptFileSection;
import com.github.stefvanschie.quickskript.skript.util.ExecutionTarget;
import com.github.stefvanschie.quickskript.util.Text;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A class for loading and containing skript files
 */
public class Skript {

    /**
     * The name of the skript
     */
    @NotNull
    private final String name;

    /**
     * The internal skript file
     */
    @NotNull
    private final SkriptFile file;

    /**
     * Constructs a new skript object
     *
     * @param name the name of the skript,
     * eg. the name of the file without the .sk extension
     * @param file the file this skript belongs to
     * @since 0.1.0
     */
    public Skript(@NotNull String name, @NotNull SkriptFile file) {
        this.name = name;
        this.file = file;
    }

    /**
     * Returns the name of this skript.
     *
     * @return the name of this skript
     * @since 0.1.0
     */
    @NotNull
    public String getName() {
        return name;
    }

    /**
     * Registers all events in this skript
     *
     * @since 0.1.0
     */
    public void registerEventExecutors() {
        file.getNodes().stream()
                .filter(node -> node instanceof SkriptFileSection)
                .map(node -> (SkriptFileSection) node)
                .forEach(section -> SkriptLoader.get().tryRegisterEventExecutor(section.getText(),
                        () -> new SkriptEventExecutor(this, section)));
    }

    /**
     * Registers all commands in this skript
     *
     * @since 0.1.0
     */
    public void registerCommands() {
        file.getNodes().stream()
                .filter(node -> node.getText().startsWith("command")
                        && node instanceof SkriptFileSection)
                .forEach(node -> registerCommand((SkriptFileSection) node));
    }

    /**
     * Registers an individual commands from the given section
     *
     * @param section the command section which starts with 'command'
     * @since 0.1.0
     */
    private void registerCommand(@NotNull SkriptFileSection section) {
        //noinspection HardcodedFileSeparator
        String commandName = section.getText().substring("command /".length());

        SkriptLoader.get().registerCommand(commandName, command -> {

            List<SkriptFileLine> lines = section.getNodes().stream()
                    .filter(node -> node instanceof SkriptFileLine)
                    .map(node -> (SkriptFileLine) node)
                    .collect(Collectors.toList());

            String description = getFileLineValue(lines, "description:",
                    "Command " + commandName + " has multiple valid descriptions");
            if (description != null) {
                command.setDescription(Text.parse(description).construct());
            }

            String aliases = getFileLineValue(lines, "aliases:",
                    "Command " + commandName + " has multiple valid aliases");
            if (aliases != null) {
                command.setAliases(Arrays.asList(StringUtils.replace(aliases, " ", "").split(",")));
            }

            String permission = getFileLineValue(lines, "permission:",
                    "Command " + commandName + " has multiple valid permissions");
            if (permission != null) {
                command.setPermission(Text.parse(permission).construct());
            }

            String permissionMessage = getFileLineValue(lines, "permission message:",
                    "Command " + commandName + " has multiple valid permission messages");
            if (permissionMessage != null) {
                command.setPermissionMessage(Text.parse(permissionMessage).construct());
            }

            String usage = getFileLineValue(lines, "usage:",
                    "Command " + commandName + " has multiple valid usages");
            if (usage != null) {
                command.setUsage(Text.parse(usage).construct());
            }

            String rawTarget = getFileLineValue(lines, "usage:",
                    "Command " + commandName + " has multiple valid execution targets");
            ExecutionTarget target = rawTarget == null ? null : ExecutionTarget.parse(rawTarget);

            SkriptFileSection trigger = null;

            for (SkriptFileNode node : section.getNodes()) {
                if (node instanceof SkriptFileSection && node.getText().equalsIgnoreCase("trigger")) {

                    if (trigger != null) {
                        QuickSkript.getInstance().getLogger().warning("Command " + commandName +
                                " has multiple valid triggers");
                        break;
                    }

                    trigger = (SkriptFileSection) node;
                }
            }

            if (trigger == null) {
                QuickSkript.getInstance().getLogger().severe("Command " + commandName +
                        " failed to load, because no trigger is set");
                return;
            }

            command.setExecutor(new SkriptCommandExecutor(this, trigger, target));
        });
    }

    /**
     * Finds a line which starts with the specified key.
     *
     * @param lines the lines in which to search
     * @param key the key of the value
     * @param multipleMatchWarning the warning to log in case of multiple matches
     * @return the found value or null in case none were found
     */
    @Nullable
    @Contract(pure = true)
    private static String getFileLineValue(@NotNull List<SkriptFileLine> lines, @NotNull String key,
                                           @NotNull String multipleMatchWarning) {
        String value = null;

        for (SkriptFileLine line : lines) {
            if (line.getText().startsWith(key)) {

                if (value != null) {
                    QuickSkript.getInstance().getLogger().warning(multipleMatchWarning);
                    break;
                }

                value = line.getText().substring(key.length()).trim();
            }
        }

        return value;
    }
}
