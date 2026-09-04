package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiEntityIsOfTypeConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "player is of types bee",
        "player is of type bee",
        "player are of types bee",
        "player are of type bee",
        "player isn't of types bee",
        "player isn't of type bee",
        "player is not of types bee",
        "player is not of type bee",
        "player aren't of types bee",
        "player aren't of type bee",
        "player are not of types bee",
        "player are not of type bee"
    })
    void test(String input) {
        assertInstanceOf(PsiEntityIsOfTypeCondition.class, loader.tryParseElement(input, -1));
    }
}
