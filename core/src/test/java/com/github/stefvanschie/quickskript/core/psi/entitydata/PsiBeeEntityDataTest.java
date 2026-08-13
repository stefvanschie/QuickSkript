package com.github.stefvanschie.quickskript.core.psi.entitydata;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiBeeEntityDataTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "happy no nectar bee",
        "happy nectar bee",
        "happy bee",
        "angry no nectar bee",
        "angry nectar bee",
        "angry bee",
        "no nectar bee",
        "nectar bee",
        "bee"
    })
    void test(String input) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertInstanceOf(PsiBeeEntityData.class, psiElement);
        assertTrue(psiElement.isPreComputed());
    }
}
