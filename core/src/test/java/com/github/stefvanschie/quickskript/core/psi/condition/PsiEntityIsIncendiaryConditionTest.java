package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiEntityIsIncendiaryConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "player is incendiary",
        "player are incendiary",
        "player causes an incendiary explosion",
        "player causes an fiery explosion",
        "player causes a incendiary explosion",
        "player causes a fiery explosion",
        "player cause an incendiary explosion",
        "player cause an fiery explosion",
        "player cause a incendiary explosion",
        "player cause a fiery explosion",
        "player is not incendiary",
        "player are not incendiary",
        "player isn't incendiary",
        "player aren't incendiary",
        "player does not causes an incendiary explosion",
        "player does not causes an fiery explosion",
        "player does not causes a incendiary explosion",
        "player does not causes a fiery explosion",
        "player does not cause an incendiary explosion",
        "player does not cause an fiery explosion",
        "player does not cause a incendiary explosion",
        "player does not cause a fiery explosion",
        "player do not causes an incendiary explosion",
        "player do not causes an fiery explosion",
        "player do not causes a incendiary explosion",
        "player do not causes a fiery explosion",
        "player do not cause an incendiary explosion",
        "player do not cause an fiery explosion",
        "player do not cause a incendiary explosion",
        "player do not cause a fiery explosion",
        "player doesn't causes an incendiary explosion",
        "player doesn't causes an fiery explosion",
        "player doesn't causes a incendiary explosion",
        "player doesn't causes a fiery explosion",
        "player doesn't cause an incendiary explosion",
        "player doesn't cause an fiery explosion",
        "player doesn't cause a incendiary explosion",
        "player doesn't cause a fiery explosion",
        "player don't causes an incendiary explosion",
        "player don't causes an fiery explosion",
        "player don't causes a incendiary explosion",
        "player don't causes a fiery explosion",
        "player don't cause an incendiary explosion",
        "player don't cause an fiery explosion",
        "player don't cause a incendiary explosion",
        "player don't cause a fiery explosion"
    })
    void test(String input) {
        assertInstanceOf(PsiEntityIsIncendiaryCondition.class, loader.tryParseElement(input, -1));
    }
}
