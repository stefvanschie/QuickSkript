package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PsiListExpressionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        String input = "0, 1, 9";

        PsiElement<?> psiElement = skriptLoader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiListExpression);

        Iterator<? extends Number> numbers = List.of(0, 1, 9).iterator();

        for (Object obj : psiElement.executeMulti(null, null)) {
            assertTrue(obj instanceof Number);

            assertTrue(numbers.hasNext());

            assertEquals(numbers.next().intValue(), ((Number) obj).intValue());
        }

        assertFalse(numbers.hasNext());
    }
}
