package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiFishingHookExpressionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "the fishing hook",
        "the fishing bobber",
        "the fish hook",
        "the fish bobber",
        "fishing hook",
        "fishing bobber",
        "fish hook",
        "fish bobber"
    })
    void test(String input) {
        assertInstanceOf(PsiFishingHookExpression.class, loader.tryParseElement(input, -1));
    }
}
