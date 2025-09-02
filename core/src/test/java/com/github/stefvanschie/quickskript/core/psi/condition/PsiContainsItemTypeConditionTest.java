package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.StandaloneSkriptLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PsiContainsItemTypeConditionTest {

    private static SkriptLoader loader;

    @BeforeAll
    static void test() {
        loader = new StandaloneSkriptLoader();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "clicked inventory has painting in their inventory",
        "clicked inventory has enchanted book in the inventory",
        "clicked inventory has netherite helmet in his inventory",
        "clicked inventory has tripwire hook in her inventory",
        "clicked inventory has pumpkin seeds in its inventory",
        "clicked inventory has potion in inventory",
        "clicked inventory has tadpole bucket",
        "clicked inventory have tipped arrow in their inventory",
        "clicked inventory have mud brick wall in the inventory",
        "clicked inventory have snowball in his inventory",
        "clicked inventory have stripped warped hyphae in her inventory",
        "clicked inventory have sea pickle in its inventory",
        "clicked inventory have painting",
        "clicked inventory contains end portal frame",
        "clicked inventory contain witch spawn eggs",
        "clicked inventory doesn't have enchanted book in their inventory",
        "clicked inventory doesn't have snowball in the inventory",
        "clicked inventory doesn't have player head in his inventory",
        "clicked inventory doesn't have tipped arrow in her inventory",
        "clicked inventory doesn't have painting in its inventory",
        "clicked inventory doesn't have cyan dye in inventory",
        "clicked inventory doesn't have jungle sapling",
        "clicked inventory does not have sunflower in their inventory",
        "clicked inventory does not have painting in the inventory",
        "clicked inventory does not have painting in his inventory",
        "clicked inventory does not have water bucket in her inventory",
        "clicked inventory does not have granite in its inventory",
        "clicked inventory does not have jungle fence gate in inventory",
        "clicked inventory does not have ravager spawn egg",
        "clicked inventory do not have blue candle in their inventory",
        "clicked inventory do not have detector rail in the inventory",
        "clicked inventory do not have prize pottery sherd in his inventory",
        "clicked inventory do not have tipped arrow in her inventory",
        "clicked inventory do not have snort pottery sherd in its inventory",
        "clicked inventory do not have podzol in inventory",
        "clicked inventory do not have painting",
        "clicked inventory don't have activator rail in their inventory",
        "clicked inventory don't have bookshelf in the inventory",
        "clicked inventory don't have writable book in his inventory",
        "clicked inventory don't have spyglass in her inventory",
        "clicked inventory don't have bookshelf in its inventory",
        "clicked inventory don't have enchanted book in inventory",
        "clicked inventory don't have beehive",
        "clicked inventory doesn't contain peony",
        "clicked inventory does not contain potion",
        "clicked inventory do not contain red sandstone",
        "clicked inventory don't contain husk spawn egg"
    })
    void test(String input) {
        assertInstanceOf(PsiContainsItemTypeCondition.class, loader.tryParseElement(input, -1));
    }
}
