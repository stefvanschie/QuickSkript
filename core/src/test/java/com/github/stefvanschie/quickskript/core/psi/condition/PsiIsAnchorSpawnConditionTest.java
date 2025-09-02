package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiIsAnchorSpawnConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "the respawn location was a respawn anchor",
        "the respawn location was respawn anchor",
        "the respawn location is a respawn anchor",
        "the respawn location is respawn anchor",
        "respawn location was a respawn anchor",
        "respawn location was respawn anchor",
        "respawn location is a respawn anchor",
        "respawn location is respawn anchor",
        "the respawn location wasn't a respawn anchor",
        "the respawn location wasn't respawn anchor",
        "the respawn location was not a respawn anchor",
        "the respawn location was not respawn anchor",
        "the respawn location isn't a respawn anchor",
        "the respawn location isn't respawn anchor",
        "the respawn location is not a respawn anchor",
        "the respawn location is not respawn anchor",
        "respawn location wasn't a respawn anchor",
        "respawn location wasn't respawn anchor",
        "respawn location was not a respawn anchor",
        "respawn location was not respawn anchor",
        "respawn location isn't a respawn anchor",
        "respawn location isn't respawn anchor",
        "respawn location is not a respawn anchor",
        "respawn location is not respawn anchor"
    })
    void test(String input) {
        assertInstanceOf(PsiIsAnchorSpawnCondition.class, loader.tryParseElement(input, -1));
    }
}
