package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiIsClimbingConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "the player is climbing",
        "the player are climbing",
        "the player isn't climbing",
        "the player is not climbing",
        "the player aren't climbing",
        "the player are not climbing"
    })
    void test(String input) {
        assertInstanceOf(PsiIsClimbingCondition.class, loader.tryParseElement(input, -1));
    }
}
