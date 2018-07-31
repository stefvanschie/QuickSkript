package com.github.stefvanschie.quickskript.file;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
     * @return the skript file
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public static SkriptFile load(@NotNull File file) {
        SkriptFile skriptFile = new SkriptFile();

        try {
            List<String> strings = Files.readAllLines(file.toPath());

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

            skriptFile.parse(strings);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return skriptFile;
    }
}
