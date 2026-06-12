package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiIsPressingKeyConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "player is pressing jump key",
        "player are pressing backward movement key",
        "player was pressing sneaking key",
        "player were pressing left movement key",
        "player isn't pressing sneak key",
        "player is not pressing jump key",
        "player aren't pressing forward movement key",
        "player are not pressing right key",
        "player wasn't pressing forward key",
        "player was not pressing forward movement key",
        "player weren't pressing forward key",
        "player were not pressing jump key"
    })
    void test(String input) {
        assertInstanceOf(PsiIsPressingKeyCondition.class, loader.tryParseElement(input, -1));
    }
}
