package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiIsChargedConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "affected entities is charged",
        "affected entities is powered",
        "affected entities are powered",
        "affected entities are charged",
        "affected entities isn't charged",
        "affected entities isn't powered",
        "affected entities is not charged",
        "affected entities is not powered",
        "affected entities aren't charged",
        "affected entities aren't powered",
        "affected entities are not charged",
        "affected entities are not powered"
    })
    void test(String input) {
        assertInstanceOf(PsiIsChargedCondition.class, loader.tryParseElement(input, -1));
    }
}
