package com.github.stefvanschie.quickskript.core.psi;

import com.github.stefvanschie.quickskript.core.file.FileSkript;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A base class for each test in this project.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestClassBase {

    @NotNull
    private static Collection<File> getSampleSkriptFiles() {
        URL resource = TestClassBase.class.getClassLoader().getResource("sample-skript-files");
        File directory = new File(Objects.requireNonNull(resource).getPath());
        return Arrays.asList(Objects.requireNonNull(directory.listFiles()));
    }

    @NotNull
    protected static Collection<FileSkript> getSampleSkripts() {
        return getSampleSkriptFiles().parallelStream()
                .map(file -> {
                    try {
                        return FileSkript.load(FileSkript.getName(file), file);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    @BeforeAll
    void initialize() {
        new StandaloneSkriptLoader();
    }

    @AfterAll
    void deinitialize() {
        SkriptLoader.get().close();
    }
}
