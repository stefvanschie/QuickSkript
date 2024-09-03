package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PsiContainsItemTypeConditionTest {

    @Test
    void test() {
        var skriptLoader = new StandaloneSkriptLoader();

        test(skriptLoader, "clicked inventory has painting item in their inventory");
        test(skriptLoader, "clicked inventory has enchanted book in the inventory");
        test(skriptLoader, "clicked inventory has gold silence netherite cap in his inventory");
        test(skriptLoader, "clicked inventory has tripwire hook in her inventory");
        test(skriptLoader, "clicked inventory has pumpkin seed in its inventory");
        test(skriptLoader, "clicked inventory has long leaping potion in inventory");
        test(skriptLoader, "clicked inventory has tadpole bucket");
        test(skriptLoader, "clicked inventory have strong leaping tipped arrow in their inventory");
        test(skriptLoader, "clicked inventory have mud brick wall in the inventory");
        test(skriptLoader, "clicked inventory have snowball in his inventory");
        test(skriptLoader, "clicked inventory have stripped warped hyphae in her inventory");
        test(skriptLoader, "clicked inventory have sea pickle in its inventory");
        test(skriptLoader, "clicked inventory have painting");

        test(skriptLoader, "clicked inventory contains end portal frame item");
        test(skriptLoader, "clicked inventory contain spawn witches");

        test(skriptLoader, "clicked inventory doesn't have enchanted book in their inventory");
        test(skriptLoader, "clicked inventory doesn't have snowball in the inventory");
        test(skriptLoader, "clicked inventory doesn't have player head item in his inventory");
        test(skriptLoader, "clicked inventory doesn't have long turtle master tipped arrow in her inventory");
        test(skriptLoader, "clicked inventory doesn't have painting in its inventory");
        test(skriptLoader, "clicked inventory doesn't have cyan dye in inventory");
        test(skriptLoader, "clicked inventory doesn't have jungle sapling");
        test(skriptLoader, "clicked inventory does not have sunflower in their inventory");
        test(skriptLoader, "clicked inventory does not have painting item in the inventory");
        test(skriptLoader, "clicked inventory does not have painting in his inventory");
        test(skriptLoader, "clicked inventory does not have water bucket in her inventory");
        test(skriptLoader, "clicked inventory does not have raw granite in its inventory");
        test(skriptLoader, "clicked inventory does not have jungle gate in inventory");
        test(skriptLoader, "clicked inventory does not have ravager spawn egg");
        test(skriptLoader, "clicked inventory do not have blue candle in their inventory");
        test(skriptLoader, "clicked inventory do not have detector minecart rail item in the inventory");
        test(skriptLoader, "clicked inventory do not have prize pottery sherd in his inventory");
        test(skriptLoader, "clicked inventory do not have tipped arrow of mundane in her inventory");
        test(skriptLoader, "clicked inventory do not have snort pottery sherd in its inventory");
        test(skriptLoader, "clicked inventory do not have podzol in inventory");
        test(skriptLoader, "clicked inventory do not have painting");
        test(skriptLoader, "clicked inventory don't have activator minecart track item in their inventory");
        test(skriptLoader, "clicked inventory don't have book shelf in the inventory");
        test(skriptLoader, "clicked inventory don't have writable book in his inventory");
        test(skriptLoader, "clicked inventory don't have spyglass in her inventory");
        test(skriptLoader, "clicked inventory don't have bookshelf in its inventory");
        test(skriptLoader, "clicked inventory don't have enchanted book in inventory");
        test(skriptLoader, "clicked inventory don't have beehive");

        test(skriptLoader, "clicked inventory doesn't contain peony");
        test(skriptLoader, "clicked inventory does not contain extended weakening potion");
        test(skriptLoader, "clicked inventory do not contain normal red sandstone");
        test(skriptLoader, "clicked inventory don't contain husk spawn egg");
    }

    private void test(SkriptLoader loader, String input) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiContainsItemTypeCondition);
    }
}
