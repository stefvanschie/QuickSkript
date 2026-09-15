package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiIsRiptidingConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "player is riptiding",
        "player are riptiding",
        "player isn't riptiding",
        "player is not riptiding",
        "player aren't riptiding",
        "player are not riptiding"
    })
    void test(String input) {
        assertInstanceOf(PsiIsRiptidingCondition.class, loader.tryParseElement(input, -1));
    }
}
