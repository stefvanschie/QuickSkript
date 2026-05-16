package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import com.github.stefvanschie.quickskript.core.util.registry.TypeRegistry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PsiEventBlockExpressionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "the event-block",
        "the block",
        "event-block",
        "block"
    })
    void test(String input) {
        TypeRegistry.Entry type = loader.getTypeRegistry().byName("block");

        assertNotNull(type);

        //specifically set type, because "block" also matches a visual effect
        assertInstanceOf(PsiEventBlockExpression.class, loader.tryParseElement(input, type, -1));
    }
}
