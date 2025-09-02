package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PsiIsItemEmptyConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "0 of enchanted book is empty",
        "0 of painting are empty",
        "4 of redstone lamp isn't empty",
        "1 of tadpole bucket is not empty",
        "1 of diamond boots aren't empty",
        "5 of tropical fish spawn egg are not empty"
    })
    void test(String input) {
        PsiElement<?> element = loader.tryParseElement(input, -1);

        assertNotNull(element);
        assertInstanceOf(PsiIsItemEmptyCondition.class, element);
        assertTrue(element.isPreComputed());
        assertEquals(true, element.execute(null, null));
    }
}
