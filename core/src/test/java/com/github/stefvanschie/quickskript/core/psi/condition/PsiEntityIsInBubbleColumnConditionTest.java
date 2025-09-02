package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiEntityIsInBubbleColumnConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "the player is in a bubble column",
        "the player is in a bubblecolumn",
        "the player is in bubble column",
        "the player is in bubblecolumn",
        "the player are in a bubble column",
        "the player are in a bubblecolumn",
        "the player are in bubble column",
        "the player are in bubblecolumn",
        "the player isn't in a bubble column",
        "the player isn't in a bubblecolumn",
        "the player isn't in bubble column",
        "the player isn't in bubblecolumn",
        "the player is not in a bubble column",
        "the player is not in a bubblecolumn",
        "the player is not in bubble column",
        "the player is not in bubblecolumn",
        "the player aren't in a bubble column",
        "the player aren't in a bubblecolumn",
        "the player aren't in bubble column",
        "the player aren't in bubblecolumn",
        "the player are not in a bubble column",
        "the player are not in a bubblecolumn",
        "the player are not in bubble column",
        "the player are not in bubblecolumn"
    })
    void test(String input) {
        assertInstanceOf(PsiEntityIsInBubbleColumnCondition.class, loader.tryParseElement(input, -1));
    }
}
