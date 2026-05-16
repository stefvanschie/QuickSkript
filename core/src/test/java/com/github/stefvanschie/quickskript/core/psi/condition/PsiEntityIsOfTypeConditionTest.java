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
        "player is of types warden",
        "player is of type squid",
        "player are of types cod",
        "player are of type donkey",
        "player isn't of types skeleton horse",
        "player isn't of type minecart",
        "player is not of types firework",
        "player is not of type spider",
        "player aren't of types skeleton",
        "player aren't of type mule",
        "player are not of types tropical fish",
        "player are not of type illusioner"
    })
    void test(String input) {
        assertInstanceOf(PsiEntityIsOfTypeCondition.class, loader.tryParseElement(input, -1));
    }
}
