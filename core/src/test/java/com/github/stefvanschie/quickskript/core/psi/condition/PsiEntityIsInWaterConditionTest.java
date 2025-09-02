package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiEntityIsInWaterConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void test() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "the player is in water",
        "the player are in water",
        "the player isn't in water",
        "the player is not in water",
        "the player aren't in water",
        "the player are not in water"
    })
    void test(String input) {
        assertInstanceOf(PsiEntityIsInWaterCondition.class, loader.tryParseElement(input, -1));
    }
}
