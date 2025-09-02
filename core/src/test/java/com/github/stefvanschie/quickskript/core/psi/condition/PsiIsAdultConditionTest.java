package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiIsAdultConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void test() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "the player is an adult",
        "the player is adult",
        "the player are an adult",
        "the player are adult",
        "the player isn't an adult",
        "the player isn't adult",
        "the player is not an adult",
        "the player is not adult",
        "the player aren't an adult",
        "the player aren't adult",
        "the player are not an adult",
        "the player are not adult"
    })
    void test(String input) {
        assertInstanceOf(PsiIsAdultCondition.class, loader.tryParseElement(input, -1));
    }
}
