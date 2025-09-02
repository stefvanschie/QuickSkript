package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiCanBuildConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "player can build at the location at (31, 87, -73) in \"test\"",
        "player is allowed to build at the location at (-24, -43, -48) in \"test\"",
        "player are allowed to build at the location at (-89, 40, 96) in \"test\"",
        "player can't build at the location at (-29, 0, -23) in \"test\"",
        "player isn't allowed to build at the location at (-62, 57, 61) in \"test\"",
        "player aren't allowed to build at the location at (-75, 65, -77) in \"test\"",
        "player cannot build at the location at (20, -62, -86) in \"test\"",
        "player is not allowed to build at the location at (76, -76, 61) in \"test\"",
        "player are not allowed to build at the location at (-100, 28, -45) in \"test\""
    })
    void test(String input) {
        assertInstanceOf(PsiCanBuildCondition.class, loader.tryParseElement(input, -1));
    }
}
