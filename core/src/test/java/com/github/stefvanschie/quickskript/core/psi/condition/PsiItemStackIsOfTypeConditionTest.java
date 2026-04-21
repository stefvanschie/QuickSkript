package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PsiItemStackIsOfTypeConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "piglin brute spawn egg is of types carrot",
        "music disc precipice is of type dark oak slab",
        "cod spawn egg are of types piglin spawn egg",
        "beetroot seeds are of type stone button",
        "writable book isn't of types mangrove chest boat",
        "oak fence isn't of type black wall banner",
        "bamboo fence gate is not of types weathered copper golem statue",
        "wheat seeds is not of type deepslate brick wall",
        "cocoa beans aren't of types cod spawn egg",
        "chicken aren't of type golden hoe",
        "tube coral wall fan are not of types brown egg",
        "music disc 13 are not of type spruce button"
    })
    void test(String input) {
        assertInstanceOf(PsiItemStackIsOfTypeCondition.class, loader.tryParseElement(input, -1));
    }
}
