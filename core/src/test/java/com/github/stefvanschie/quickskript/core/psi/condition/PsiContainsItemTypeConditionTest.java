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

        test(skriptLoader, "clicked inventory has painting in their inventory");
        test(skriptLoader, "clicked inventory has enchanted book in the inventory");
        test(skriptLoader, "clicked inventory has netherite helmet in his inventory");
        test(skriptLoader, "clicked inventory has tripwire hook in her inventory");
        test(skriptLoader, "clicked inventory has pumpkin seeds in its inventory");
        test(skriptLoader, "clicked inventory has potion in inventory");
        test(skriptLoader, "clicked inventory has tadpole bucket");
        test(skriptLoader, "clicked inventory have tipped arrow in their inventory");
        test(skriptLoader, "clicked inventory have mud brick wall in the inventory");
        test(skriptLoader, "clicked inventory have snowball in his inventory");
        test(skriptLoader, "clicked inventory have stripped warped hyphae in her inventory");
        test(skriptLoader, "clicked inventory have sea pickle in its inventory");
        test(skriptLoader, "clicked inventory have painting");

        test(skriptLoader, "clicked inventory contains end portal frame");
        test(skriptLoader, "clicked inventory contain witch spawn eggs");

        test(skriptLoader, "clicked inventory doesn't have enchanted book in their inventory");
        test(skriptLoader, "clicked inventory doesn't have snowball in the inventory");
        test(skriptLoader, "clicked inventory doesn't have player head in his inventory");
        test(skriptLoader, "clicked inventory doesn't have tipped arrow in her inventory");
        test(skriptLoader, "clicked inventory doesn't have painting in its inventory");
        test(skriptLoader, "clicked inventory doesn't have cyan dye in inventory");
        test(skriptLoader, "clicked inventory doesn't have jungle sapling");
        test(skriptLoader, "clicked inventory does not have sunflower in their inventory");
        test(skriptLoader, "clicked inventory does not have painting in the inventory");
        test(skriptLoader, "clicked inventory does not have painting in his inventory");
        test(skriptLoader, "clicked inventory does not have water bucket in her inventory");
        test(skriptLoader, "clicked inventory does not have granite in its inventory");
        test(skriptLoader, "clicked inventory does not have jungle fence gate in inventory");
        test(skriptLoader, "clicked inventory does not have ravager spawn egg");
        test(skriptLoader, "clicked inventory do not have blue candle in their inventory");
        test(skriptLoader, "clicked inventory do not have detector rail in the inventory");
        test(skriptLoader, "clicked inventory do not have prize pottery sherd in his inventory");
        test(skriptLoader, "clicked inventory do not have tipped arrow in her inventory");
        test(skriptLoader, "clicked inventory do not have snort pottery sherd in its inventory");
        test(skriptLoader, "clicked inventory do not have podzol in inventory");
        test(skriptLoader, "clicked inventory do not have painting");
        test(skriptLoader, "clicked inventory don't have activator rail in their inventory");
        test(skriptLoader, "clicked inventory don't have bookshelf in the inventory");
        test(skriptLoader, "clicked inventory don't have writable book in his inventory");
        test(skriptLoader, "clicked inventory don't have spyglass in her inventory");
        test(skriptLoader, "clicked inventory don't have bookshelf in its inventory");
        test(skriptLoader, "clicked inventory don't have enchanted book in inventory");
        test(skriptLoader, "clicked inventory don't have beehive");

        test(skriptLoader, "clicked inventory doesn't contain peony");
        test(skriptLoader, "clicked inventory does not contain potion");
        test(skriptLoader, "clicked inventory do not contain red sandstone");
        test(skriptLoader, "clicked inventory don't contain husk spawn egg");
    }

    private void test(SkriptLoader loader, String input) {
        PsiElement<?> psiElement = loader.tryParseElement(input, -1);

        assertTrue(psiElement instanceof PsiContainsItemTypeCondition);
    }
}
