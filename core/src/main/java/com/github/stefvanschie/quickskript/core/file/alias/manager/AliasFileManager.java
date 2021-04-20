package com.github.stefvanschie.quickskript.core.file.alias.manager;

import com.github.stefvanschie.quickskript.core.file.alias.AliasFile;
import com.github.stefvanschie.quickskript.core.file.alias.ResolvedAliasEntry;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Manages the alias files.
 *
 * @since 0.1.0
 */
public class AliasFileManager {

    /**
     * The files and their relative paths.
     */
    private final Map<String, AliasFile> files = new HashMap<>();

    /**
     * Reads the alias file from the specified url and stores them by the given name. This throws an {@link IOException}
     * when the method fails to read the data from the url.
     *
     * @param url the url to read from
     * @param name the name of the file
     * @throws IOException when reading the data failed
     * @since 0.1.0
     */
    public void read(@NotNull URL url, @NotNull String name) throws IOException {
        files.put(name, AliasFile.parse(url.openStream()));
    }

    /**
     * Resolves all files and returns a collection of entries and categories
     *
     * @return all resolved entries and categories
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public Collection<? extends ResolvedAliasEntry> resolveAll() {
        var entries = new HashSet<ResolvedAliasEntry>();

        for (AliasFile file : files.values()) {
            entries.addAll(file.resolveAll(this));
        }

        return Collections.unmodifiableSet(entries);
    }

    /**
     * Gets the requested file by the specified relative file path. If the file by the given relative file path doesn't
     * exist or has not been loaded, this will return null.
     *
     * @param fileName the relative file name
     * @return the requested file or null
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public AliasFile getFile(@NotNull String fileName) {
        return files.get(fileName);
    }
}
