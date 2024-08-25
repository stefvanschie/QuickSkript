package com.github.stefvanschie.quickskript.core;

import com.github.stefvanschie.quickskript.core.file.skript.FileSkript;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.net.URL;

/**
 * A base class for each test in this project.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class TestClassBase {

    private StandaloneSkriptLoader skriptLoader;

    @BeforeAll
    void initialize() {
        skriptLoader = new StandaloneSkriptLoader();
    }

    @AfterAll
    void deinitialize() {
        skriptLoader = null;
    }

    protected StandaloneSkriptLoader getSkriptLoader() {
        return skriptLoader;
    }

    @NotNull
    protected FileSkript getSkriptResource(@NotNull String filename) {
        try {
            URL resource = getClass().getClassLoader().getResource("sample-skript-files/" + filename + ".sk");
            //noinspection ConstantConditions
            File file = new File(resource.getPath());
            return FileSkript.load(FileSkript.getName(file), file);
        } catch (Exception e) {
            throw new RuntimeException("Error loading skript resource named: " + filename, e);
        }
    }
}