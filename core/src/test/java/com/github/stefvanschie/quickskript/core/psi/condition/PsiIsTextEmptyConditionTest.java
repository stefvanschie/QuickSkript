package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PsiIsTextEmptyConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "\"\" is empty",
        "\"\" are empty",
        "\"a\" isn't empty",
        "\"b\" is not empty",
        "\"c\" aren't empty",
        "\"d\" are not empty"
    })
    void test(String input) {
        PsiElement<?> element = loader.tryParseElement(input, -1);

        assertNotNull(element);
        assertInstanceOf(PsiIsTextEmptyCondition.class, element);
        assertTrue(element.isPreComputed());
        assertTrue(element.execute(null, null, Boolean.class));
    }
}
