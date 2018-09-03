package com.github.stefvanschie.quickskript.skript;

import com.github.stefvanschie.quickskript.QuickSkript;
import com.github.stefvanschie.quickskript.file.SkriptFile;
import com.github.stefvanschie.quickskript.file.SkriptFileLine;
import com.github.stefvanschie.quickskript.file.SkriptFileNode;
import com.github.stefvanschie.quickskript.file.SkriptFileSection;
import com.github.stefvanschie.quickskript.skript.util.ExecutionTarget;
import com.github.stefvanschie.quickskript.util.TextMessage;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * A class for loading and containing skript files
 */
public class Skript {

    /**
     * The internal skript file
     */
    @NotNull
    private final SkriptFile file;

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
     * Registers all events in this skript
     *
     * @since 0.1.0
     */
    public void registerEvents() {
        file.getNodes().stream()
                .filter(node -> node instanceof SkriptFileSection && node.getText() != null)
                .forEach(node -> registerEvent((SkriptFileSection) node));
    }

    /**
     * Registers an individual commands from the given section
     *
     * @param section the command section which starts with 'command'
     * @since 0.1.0
     */
    private void registerCommand(@NotNull SkriptFileSection section) {
        String text = section.getText();

        if (text == null)
            throw new IllegalStateException("Command is file itself, which isn't possible");

        //noinspection HardcodedFileSeparator
        String commandName = section.getText().substring("command /".length());

        SkriptLoader.get().registerCommand(commandName, command -> {

            //noinspection ConstantConditions
            section.getNodes().stream()
                    .filter(node -> node instanceof SkriptFileLine &&
                            node.getText() != null &&
                            node.getText().startsWith("description:"))
                    .findAny()
                    .ifPresent(description -> command.setDescription(TextMessage.parse(
                            description.getText().substring("description:".length()).trim()).construct()
                    ));

            //noinspection ConstantConditions
            section.getNodes().stream()
                    .filter(node -> node instanceof SkriptFileLine &&
                            node.getText() != null &&
                            node.getText().startsWith("aliases:"))
                    .findAny()
                    .ifPresent(aliases -> command.setAliases(Arrays.asList(
                            aliases.getText().substring("aliases:".length()).replace(" ", "").split(",")
                    )));

            //noinspection ConstantConditions
            section.getNodes().stream()
                    .filter(node -> node instanceof SkriptFileLine &&
                            node.getText() != null &&
                            node.getText().startsWith("permission:"))
                    .findAny()
                    .ifPresent(permission -> command.setPermission(
                            permission.getText().substring("permission:".length()).trim()
                    ));

            //noinspection ConstantConditions
            section.getNodes().stream()
                    .filter(node -> node instanceof SkriptFileLine &&
                            node.getText() != null &&
                            node.getText().startsWith("permission message:"))
                    .findAny()
                    .ifPresent(permissionMessage -> command.setPermissionMessage(TextMessage.parse(
                            permissionMessage.getText().substring("permission message:".length()).trim()
                    ).construct()));

            //noinspection ConstantConditions
            section.getNodes().stream()
                    .filter(node -> node instanceof SkriptFileLine &&
                            node.getText() != null &&
                            node.getText().startsWith("usage:"))
                    .findAny()
                    .ifPresent(usage -> command.setUsage(TextMessage.parse(
                            usage.getText().substring("usage:".length()).trim()).construct()
                    ));

            ExecutionTarget target = section.getNodes().stream()
                    .filter(node -> node instanceof SkriptFileLine &&
                            node.getText() != null &&
                            node.getText().startsWith("executable by:"))
                    .limit(1)
                    .map(executableBy -> ExecutionTarget.parse(
                            executableBy.getText().substring("executable by:".length()).trim()
                    ))
                    .findAny()
                    .orElse(null);

            SkriptFileSection trigger = null;

            for (SkriptFileNode node : section.getNodes()) {
                if (node instanceof SkriptFileSection && node.getText() != null &&
                        node.getText().equalsIgnoreCase("trigger")) {

                    if (trigger != null) {
                        QuickSkript.getInstance().getLogger().warning("Command " + commandName + " has multiple valid triggers");
                        break;
                    }

                    trigger = (SkriptFileSection) node;
                }
            }

            if (trigger == null) {
                QuickSkript.getInstance().getLogger().severe("Command " + commandName + " failed to load, because no trigger is set");
                return;
            }

            command.setExecutor(new SkriptCommand(trigger, target));
        });
    }

    /**
     * Tries to register this event. If the provided section isn't a valid event, this method will silently fail.
     *
     * @param section the section the event is contained in.
     * @since 0.1.0
     */
    private void registerEvent(@NotNull SkriptFileSection section) {
        String text = section.getText();

        if (text == null)
            return;

        SkriptLoader.get().tryRegisterEvent(text, () -> new SkriptEvent(section));
    }
}
