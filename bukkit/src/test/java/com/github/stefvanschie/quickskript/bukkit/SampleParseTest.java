package com.github.stefvanschie.quickskript.bukkit;

import com.github.stefvanschie.quickskript.core.file.skript.FileSkript;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Tests the sample skript files.
 */
class SampleParseTest extends TestClassBase {
    @ParameterizedTest
    @ValueSource(strings = "Simple")
    void testValidSamples(String file) {
        FileSkript skript = getSkriptResource(file);
        skript.registerCommands(getSkriptLoader());
        skript.registerEventExecutors(getSkriptLoader());
    }
}
