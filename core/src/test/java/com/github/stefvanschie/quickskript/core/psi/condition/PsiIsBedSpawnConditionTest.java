package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiIsBedSpawnConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "the respawn location was a bed",
        "the respawn location was bed",
        "the respawn location is a bed",
        "the respawn location is bed",
        "respawn location was a bed",
        "respawn location was bed",
        "respawn location is a bed",
        "respawn location is bed",
        "the respawn location wasn't a bed",
        "the respawn location wasn't bed",
        "the respawn location was not a bed",
        "the respawn location was not bed",
        "the respawn location isn't a bed",
        "the respawn location isn't bed",
        "the respawn location is not a bed",
        "the respawn location is not bed",
        "respawn location wasn't a bed",
        "respawn location wasn't bed",
        "respawn location was not a bed",
        "respawn location was not bed",
        "respawn location isn't a bed",
        "respawn location isn't bed",
        "respawn location is not a bed",
        "respawn location is not bed"
    })
    void test(String input) {
        assertInstanceOf(PsiIsBedSpawnCondition.class, loader.tryParseElement(input, -1));
    }
}
