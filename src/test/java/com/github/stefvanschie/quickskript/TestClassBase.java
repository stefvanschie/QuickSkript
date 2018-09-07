package com.github.stefvanschie.quickskript;

import com.github.stefvanschie.quickskript.skript.SkriptLoader;
import org.apache.commons.lang.reflect.FieldUtils;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.util.logging.Logger;

import static org.mockito.Mockito.*;

/**
 * A base class for each test in this project.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestClassBase {

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
