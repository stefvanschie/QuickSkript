package com.github.stefvanschie.quickskript.core.util.registry;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class EnchantmentRegistryTest {

    private static EnchantmentRegistry registry;

    @BeforeAll
    static void init() {
        registry = new EnchantmentRegistry();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "minecraft:bane_of_arthropods",
        "knockback",
        "Mending"
    })
    void testByName(String name) {
        assertNotNull(registry.byName(name));
    }
}
