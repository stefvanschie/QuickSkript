package com.github.stefvanschie.quickskript;

import com.github.stefvanschie.quickskript.file.SkriptFile;
import com.github.stefvanschie.quickskript.skript.Skript;
import com.github.stefvanschie.quickskript.skript.SkriptLoader;
import org.apache.commons.lang.reflect.FieldUtils;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;

import static org.mockito.Mockito.*;

/**
 * A base class for each test in this project.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestClassBase {

    @NotNull
    protected static Collection<File> getSampleSkriptFiles() {
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @BeforeAll
    void initialize() throws ReflectiveOperationException {
        Server server = mock(Server.class);
        SimplePluginManager pluginManager = new SimplePluginManager(server, new SimpleCommandMap(server));
        when(server.getPluginManager()).thenReturn(pluginManager);
        FieldUtils.writeDeclaredStaticField(Bukkit.class, "server", server, true);

        QuickSkript plugin = mock(QuickSkript.class);
        when(plugin.isEnabled()).thenReturn(true);
        when(plugin.getLogger()).thenReturn(Logger.getGlobal());
        FieldUtils.writeDeclaredStaticField(QuickSkript.class, "instance", plugin, true);

        new SkriptLoader();
    }

    @AfterAll
    void deinitialize() {
        SkriptLoader.get().close();
    }
}
