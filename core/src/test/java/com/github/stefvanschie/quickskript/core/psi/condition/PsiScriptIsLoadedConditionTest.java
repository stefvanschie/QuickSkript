package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiScriptIsLoadedConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "scripts script is loaded",
        "scripts script are loaded",
        "script script is loaded",
        "script script are loaded",
        "script is loaded",
        "script are loaded",
        "scripts script isn't loaded",
        "scripts script is not loaded",
        "scripts script aren't loaded",
        "scripts script are not loaded",
        "script script isn't loaded",
        "script script is not loaded",
        "script script aren't loaded",
        "script script are not loaded",
        "script isn't loaded",
        "script is not loaded",
        "script aren't loaded",
        "script are not loaded"
    })
    void test(String input) {
        assertInstanceOf(PsiScriptIsLoadedCondition.class, loader.tryParseElement(input, -1));
    }
}
