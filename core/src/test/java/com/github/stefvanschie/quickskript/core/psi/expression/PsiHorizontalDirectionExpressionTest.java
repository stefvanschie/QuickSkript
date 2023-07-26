package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import com.github.stefvanschie.quickskript.core.util.literal.direction.HorizontalRelativeDirection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiHorizontalDirectionExpressionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "9 horizontal to the left", new HorizontalRelativeDirection(-90, 9));
        test(skriptLoader, "horizontally in front", new HorizontalRelativeDirection(0, 1));
    }

    private void test(SkriptLoader loader, String input, Object result) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiHorizontalDirectionExpression);
        assertEquals(result, psiElement.execute(null, null));
    }
}
