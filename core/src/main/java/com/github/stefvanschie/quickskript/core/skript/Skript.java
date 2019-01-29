package com.github.stefvanschie.quickskript.core.skript;

import com.github.stefvanschie.quickskript.core.file.SkriptFile;
import com.github.stefvanschie.quickskript.core.file.SkriptFileSection;
import com.github.stefvanschie.quickskript.core.skript.profiler.SimpleSkriptProfiler;
import com.github.stefvanschie.quickskript.core.skript.profiler.SkriptProfiler;
import org.jetbrains.annotations.NotNull;

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
     * A {@link SkriptProfiler} to be used by all skripts
     */
    @NotNull
    private static final SkriptProfiler PROFILER = new SimpleSkriptProfiler();

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
                .forEach(this::registerEvent);
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
     * Gets the global {@link SkriptProfiler}
     *
     * @return the profiler
     */
    public SkriptProfiler getSkriptProfiler() {
        return PROFILER;
    }

    /**
     * Registers an individual command from the given section
     *
     * @param section the command section which starts with 'command'
     * @since 0.1.0
     */
    private void registerCommand(@NotNull SkriptFileSection section) {
        SkriptLoader.get().tryRegisterCommand(this, section);
    }

    /**
     * Registers an individual event from the given section
     *
     * @param section the command section which starts with 'on'
     * @since 0.1.0
     */
    private void registerEvent(@NotNull SkriptFileSection section) {
        SkriptLoader.get().tryRegisterEvent(this, section);
    }
}
