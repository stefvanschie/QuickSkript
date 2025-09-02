package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class PsiContainsObjectConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        6, 8 contains 6, and 6              ; true
        3, 2 contain 1, or 4                ; false
        5 doesn't contain 5, 3, and 7       ; true
        4, 6, 7 does not contain 7, 7, or 10; false
        8, 10, 1 do not contain 1, and 7    ; true
        7, 7, 9 don't contain 6, or 7       ; false
        """, delimiter = ';')
    void test(String input, boolean result) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertNotNull(psiElement);
        assertInstanceOf(PsiContainsObjectCondition.class, psiElement);
        assertEquals(result, psiElement.execute(null, null));
    }
}
