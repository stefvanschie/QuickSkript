package com.github.stefvanschie.quickskript.core.psi;

import com.github.stefvanschie.quickskript.core.file.skript.FileSkript;
import com.github.stefvanschie.quickskript.core.skript.loader.StandaloneSkriptLoader;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A base class for each test in this project.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS) //TODO multithreading? concurrent tests
public class TestClassBase {

    private StandaloneSkriptLoader skriptLoader;

    @BeforeAll
    void initialize() {
        skriptLoader = new StandaloneSkriptLoader();
    }

    @AfterAll
    void deinitialize() {
        skriptLoader = null;
    }

    public StandaloneSkriptLoader getSkriptLoader() {
        return skriptLoader;
    }

    @NotNull
    private Collection<File> getSampleSkriptFiles() {
        URL resource = TestClassBase.class.getClassLoader().getResource("sample-skript-files");
        File directory = new File(Objects.requireNonNull(resource).getPath());

        return Arrays.asList(Objects.requireNonNull(directory.listFiles()));
    }

    @NotNull
    protected Collection<FileSkript> getSampleSkripts() {
        return getSampleSkriptFiles().parallelStream()
                .map(file -> {
                    try {
                        return FileSkript.load(skriptLoader, FileSkript.getName(file), file);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }
}
