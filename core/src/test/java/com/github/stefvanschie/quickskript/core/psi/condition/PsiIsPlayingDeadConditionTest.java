package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiIsPlayingDeadConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "player is playing dead",
        "player are playing dead",
        "player isn't playing dead",
        "player is not playing dead",
        "player aren't playing dead",
        "player are not playing dead"
    })
    void test(String input) {
        assertInstanceOf(PsiIsPlayingDeadCondition.class, loader.tryParseElement(input, -1));
    }
}
