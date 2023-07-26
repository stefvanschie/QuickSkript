package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import com.github.stefvanschie.quickskript.core.util.literal.direction.RelativeDirection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiDirectionExpressionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "4 metre forwards", new RelativeDirection(0, 0, 4));
        test(skriptLoader, "backwards", new RelativeDirection(180, 0, 1));
    }

    private void test(SkriptLoader loader, String input, Object result) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiDirectionExpression);
        assertEquals(result, psiElement.execute(null, null));
    }
}
