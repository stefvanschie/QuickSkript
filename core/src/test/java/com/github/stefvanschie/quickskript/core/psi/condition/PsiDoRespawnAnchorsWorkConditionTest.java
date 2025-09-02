package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiDoRespawnAnchorsWorkConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "respawn anchors do work in \"test\"",
        "respawn anchors work in \"test\"",
        "respawn anchors don't work in \"test\"",
        "respawn anchors do not work in \"test\""
    })
    void test(String input) {
        assertInstanceOf(PsiDoRespawnAnchorsWorkCondition.class, loader.tryParseElement(input, -1));
    }
}
