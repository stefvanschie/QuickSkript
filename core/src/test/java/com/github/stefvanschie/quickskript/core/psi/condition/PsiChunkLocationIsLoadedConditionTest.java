package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiChunkLocationIsLoadedConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "chunks at location 0, 0, 0 in \"world\" is loaded",
        "chunks at location 0, 0, 0 in \"world\" are loaded",
        "chunk at location 0, 0, 0 in \"world\" is loaded",
        "chunk at location 0, 0, 0 in \"world\" are loaded",
        "chunks at location 0, 0, 0 in \"world\" isn't loaded",
        "chunks at location 0, 0, 0 in \"world\" is not loaded",
        "chunks at location 0, 0, 0 in \"world\" aren't loaded",
        "chunks at location 0, 0, 0 in \"world\" are not loaded",
        "chunk at location 0, 0, 0 in \"world\" isn't loaded",
        "chunk at location 0, 0, 0 in \"world\" is not loaded",
        "chunk at location 0, 0, 0 in \"world\" aren't loaded",
        "chunk at location 0, 0, 0 in \"world\" are not loaded"
    })
    void test(String input) {
        assertInstanceOf(PsiChunkLocationIsLoadedCondition.class, loader.tryParseElement(input, -1));
    }
}
