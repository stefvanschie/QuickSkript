package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import com.github.stefvanschie.quickskript.core.util.literal.direction.AbsoluteDirection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiAtExpressionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        PsiElement<?> psiElement = skriptLoader.tryParseElement("at", -1);

        assertTrue(psiElement instanceof PsiAtExpression);
        assertEquals(new AbsoluteDirection(0, 0, 0), psiElement.execute(null, null));
    }
}
