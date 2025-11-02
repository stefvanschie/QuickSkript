package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiIsFireResistantConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "wolf armor is fire resistant",
        "bowl is resistant to fire",
        "donkey spawn egg are fire resistant",
        "purple banner are resistant to fire",
        "light gray harness isn't fire resistant",
        "cooked mutton isn't resistant to fire",
        "waxed oxidized cut copper slab is not fire resistant",
        "wall torch is not resistant to fire",
        "knowledge book aren't fire resistant",
        "cake aren't resistant to fire",
        "fishing rod are not fire resistant",
        "oak wall hanging sign are not resistant to fire"
    })
    void test(String input) {
        assertInstanceOf(PsiIsFireResistantCondition.class, loader.tryParseElement(input, -1));
    }
}
