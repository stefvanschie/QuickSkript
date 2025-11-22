package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiIsFlammableConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "copper axe is flammable",
        "egg are flammable",
        "amethyst shard isn't flammable",
        "diamond sword is not flammable",
        "exposed cut copper stairs aren't flammable",
        "spore blossom are not flammable"
    })
    void test(String input) {
        assertInstanceOf(PsiIsFlammableCondition.class, loader.tryParseElement(input, -1));
    }
}
