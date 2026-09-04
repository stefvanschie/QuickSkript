package com.github.stefvanschie.quickskript.core.psi.entitydata;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import com.github.stefvanschie.quickskript.core.util.registry.TypeRegistry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

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
        TypeRegistry.Entry entityData = loader.getTypeRegistry().byName("entity data");

        assertNotNull(entityData);

        PsiElement<?> psiElement = loader.tryParseElement(input, entityData, -1);

        assertInstanceOf(PsiBeeEntityData.class, psiElement);
    }
}
