package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiIsOccludingConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "nautilus spawn egg is occluding",
        "leather are occluding",
        "polished diorite stairs isn't occluding",
        "gray bundle is not occluding",
        "sandstone stairs aren't occluding",
        "stone brick stairs are not occluding"
    })
    void test(String input) {
        assertInstanceOf(PsiIsOccludingCondition.class, loader.tryParseElement(input, -1));
    }
}
