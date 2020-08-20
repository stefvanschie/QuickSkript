package com.github.stefvanschie.quickskript.core.file.skript;

import com.github.stefvanschie.quickskript.core.skript.Skript;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a skript file. The source of the code does not necessary have to be a file,
 * the name only suggests that these lines are tightly joined together in an order.
 *
 * @since 0.1.0
 */
public class FileSkript implements Skript {

    /**
     * The name of this Skript. In case of real Skript files,
     * ths is the file name without the extension.
     */
    private final String name;

    /**
     * The section which stores all information of this file
     */
    @NotNull
    private final SkriptFileSection section;

    /**
     * Loads a skript file from a given file
     *
     * @param name the name of the Skript
     * @param file the actual file
     * @return the skript file
     * @throws IOException if an error occurs while reading the file
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public static FileSkript load(@NotNull String name, @NotNull File file) throws IOException {
        if (!file.isFile() || !file.canRead()) {
            throw new IllegalArgumentException("The file must be a valid, readable, existing file.");
        }

        return loadInternal(name, Files.readAllLines(file.toPath()));
    }

    /**
     * Loads a skript file from the given lines
     *
     * @param name the name of the Skript
     * @param lines the lines to load (will be cloned - won't be modified)
     * @return the skript file
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public static FileSkript load(@NotNull String name, @NotNull List<String> lines) {
        return loadInternal(name, new ArrayList<>(lines));
    }

    /**
     * Loads a skript file from the given lines.
     * This method mutates its parameter, therefore should only be used internally.
     *
     * @param name the name of the Skript
     * @param lines the lines to load
     * @return the skript file
     * @since 0.1.0
     */
    @NotNull
    private static FileSkript loadInternal(@NotNull String name, @NotNull List<String> lines) {
        //remove comments
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            int index = line.indexOf('#');

            if (index != -1) {
                lines.set(i, line.substring(0, index));
            }
        }

        //if a line is empty or only contains spaces, remove it, otherwise it'll screw the rest of the algorithm up
        Pattern emptyLinePattern = Pattern.compile(" +");
        lines.replaceAll(line -> emptyLinePattern.matcher(line).matches() ? "" : line);

        //remove trailing spaces and replace \t with four spaces
        for (int i = 0; i < lines.size(); i++) {
            lines.set(i, lines.get(i)
                    .replace("\t", "    ") //replace tabs with four spaces (U+0020)
                    .replaceAll("\\s+$", "")); //remove trailing spaces

            Matcher matcher = Pattern.compile("^\\s+").matcher(lines.get(i));
            lines.set(i, matcher.replaceAll(result -> " ".repeat(result.group().length())));
            //TODO pre-compile patterns
        }

        return new FileSkript(name, new SkriptFileSection("", 0, lines));
    }

    /**
     * A utility method which removes the .sk extension from
     * the file name if it is present and if the file name is not just that.
     *
     * @param file the file which is the source of the name
     * @return the user friendly name
     */
    @NotNull
    @Contract(pure = true)
    public static String getName(@NotNull File file) {
        String name = file.getName();
        return name.length() > 3 && name.endsWith(".sk")
                ? name.substring(0, name.length() - 3) : name;
    }

    /**
     * Creates a new instance backed by the specified section
     *
     * @param name the name of the Skript
     * @param section the backing section of this file
     */
    private FileSkript(@NotNull String name, @NotNull SkriptFileSection section) {
        this.name = name;
        this.section = section;
    }

    @NotNull
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns an immutable list of nodes
     *
     * @return the nodes
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public List<SkriptFileNode> getNodes() {
        return section.getNodes();
    }

    /**
     * Registers all events in this skript
     *
     * @param skriptLoader the skript loader to parse with
     * @since 0.1.0
     */
    public void registerEventExecutors(@NotNull SkriptLoader skriptLoader) {
        getNodes().stream()
                .filter(node -> node instanceof SkriptFileSection)
                .map(node -> (SkriptFileSection) node)
                .forEach(node -> registerEvent(skriptLoader, node));
    }

    /**
     * Registers all commands in this skript
     *
     * @param skriptLoader the skript loader to parse with
     * @since 0.1.0
     */
    public void registerCommands(@NotNull SkriptLoader skriptLoader) {
        getNodes().stream()
                .filter(node -> node.getText().startsWith("command")
                        && node instanceof SkriptFileSection)
                .forEach(node -> registerCommand(skriptLoader, (SkriptFileSection) node));
    }

    /**
     * Registers an individual command from the given section
     *
     * @param skriptLoader the skript loader to parse with
     * @param section the command section which starts with 'command'
     * @since 0.1.0
     */
    private void registerCommand(@NotNull SkriptLoader skriptLoader, @NotNull SkriptFileSection section) {
        skriptLoader.tryRegisterCommand(this, section);
    }

    /**
     * Registers an individual event from the given section
     *
     * @param skriptLoader the skript loader to parse with
     * @param section the command section which starts with 'on'
     * @since 0.1.0
     */
    private void registerEvent(@NotNull SkriptLoader skriptLoader, @NotNull SkriptFileSection section) {
        skriptLoader.tryRegisterEvent(this, section);
    }
}
