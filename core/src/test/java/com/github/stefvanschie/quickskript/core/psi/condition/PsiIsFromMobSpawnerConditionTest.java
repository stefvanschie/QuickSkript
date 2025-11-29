package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiIsFromMobSpawnerConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "player is from a mob spawner",
        "player is from a spawner",
        "player are from a mob spawner",
        "player are from a spawner",
        "player was spawned from a mob spawner",
        "player was spawned from a spawner",
        "player was spawned by a mob spawner",
        "player was spawned by a spawner",
        "player were spawned from a mob spawner",
        "player were spawned from a spawner",
        "player were spawned by a mob spawner",
        "player were spawned by a spawner",
        "player isn't from a mob spawner",
        "player isn't from a spawner",
        "player aren't from a mob spawner",
        "player aren't from a spawner",
        "player is not from a mob spawner",
        "player is not from a spawner",
        "player are not from a mob spawner",
        "player are not from a spawner",
        "player wasn't spawned from a mob spawner",
        "player wasn't spawned from a spawner",
        "player wasn't spawned by a mob spawner",
        "player wasn't spawned by a spawner",
        "player weren't spawned from a mob spawner",
        "player weren't spawned from a spawner",
        "player weren't spawned by a mob spawner",
        "player weren't spawned by a spawner",
        "player was not spawned from a mob spawner",
        "player was not spawned from a spawner",
        "player was not spawned by a mob spawner",
        "player was not spawned by a spawner",
        "player were not spawned from a mob spawner",
        "player were not spawned from a spawner",
        "player were not spawned by a mob spawner",
        "player were not spawned by a spawner",
    })
    void test(String input) {
        assertInstanceOf(PsiIsFromMobSpawnerCondition.class, loader.tryParseElement(input, -1));
    }
}
