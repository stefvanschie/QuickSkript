package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiEntityIsInRainConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "the player is in rain",
        "the player are in rain",
        "the player isn't in rain",
        "the player is not in rain",
        "the player aren't in rain",
        "the player are not in rain"
    })
    void test(String input) {
        assertInstanceOf(PsiEntityIsInRainCondition.class, loader.tryParseElement(input, -1));
    }
}
