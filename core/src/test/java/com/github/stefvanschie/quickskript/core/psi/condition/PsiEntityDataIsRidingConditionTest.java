package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiEntityDataIsRidingConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "player is riding bee",
        "player are riding bee",
        "player isn't riding bee",
        "player is not riding bee",
        "player aren't riding bee",
        "player are not riding bee"
    })
    void test(String input) {
        assertInstanceOf(PsiEntityDataIsRidingCondition.class, loader.tryParseElement(input, -1));
    }
}
