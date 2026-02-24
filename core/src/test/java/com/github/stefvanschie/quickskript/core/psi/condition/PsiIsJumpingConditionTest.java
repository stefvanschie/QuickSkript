package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PsiIsJumpingConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "player is jumping",
        "player are jumping",
        "player isn't jumping",
        "player is not jumping",
        "player aren't jumping",
        "player are not jumping"
    })
    void test(String input) {
        assertInstanceOf(PsiIsJumpingCondition.class, loader.tryParseElement(input, -1));
    }
}
