package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiInitiatorInventoryExpressionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "initiator");
        test(skriptLoader, "the initiator");
        test(skriptLoader, "event-initiator");
        test(skriptLoader, "the event-initiator");

        test(skriptLoader, "initiator inventory");
        test(skriptLoader, "the initiator inventory");
        test(skriptLoader, "event-initiator inventory");
        test(skriptLoader, "the event-initiator inventory");

        test(skriptLoader, "initiator-inventory");
        test(skriptLoader, "the initiator-inventory");
        test(skriptLoader, "event-initiator-inventory");
        test(skriptLoader, "the event-initiator-inventory");
    }

    private void test(SkriptLoader loader, String input) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiInitiatorInventoryExpression);
    }
}
