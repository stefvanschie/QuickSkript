package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiIsBabyConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "the player is a child",
        "the player is a baby",
        "the player are a child",
        "the player are a baby",
        "the player isn't a child",
        "the player isn't a baby",
        "the player is not a child",
        "the player is not a baby",
        "the player aren't a child",
        "the player aren't a baby",
        "the player are not a child",
        "the player are not a baby"
    })
    void test(String input) {
        assertInstanceOf(PsiIsBabyCondition.class, loader.tryParseElement(input, -1));
    }
}
