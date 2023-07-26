package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import com.github.stefvanschie.quickskript.core.util.literal.direction.AbsoluteDirection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiAbsoluteDirectionExpressionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "eastward of", new AbsoluteDirection(1, 0, 0));
        test(skriptLoader, "south over 6 block downward", new AbsoluteDirection(0, -5, 1));
        test(skriptLoader, "2 over", new AbsoluteDirection(0, 2, 0));
        test(skriptLoader, "8 beneath over", new AbsoluteDirection(0, -7, 0));
    }

    private void test(SkriptLoader loader, String input, Object result) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiAbsoluteDirectionExpression);
        assertEquals(result, psiElement.execute(null, null));
    }
}
