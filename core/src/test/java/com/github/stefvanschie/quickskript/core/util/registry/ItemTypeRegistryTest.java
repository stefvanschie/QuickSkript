package com.github.stefvanschie.quickskript.core.util.registry;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class ItemTypeRegistryTest {

    @Test
    void testFullNamespacedKey() {
        var entry = new ItemTypeRegistry.Entry(SkriptPattern.parse(""), "minecraft:wheat", new HashSet<>());

        assertEquals("minecraft:wheat", entry.getFullNamespacedKey());
    }
}
