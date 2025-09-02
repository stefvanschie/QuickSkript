package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiHasAIConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "the player has ai",
        "the player has artificial intelligence",
        "the player have ai",
        "the player have artificial intelligence",
        "the player doesn't have ai",
        "the player doesn't have artificial intelligence",
        "the player does not have ai",
        "the player does not have artificial intelligence",
        "the player do not have ai",
        "the player do not have artificial intelligence",
        "the player don't have ai",
        "the player don't have artificial intelligence"
    })
    void test(String input) {
        assertInstanceOf(PsiHasAICondition.class, loader.tryParseElement(input, -1));
    }
}
