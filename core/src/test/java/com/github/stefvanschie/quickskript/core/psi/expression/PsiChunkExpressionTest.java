package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiChunkExpressionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "all of the chunks of location(world(\"world\"), 0, 0, 0)",
        "all of the chunks at location(world(\"world\"), 0, 0, 0)",
        "all of the chunk of location(world(\"world\"), 0, 0, 0)",
        "all of the chunk at location(world(\"world\"), 0, 0, 0)",
        "all the chunks of location(world(\"world\"), 0, 0, 0)",
        "all the chunks at location(world(\"world\"), 0, 0, 0)",
        "all the chunk of location(world(\"world\"), 0, 0, 0)",
        "all the chunk at location(world(\"world\"), 0, 0, 0)",
        "all chunks of location(world(\"world\"), 0, 0, 0)",
        "all chunks at location(world(\"world\"), 0, 0, 0)",
        "all chunk of location(world(\"world\"), 0, 0, 0)",
        "all chunk at location(world(\"world\"), 0, 0, 0)",
        "the chunks of location(world(\"world\"), 0, 0, 0)",
        "the chunks at location(world(\"world\"), 0, 0, 0)",
        "the chunk of location(world(\"world\"), 0, 0, 0)",
        "the chunk at location(world(\"world\"), 0, 0, 0)",
        "chunks of location(world(\"world\"), 0, 0, 0)",
        "chunks at location(world(\"world\"), 0, 0, 0)",
        "chunk of location(world(\"world\"), 0, 0, 0)",
        "chunk at location(world(\"world\"), 0, 0, 0)",
        "location(world(\"world\"), 0, 0, 0)'s chunks",
        "location(world(\"world\"), 0, 0, 0)'s chunk",
        "location(world(\"world\"), 0, 0, 0)' chunks",
        "location(world(\"world\"), 0, 0, 0)' chunk",
    })
    void test(String input) {
        assertInstanceOf(PsiChunkExpression.class, loader.tryParseElement(input, -1));
    }
}
