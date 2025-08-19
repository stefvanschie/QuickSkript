package com.github.stefvanschie.quickskript.core.util.registry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class EnchantmentRegistryTest {

    @Test
    void testByName() {
        var enchantmentRegistry = new EnchantmentRegistry();

        assertNotNull(enchantmentRegistry.byName("minecraft:bane_of_arthropods"));
        assertNotNull(enchantmentRegistry.byName("knockback"));
        assertNotNull(enchantmentRegistry.byName("Mending"));
    }
}
