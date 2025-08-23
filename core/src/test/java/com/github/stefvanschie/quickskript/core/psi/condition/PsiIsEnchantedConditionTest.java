package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiIsEnchantedConditionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "flint is enchanted", false);
        test(skriptLoader, "iron pickaxe are enchanted", false);

        test(skriptLoader, "sheep spawn egg is enchanted with looting", false);
        test(skriptLoader, "basalt are enchanted with binding_curse", false);

        test(skriptLoader, "potions of knockback isn't enchanted", false);
        test(skriptLoader, "potted jungle sapling of bane_of_arthropods is not enchanted", false);
        test(skriptLoader, "birch planks of fire_aspect aren't enchanted", false);
        test(skriptLoader, "leather horse armor of depth_strider are not enchanted", false);

        test(skriptLoader, "cyan dye of binding_curse isn't enchanted with binding_curse", false);
        test(skriptLoader, "brush of lure is not enchanted with lure", false);
        test(skriptLoader, "end stone brick stairs of unbreaking aren't enchanted with unbreaking", false);
        test(skriptLoader, "netherite boots of unbreaking are not enchanted with unbreaking", false);

        test(skriptLoader, "paper of power is enchanted", true);
        test(skriptLoader, "creeper head of knockback are enchanted", true);

        test(skriptLoader, "netherite ingot of luck_of_the_sea is enchanted with luck_of_the_sea", true);
        test(skriptLoader, "slime spawn egg of smite are enchanted with smite", true);

        test(skriptLoader, "zombie spawn egg isn't enchanted", true);
        test(skriptLoader, "bone is not enchanted", true);
        test(skriptLoader, "waxed oxidized cut copper slab aren't enchanted", true);
        test(skriptLoader, "cave vines are not enchanted", true);

        test(skriptLoader, "stone brick slab isn't enchanted with infinity", true);
        test(skriptLoader, "granite wall is not enchanted with frost_walker", true);
        test(skriptLoader, "bamboo door aren't enchanted with power", true);
        test(skriptLoader, "pale oak chest boat are not enchanted with protection", true);
    }

    private void test(SkriptLoader loader, String input, boolean output) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiIsEnchantedCondition);
        assertEquals(output, psiElement.execute(null, null));
    }
}
