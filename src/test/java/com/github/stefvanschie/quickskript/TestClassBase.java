package com.github.stefvanschie.quickskript;

import com.github.stefvanschie.quickskript.skript.SkriptLoader;
import org.apache.commons.lang.reflect.FieldUtils;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.*;
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
    void initialize() throws IllegalAccessException {
        Server server = mock(Server.class);
        when(server.getPluginManager()).thenReturn(new SimplePluginManager(server, new SimpleCommandMap(server)));
        FieldUtils.writeDeclaredStaticField(Bukkit.class, "server", server, true);

        QuickSkript plugin = mock(QuickSkript.class);
        FieldUtils.writeDeclaredStaticField(QuickSkript.class, "instance", plugin, true);
        when(plugin.getLogger()).thenReturn(Logger.getGlobal());

        new SkriptLoader();
    }

    @AfterAll
    void deinitialize() {
        SkriptLoader.get().close();
    }
}
