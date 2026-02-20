package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class PsiGameModeIsInvulnerableConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @CsvSource({
        "survival is invulnerable,      false",
        "survival is invincible,        false",
        "survival are invulnerable,     false",
        "survival are invincible,       false",
        "survival isn't invulnerable,   true",
        "survival isn't invincible,     true",
        "survival is not invulnerable,  true",
        "survival is not invincible,    true",
        "survival aren't invulnerable,  true",
        "survival aren't invincible,    true",
        "survival are not invulnerable, true",
        "survival are not invincible,   true"
    })
    void test(String input, boolean result) {
        PsiElement<?> element = loader.tryParseElement(input, -1);

        assertInstanceOf(PsiGameModeIsInvulnerableCondition.class, element);

        assert element != null;

        assertTrue(element.isPreComputed());
        assertEquals(result, element.execute(null, null));
    }
}
