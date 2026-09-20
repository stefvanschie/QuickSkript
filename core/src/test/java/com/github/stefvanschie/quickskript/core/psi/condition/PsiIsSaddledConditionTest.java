package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiIsSaddledConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "player is properly saddled",
        "player is saddled",
        "player are properly saddled",
        "player are saddled",
        "player isn't properly saddled",
        "player isn't saddled",
        "player is not properly saddled",
        "player is not saddled",
        "player aren't properly saddled",
        "player aren't saddled",
        "player are not properly saddled",
        "player are not saddled"
    })
    void test(String input) {
        assertInstanceOf(PsiIsSaddledCondition.class, loader.tryParseElement(input, -1));
    }
}
