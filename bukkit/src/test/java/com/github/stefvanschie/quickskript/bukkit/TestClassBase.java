package com.github.stefvanschie.quickskript.bukkit;

import com.github.stefvanschie.quickskript.bukkit.plugin.QuickSkript;
import com.github.stefvanschie.quickskript.bukkit.skript.BukkitSkriptLoader;
import com.github.stefvanschie.quickskript.core.file.skript.FileSkript;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.apache.commons.lang.reflect.FieldUtils;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.Mockito;

import java.io.File;
import java.net.URL;
import java.util.logging.Logger;

/**
 * A base for each class in this project.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Execution(ExecutionMode.CONCURRENT)
public abstract class TestClassBase {

    private BukkitSkriptLoader skriptLoader;

    @BeforeAll
    void initialize() throws ReflectiveOperationException {
        Server server = Mockito.mock(Server.class);
        SimplePluginManager pluginManager = new SimplePluginManager(server, new SimpleCommandMap(server));
        Mockito.when(server.getPluginManager()).thenReturn(pluginManager);
        FieldUtils.writeDeclaredStaticField(Bukkit.class, "server", server, true);

        QuickSkript plugin = Mockito.mock(QuickSkript.class);
        Mockito.when(plugin.isEnabled()).thenReturn(true);
        Mockito.when(plugin.getLogger()).thenReturn(Logger.getGlobal());
        FieldUtils.writeDeclaredStaticField(QuickSkript.class, "instance", plugin, true);

        skriptLoader = new BukkitSkriptLoader(new SkriptRunEnvironment());
    }

    @AfterAll
    void deinitialize() {
        skriptLoader = null;
    }

    protected BukkitSkriptLoader getSkriptLoader() {
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
