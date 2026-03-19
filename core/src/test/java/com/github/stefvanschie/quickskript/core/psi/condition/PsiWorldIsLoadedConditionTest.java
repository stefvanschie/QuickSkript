package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiWorldIsLoadedConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "worlds world(\"world\") is loaded",
        "worlds world(\"world\") are loaded",
        "world world(\"world\") is loaded",
        "world world(\"world\") are loaded",
        "world(\"world\") is loaded",
        "world(\"world\") are loaded",
        "worlds world(\"world\") isn't loaded",
        "worlds world(\"world\") is not loaded",
        "worlds world(\"world\") aren't loaded",
        "worlds world(\"world\") are not loaded",
        "world world(\"world\") isn't loaded",
        "world world(\"world\") is not loaded",
        "world world(\"world\") aren't loaded",
        "world world(\"world\") are not loaded",
        "world(\"world\") isn't loaded",
        "world(\"world\") is not loaded",
        "world(\"world\") aren't loaded",
        "world(\"world\") are not loaded"
    })
    void test(String input) {
        assertInstanceOf(PsiWorldIsLoadedCondition.class, loader.tryParseElement(input, -1));
    }
}
