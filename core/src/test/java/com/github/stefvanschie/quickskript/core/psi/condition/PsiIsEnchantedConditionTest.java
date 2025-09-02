package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class PsiIsEnchantedConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void init() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        flint is enchanted                                                   ; false
        iron pickaxe are enchanted                                           ; false
        sheep spawn egg is enchanted with looting                            ; false
        basalt are enchanted with binding_curse                              ; false
        potions of knockback isn't enchanted                                 ; false
        potted jungle sapling of bane_of_arthropods is not enchanted         ; false
        birch planks of fire_aspect aren't enchanted                         ; false
        leather horse armor of depth_strider are not enchanted               ; false
        cyan dye of binding_curse isn't enchanted with binding_curse         ; false
        brush of lure is not enchanted with lure                             ; false
        end stone brick stairs of unbreaking aren't enchanted with unbreaking; false
        netherite boots of unbreaking are not enchanted with unbreaking      ; false
        paper of power is enchanted                                          ; true
        creeper head of knockback are enchanted                              ; true
        netherite ingot of luck_of_the_sea is enchanted with luck_of_the_sea ; true
        slime spawn egg of smite are enchanted with smite                    ; true
        zombie spawn egg isn't enchanted                                     ; true
        bone is not enchanted                                                ; true
        waxed oxidized cut copper slab aren't enchanted                      ; true
        cave vines are not enchanted                                         ; true
        stone brick slab isn't enchanted with infinity                       ; true
        granite wall is not enchanted with frost_walker                      ; true
        bamboo door aren't enchanted with power                              ; true
        pale oak chest boat are not enchanted with protection                ; true
        """, delimiter = ';')
    void test(String input, boolean output) {
        PsiElement<?> element = loader.tryParseElement(input, -1);

        assertNotNull(element);
        assertInstanceOf(PsiIsEnchantedCondition.class, element);
        assertEquals(output, element.execute(null, null));
    }
}
