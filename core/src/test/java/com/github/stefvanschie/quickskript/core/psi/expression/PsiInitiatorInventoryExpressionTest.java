package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiInitiatorInventoryExpressionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "initiator",
        "the initiator",
        "event-initiator",
        "the event-initiator",
        "initiator inventory",
        "the initiator inventory",
        "event-initiator inventory",
        "the event-initiator inventory",
        "initiator-inventory",
        "the initiator-inventory",
        "event-initiator-inventory",
        "the event-initiator-inventory"
    })
    void test(String input) {
        assertInstanceOf(PsiInitiatorInventoryExpression.class, loader.tryParseElement(input, -1));
    }
}
