package com.github.stefvanschie.quickskript.file;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a skript file. The source of the code does not necessary have to be a file,
 * the name only suggests that these lines are tightly joined together in an order.
 *
 * @since 0.1.0
 */
public class SkriptFile extends SkriptFileSection {

    /**
     * {@inheritDoc}
     */
    private SkriptFile() {
        super(null);
    }

    /**
     * Loads a skript file from a given file
     *
     * @param file the actual file
     * @return the skript file
     * @throws IOException if an error occurs while reading the file
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public static SkriptFile load(@NotNull File file) throws IOException {
        Validate.isTrue(file.isFile() && file.canRead(), "The file must be a valid, readable, existing file.");
        return load(Files.readAllLines(file.toPath()));
    }

    /**
     * Loads a skript file from the given lines
     *
     * @param lines the lines to load (the variable will be cloned - won't be modified)
     * @return the skript file
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public static SkriptFile load(List<String> lines) {
        lines = new ArrayList<>(lines);

        //remove comments
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            int index = line.indexOf('#');

            if (index != -1)
                lines.set(i, line.substring(0, index));
        }

        //if a line is empty or only contains spaces, remove it, otherwise it'll screw the rest of the algorithm up
        lines.removeIf(string -> string.isEmpty() || string.matches(" +"));

        //remove trailing spaces and replace \t with four spaces
        for (int i = 0; i < lines.size(); i++)
            lines.set(i, StringUtils.replace(StringUtils.stripEnd(lines.get(i), null), "\t", "    "));

        SkriptFile skriptFile = new SkriptFile();
        skriptFile.parse(lines);
        return skriptFile;
    }

    /**
     * Gets a user friendly name for a {@link SkriptFile} from the given {@link File}.
     *
     * @param file the source of the file name
     * @return the user friendly name
     */
    @NotNull
    @Contract(pure = true)
    public static String getName(@NotNull File file) {
        int index = file.getName().lastIndexOf('.');
        return index == -1 ? file.getName() : file.getName().substring(0, index);
    }
}
