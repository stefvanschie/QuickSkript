package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiIsInLoveConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "player is in love state",
        "player is in love mode",
        "player is in love",
        "player is in loving state",
        "player is in loving mode",
        "player is in loving",
        "player are in love state",
        "player are in love mode",
        "player are in love",
        "player are in loving state",
        "player are in loving mode",
        "player are in loving",
        "player isn't in love state",
        "player isn't in love mode",
        "player isn't in love",
        "player isn't in loving state",
        "player isn't in loving mode",
        "player isn't in loving",
        "player is not in love state",
        "player is not in love mode",
        "player is not in love",
        "player is not in loving state",
        "player is not in loving mode",
        "player is not in loving",
        "player aren't in love state",
        "player aren't in love mode",
        "player aren't in love",
        "player aren't in loving state",
        "player aren't in loving mode",
        "player aren't in loving",
        "player are not in love state",
        "player are not in love mode",
        "player are not in love",
        "player are not in loving state",
        "player are not in loving mode",
        "player are not in loving"
    })
    void test(String input) {
        assertInstanceOf(PsiIsInLoveCondition.class, loader.tryParseElement(input, -1));
    }
}
