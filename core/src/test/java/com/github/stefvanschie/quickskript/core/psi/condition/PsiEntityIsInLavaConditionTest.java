package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiEntityIsInLavaConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "the player is in lava",
        "the player are in lava",
        "the player isn't in lava",
        "the player is not in lava",
        "the player aren't in lava",
        "the player are not in lava"
    })
    void test(String input) {
        assertInstanceOf(PsiEntityIsInLavaCondition.class, loader.tryParseElement(input, -1));
    }
}
