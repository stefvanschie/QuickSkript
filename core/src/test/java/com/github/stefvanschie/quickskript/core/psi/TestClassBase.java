package com.github.stefvanschie.quickskript.core.psi;

import com.github.stefvanschie.quickskript.core.file.SkriptFile;
import com.github.stefvanschie.quickskript.core.skript.Skript;
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
    protected static Collection<Skript> getSampleSkripts() {
        Collection<File> files = getSampleSkriptFiles();
        Queue<Skript> result = new ArrayDeque<>(files.size());

        try {
            for (File file : files) {
                result.add(new Skript(SkriptFile.getName(file), SkriptFile.load(file)));
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
