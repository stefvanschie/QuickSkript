package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import com.github.stefvanschie.quickskript.core.util.literal.InputKey;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class PsiInputKeyLiteralTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @CsvSource({
        "backward movement key, BACKWARD",
        "backward key,          BACKWARD",
        "forward movement key,  FORWARD",
        "forward key,           FORWARD",
        "jumping key,           JUMP",
        "jump key,              JUMP",
        "left movement key,     LEFT",
        "left key,              LEFT",
        "right movement key,    RIGHT",
        "right key,             RIGHT",
        "sneaking key,          SNEAK",
        "sneak key,             SNEAK",
        "sprinting key,         SPRINT",
        "sprint key,            SPRINT"
    })
    void test(String input, InputKey result) {
        PsiElement<?> element = loader.tryParseElement(input, -1);

        assertInstanceOf(PsiInputKeyLiteral.class, element);
        assertTrue(element.isPreComputed());
        assertEquals(result, element.execute(null, null));
    }
}
