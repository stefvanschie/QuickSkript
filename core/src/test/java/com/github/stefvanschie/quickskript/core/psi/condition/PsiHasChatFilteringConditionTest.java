package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiHasChatFilteringConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void test() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "the player has chat filtering on",
        "the player has chat filtering enabled",
        "the player has text filtering on",
        "the player has text filtering enabled",
        "the player have chat filtering on",
        "the player have chat filtering enabled",
        "the player have text filtering on",
        "the player have text filtering enabled",
        "the player doesn't have chat filtering on",
        "the player doesn't have chat filtering enabled",
        "the player doesn't have text filtering on",
        "the player doesn't have text filtering enabled",
        "the player does not have chat filtering on",
        "the player does not have chat filtering enabled",
        "the player does not have text filtering on",
        "the player does not have text filtering enabled",
        "the player do not have chat filtering on",
        "the player do not have chat filtering enabled",
        "the player do not have text filtering on",
        "the player do not have text filtering enabled",
        "the player don't have chat filtering on",
        "the player don't have chat filtering enabled",
        "the player don't have text filtering on",
        "the player don't have text filtering enabled"
    })
    void test(String input) {
        assertInstanceOf(PsiHasChatFilteringCondition.class, loader.tryParseElement(input, -1));
    }
}
