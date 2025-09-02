package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class PsiIsAlphanumericConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        "abc123" is alphanumeric   ; true
        "!@#" is alphanumeric      ; false
        "abc123" isn't alphanumeric; false
        "!@#" isn't alphanumeric   ; true
        """, delimiter = ';')
    void test(String input, boolean result) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertNotNull(psiElement);
        assertInstanceOf(PsiIsAlphanumericCondition.class, psiElement);
        assertEquals(result, psiElement.execute(null, null));
    }
}
