package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiChunkCoordinateIsLoadedConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "chunk at 0, 0 in world \"world\" is loaded",
        "chunk at 0, 0 in \"world\" is loaded",
        "chunk at 0, 0 of world \"world\" is loaded",
        "chunk at 0, 0 of \"world\" is loaded",
        "chunk 0, 0 in world \"world\" is loaded",
        "chunk 0, 0 in \"world\" is loaded",
        "chunk 0, 0 of world \"world\" is loaded",
        "chunk 0, 0 of \"world\" is loaded",
        "chunk at 0, 0 in world \"world\" isn't loaded",
        "chunk at 0, 0 in world \"world\" is not loaded",
        "chunk at 0, 0 in \"world\" isn't loaded",
        "chunk at 0, 0 in \"world\" is not loaded",
        "chunk at 0, 0 of world \"world\" isn't loaded",
        "chunk at 0, 0 of world \"world\" is not loaded",
        "chunk at 0, 0 of \"world\" isn't loaded",
        "chunk at 0, 0 of \"world\" is not loaded",
        "chunk 0, 0 in world \"world\" isn't loaded",
        "chunk 0, 0 in world \"world\" is not loaded",
        "chunk 0, 0 in \"world\" isn't loaded",
        "chunk 0, 0 in \"world\" is not loaded",
        "chunk 0, 0 of world \"world\" isn't loaded",
        "chunk 0, 0 of world \"world\" is not loaded",
        "chunk 0, 0 of \"world\" isn't loaded",
        "chunk 0, 0 of \"world\" is not loaded"
    })
    void test(String input) {
        assertInstanceOf(PsiChunkCoordinateIsLoadedCondition.class, loader.tryParseElement(input, -1));
    }
}
