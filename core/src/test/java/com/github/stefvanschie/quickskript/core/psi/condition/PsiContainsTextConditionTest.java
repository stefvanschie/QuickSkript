package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class PsiContainsTextConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        "operation" contains "e"                                                          ; true
        "meaning", "storage", or "tea" contain "iq", "j", or "nn"                         ; false
        "attention" doesn't contain "z"                                                   ; true
        "coffee", "communication", and "atmosphere" does not contain "o", or "eg"         ; false
        "relationship", "comparison", or "law" do not contain "elation", "ison", and "law"; true
        "investment", and "loss" don't contain "s", "est", or "o"                         ; false
        """, delimiter = ';')
    void test(String input, boolean result) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertNotNull(psiElement);
        assertInstanceOf(PsiContainsTextCondition.class, psiElement);
        assertEquals(result, psiElement.execute(null, null));
    }
}
