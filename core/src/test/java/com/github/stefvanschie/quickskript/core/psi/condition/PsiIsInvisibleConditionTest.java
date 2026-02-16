package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiIsInvisibleConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "player is invisible",
        "player are invisible",
        "player isn't visible",
        "player is not visible",
        "player aren't visible",
        "player are not visible",
        "player is visible",
        "player are visible",
        "player isn't invisible",
        "player is not invisible",
        "player aren't invisible",
        "player are not invisible"
    })
    void test(String input) {
        assertInstanceOf(PsiIsInvisibleCondition.class, loader.tryParseElement(input, -1));
    }
}
