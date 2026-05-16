package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiIsPathfindingConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "player is pathfinding towards player",
        "player is pathfinding to player",
        "player is pathfinding",
        "player are pathfinding towards player",
        "player are pathfinding to player",
        "player are pathfinding",
        "player isn't pathfinding towards player",
        "player isn't pathfinding to player",
        "player isn't pathfinding",
        "player is not pathfinding towards player",
        "player is not pathfinding to player",
        "player is not pathfinding",
        "player aren't pathfinding towards player",
        "player aren't pathfinding to player",
        "player aren't pathfinding",
        "player are not pathfinding towards player",
        "player are not pathfinding to player",
        "player are not pathfinding"
    })
    void test(String input) {
        assertInstanceOf(PsiIsPathfindingCondition.class, loader.tryParseElement(input, -1));
    }
}
