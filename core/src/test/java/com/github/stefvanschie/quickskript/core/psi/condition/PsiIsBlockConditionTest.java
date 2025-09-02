package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiIsBlockConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "light gray concrete is a block",
        "purple candle cake is block",
        "stone shovels is blocks",
        "ravager spawn egg are a block",
        "pumpkin seeds are block",
        "carrot are blocks",
        "dead bubble coral wall fan isn't a block",
        "horse spawn egg isn't block",
        "golden carrot isn't blocks",
        "bamboo fence gates is not a block",
        "splash potion is not block",
        "netherite leggings is not blocks",
        "nether brick wall aren't a block",
        "spyglasss aren't block",
        "arrow aren't blocks",
        "rotten flesh are not a block",
        "pufferfish are not block",
        "magenta wool are not blocks"
    })
    void test(String input) {
        assertInstanceOf(PsiIsBlockCondition.class, loader.tryParseElement(input, -1));
    }
}
