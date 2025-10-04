package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class PsiIsEvenlyDivisibleByConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        48 is evenly divisible by 8 with a tolerance of 0.7      ; true
        62 is evenly divisible by 3 with a tolerance 0.8         ; false
        19 is evenly divisible by 6 with tolerance of 0.2        ; false
        75 is evenly divisible by 9 with tolerance 0.4           ; false
        25 is evenly divisible by 5                              ; true
        22 are evenly divisible by 8 with a tolerance of 0.2     ; false
        98 are evenly divisible by 4 with a tolerance 0.4        ; false
        43 are evenly divisible by 9 with tolerance of 0.1       ; false
        13 are evenly divisible by 9 with tolerance 0.2          ; false
        24 are evenly divisible by 9                             ; false
        44 can be evenly divided by 1 with a tolerance of 0.9    ; true
        76 can be evenly divided by 8 with a tolerance 0.3       ; false
        67 can be evenly divided by 9 with tolerance of 0.6      ; false
        22 can be evenly divided by 9 with tolerance 0.7         ; false
        49 can be evenly divided by 8                            ; false
        48 isn't evenly divisible by 7 with a tolerance of 0.5   ; true
        94 isn't evenly divisible by 7 with a tolerance 0.9      ; true
        71 isn't evenly divisible by 9 with tolerance of 0.3     ; true
        90 isn't evenly divisible by 7 with tolerance 0.9        ; true
        42 isn't evenly divisible by 1                           ; false
        96 is not evenly divisible by 2 with a tolerance of 0.5  ; false
        28 is not evenly divisible by 4 with a tolerance 0.1     ; false
        17 is not evenly divisible by 8 with tolerance of 0.3    ; true
        55 is not evenly divisible by 1 with tolerance 0.9       ; false
        20 is not evenly divisible by 8                          ; true
        26 aren't evenly divisible by 6 with a tolerance of 0.3  ; true
        51 aren't evenly divisible by 9 with a tolerance 0.9     ; true
        44 aren't evenly divisible by 5 with tolerance of 0.3    ; true
        17 aren't evenly divisible by 7 with tolerance 0.6       ; true
        85 aren't evenly divisible by 3                          ; true
        98 are not evenly divisible by 7 with a tolerance of 0.2 ; false
        84 are not evenly divisible by 4 with a tolerance 0.6    ; false
        32 are not evenly divisible by 1 with tolerance of 0.4   ; false
        75 are not evenly divisible by 5 with tolerance 0.1      ; false
        27 are not evenly divisible by 2                         ; true
        48 can't be evenly divided by 7 with a tolerance of 0.8  ; true
        56 can't be evenly divided by 3 with a tolerance 0.4     ; true
        96 can't be evenly divided by 4 with tolerance of 0.9    ; false
        62 can't be evenly divided by 5 with tolerance 0.4       ; true
        42 can't be evenly divided by 6                          ; false
        34 can not be evenly divided by 1 with a tolerance of 0.1; false
        45 can not be evenly divided by 2 with a tolerance 0.6   ; true
        71 can not be evenly divided by 2 with tolerance of 0.5  ; true
        74 can not be evenly divided by 3 with tolerance 0.6     ; true
        65 can not be evenly divided by 2                        ; true
        33 cannot be evenly divided by 5 with a tolerance of 0.9 ; true
        18 cannot be evenly divided by 9 with a tolerance 0.9    ; false
        98 cannot be evenly divided by 7 with tolerance of 0.1   ; false
        84 cannot be evenly divided by 7 with tolerance 0.5      ; false
        86 cannot be evenly divided by 3                         ; true
        """, delimiter = ';')
    void test(String input, boolean output) {
        PsiElement<?> element = loader.tryParseElement(input, -1);

        assertNotNull(element);
        assertInstanceOf(PsiIsEvenlyDivisibleByCondition.class, element);
        assertTrue(element.isPreComputed());
        assertEquals(output, element.execute(null, null));
    }
}
