package com.github.stefvanschie.quickskript.core.file.alias;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * An alias file. This alias file contains parsed data about its contents.
 *
 * @since 0.1.0
 */
public class AliasFile {

    /**
     * All the entries in this file
     */
    private final Set<SkriptPattern> entries = new HashSet<>();

    /**
     * Gets all the entries in this file.
     *
     * @return the entries
     * @since 0.1.0
     */
    public Iterable<? extends SkriptPattern> getEntries() {
        return entries;
    }

    /**
     * Parses an alias file from the specified input stream
     *
     * @param inputStream the input stream that contains the contents of the file
     * @return the alias file
     * @throws IOException when something went wrong while reading the file
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public static AliasFile parse(@NotNull InputStream inputStream) throws IOException {
        AliasFile file = new AliasFile();

        String[] lines = new String(inputStream.readAllBytes()).split("[\r\n]+");

        for (String line : lines) {
            file.entries.add(SkriptPattern.parse(line.trim()));
        }

        return file;
    }

    /**
     * Reads the alias file from the specified url. This throws an {@link IOException} when the method fails to read the
     * data from the url.
     *
     * @param url the url to read from
     * @throws IOException when reading the data failed
     * @since 0.1.0
     */
    public static AliasFile parse(@NotNull URL url) throws IOException {
        return parse(url.openStream());
    }
}
