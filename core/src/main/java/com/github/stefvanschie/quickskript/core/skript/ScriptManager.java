package com.github.stefvanschie.quickskript.core.skript;

import com.github.stefvanschie.quickskript.core.file.skript.FileSkript;
import com.github.stefvanschie.quickskript.core.psi.exception.ParseException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * A script manager keeps track of loaded script files.
 *
 * @since 0.1.0
 */
public class ScriptManager {

    /**
     * The script loader with which to load scripts
     */
    @NotNull
    private final SkriptLoader loader;

    /**
     * A collection of the scripts that have been loaded.
     */
    @NotNull
    private final Collection<FileSkript> loadedScripts = new HashSet<>();

    /**
     * Creates a new manager for scripts with the given script loader.
     *
     * @param loader the loader with which to load scripts
     * @since 0.1.0
     */
    public ScriptManager(@NotNull SkriptLoader loader) {
        this.loader = loader;
    }

    /**
     * Loads the skript at the given path. This throws an {@link IOException} if the path does not exist or does not
     * point to a file. Symbolic links will be followed. If the script can't be parsed, a {@link ParseException} will
     * be thrown. The loaded script will be registered immediately.
     *
     * @param path the path where the script file resides
     * @return the parsed script file
     * @since 0.1.0
     */
    @NotNull
    public FileSkript loadScript(@NotNull Path path) throws IOException {
        if (!Files.isRegularFile(path)) {
            throw new FileNotFoundException();
        }

        File file = path.toFile();
        String scriptName = FileSkript.getName(file);
        FileSkript scriptFile = FileSkript.load(scriptName, file);

        scriptFile.registerAll(this.loader);

        this.loadedScripts.add(scriptFile);

        return scriptFile;
    }

    /**
     * This returns all the scripts that were loaded by this manager. The returned iterable is unmodifiable.
     *
     * @return the loaded scripts
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public Iterable<FileSkript> getLoadedScripts() {
        return Collections.unmodifiableCollection(this.loadedScripts);
    }
}
