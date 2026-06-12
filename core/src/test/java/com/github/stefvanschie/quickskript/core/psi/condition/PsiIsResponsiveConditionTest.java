package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiIsResponsiveConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "player is responsive",
        "player are responsive",
        "player isn't unresponsive",
        "player is not unresponsive",
        "player aren't unresponsive",
        "player are not unresponsive",
        "player is unresponsive",
        "player are unresponsive",
        "player isn't responsive",
        "player is not responsive",
        "player aren't responsive",
        "player are not responsive",
    })
    void test(String input) {
        assertInstanceOf(PsiIsResponsiveCondition.class, loader.tryParseElement(input, -1));
    }
}
