package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiEventBlockExpressionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "the event-block");
        test(skriptLoader, "the block");
        test(skriptLoader, "event-block");
        test(skriptLoader, "block");
    }

    private void test(SkriptLoader loader, String input) {
        //specifically set Type.BLOCK, because "block" also matches a visual effect
        PsiElement<?> psiElement = loader.tryParseElement(input, Type.BLOCK, -1);

        assertTrue(psiElement instanceof PsiEventBlockExpression);
    }
}
