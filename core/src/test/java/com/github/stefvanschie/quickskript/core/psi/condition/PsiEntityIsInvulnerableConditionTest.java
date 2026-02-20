package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiEntityIsInvulnerableConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "player is invulnerable",
        "player is invincible",
        "player are invulnerable",
        "player are invincible",
        "player isn't invulnerable",
        "player isn't invincible",
        "player is not invulnerable",
        "player is not invincible",
        "player aren't invulnerable",
        "player aren't invincible",
        "player are not invulnerable",
        "player are not invincible"
    })
    void test(String input) {
        assertInstanceOf(PsiEntityIsInvulnerableCondition.class, loader.tryParseElement(input, -1));
    }
}
