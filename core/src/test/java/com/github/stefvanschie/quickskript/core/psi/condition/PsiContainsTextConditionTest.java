package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiContainsTextConditionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "\"operation\" contains \"e\"", true);
        test(skriptLoader, "\"meaning\", \"storage\", or \"tea\" contain \"iq\", \"j\", or \"nn\"", false);

        test(skriptLoader, "\"attention\" doesn't contain \"z\"", true);
        test(skriptLoader, "\"coffee\", \"communication\", and \"atmosphere\" does not contain \"o\", or \"eg\"", false);
        test(skriptLoader, "\"relationship\", \"comparison\", or \"law\" do not contain \"elation\", \"ison\", and \"law\"", true);
        test(skriptLoader, "\"investment\", and \"loss\" don't contain \"s\", \"est\", or \"o\"", false);
    }

    private void test(SkriptLoader loader, String input, boolean result) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiContainsTextCondition);
        assertEquals(result, psiElement.execute(null, null));
    }
}
