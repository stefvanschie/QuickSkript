package com.github.stefvanschie.quickskript.file;

import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * Represents a skript file
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
     * @return the skript file, or null if it couldn't be loaded
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public static SkriptFile load(@NotNull File file) {
        Validate.isTrue(file.isFile() && file.canRead(), "The file must be a valid, readable, existing file.");

        List<String> strings;
        try {
            strings = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        //remove comments
        for (int i = 0; i < strings.size(); i++) {
            String line = strings.get(i);
            int index = line.indexOf('#');

            if (index != -1)
                strings.set(i, line.substring(0, index));
        }

        //if a line is empty or only contains spaces, remove it, otherwise it'll screw the rest of the algorithm up
        strings.removeIf(string -> string.isEmpty() || string.matches("[ ]+"));

        //remove trailing spaces
        for (int i = 0; i < strings.size(); i++) {
            String line = strings.get(i);
            int len = line.length();

            for (; len > 0; len--)
                if (!Character.isWhitespace(line.charAt(len - 1)))
                    break;

            strings.set(i, line.substring(0, len));
        }

        //replace \t with four spaces
        for (int i = 0; i < strings.size(); i++)
            strings.set(i, strings.get(i).replace("\t", "    "));

        SkriptFile skriptFile = new SkriptFile();
        skriptFile.parse(strings);
        return skriptFile;
    }
}
