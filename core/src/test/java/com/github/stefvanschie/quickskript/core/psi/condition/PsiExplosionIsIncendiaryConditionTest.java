package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiExplosionIsIncendiaryConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "the event-explosion is incendiary",
        "the event-explosion is fiery",
        "the event explosion is incendiary",
        "the event explosion is fiery",
        "the explosion is incendiary",
        "the explosion is fiery",
        "the event-explosion is not incendiary",
        "the event-explosion is not fiery",
        "the event-explosion isn't incendiary",
        "the event-explosion isn't fiery",
        "the event explosion is not incendiary",
        "the event explosion is not fiery",
        "the event explosion isn't incendiary",
        "the event explosion isn't fiery",
        "the explosion is not incendiary",
        "the explosion is not fiery",
        "the explosion isn't incendiary",
        "the explosion isn't fiery"
    })
    void test(String input) {
        assertInstanceOf(PsiExplosionIsIncendiaryCondition.class, loader.tryParseElement(input, -1));
    }
}
