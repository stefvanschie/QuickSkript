package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import com.github.stefvanschie.quickskript.core.util.literal.Location;
import com.github.stefvanschie.quickskript.core.util.literal.World;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiLocationAtExpressionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        String input = "the position at (2, 5, 4 \"world\"";

        PsiElement<?> psiElement = skriptLoader.tryParseElement(input, -1);

        Location expected = new Location(2, 5, 4, new World("world"));

        assertTrue(psiElement instanceof PsiLocationAtExpression);
        assertEquals(expected, psiElement.execute(null, null));
    }
}
