package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiEntityIsShearedConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "the player is sheared",
        "the player is shorn",
        "the player are sheared",
        "the player are shorn",
        "the player isn't sheared",
        "the player isn't shorn",
        "the player is not sheared",
        "the player is not shorn",
        "the player aren't sheared",
        "the player aren't shorn",
        "the player are not sheared",
        "the player are not shorn"
    })
    void test(String input) {
        assertInstanceOf(PsiEntityIsShearedCondition.class, loader.tryParseElement(input, -1));
    }
}
