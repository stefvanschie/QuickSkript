package com.github.stefvanschie.quickskript.psi;

import com.github.stefvanschie.quickskript.psi.effect.PsiMessageEffect;
import com.github.stefvanschie.quickskript.psi.expression.PsiConsoleSenderExpression;
import com.github.stefvanschie.quickskript.psi.expression.PsiParseExpression;
import com.github.stefvanschie.quickskript.psi.function.*;
import com.github.stefvanschie.quickskript.psi.literal.PsiNumberLiteral;
import com.github.stefvanschie.quickskript.psi.literal.PsiStringLiteral;
import org.apache.commons.lang3.Validate;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * A factory for creating psi elements from text
 *
 * @since 0.1.0
 */
public class PsiElementFactory {
    
    /**
     * A list of all possible factories available
     */
    @NotNull
    private static final List<PsiFactory<?>> FACTORIES = new ArrayList<>();

    /**
     * A map indexing converters by their name
     */
    @NotNull
    private static final Map<String, PsiConverter<?>> CONVERTERS = new HashMap<>();
    
    /**
     * Parses text into psi elements. May return null if no element was found.
     *
     * @param input the text to be parsed
     * @return the parsed psi element, or null if none were found
     * @since 0.1.0
     */
    @Nullable
    @Contract("null -> fail")
    public static PsiElement<?> parseText(@NotNull String input) {
        input = input.trim();

        for (PsiFactory<?> factory : FACTORIES) {
            PsiElement<?> element = factory.parse(input);
            
            if (element != null)
                return element;
        }
        
        return null;
    }

    /**
     * Returns a converter based on the specified name
     *
     * @param name the name of the converter
     * @return the converter
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public static PsiConverter<?> getConverter(@NotNull String name) {
        return CONVERTERS.get(name);
    }
    
    static {
        //effects
        registerElement(new PsiMessageEffect.Factory());

        //expressions
        registerElement(new PsiConsoleSenderExpression.Factory());
        registerElement(new PsiParseExpression.Factory());

        //functions
        registerElement(new PsiAbsoluteValueFunction.Factory());
        registerElement(new PsiAtan2Function.Factory());
        registerElement(new PsiCalculateExperienceFunction.Factory());
        registerElement(new PsiCeilFunction.Factory());
        registerElement(new PsiCosineFunction.Factory());
        registerElement(new PsiDateFunction.Factory());
        registerElement(new PsiExponentialFunction.Factory());
        registerElement(new PsiFloorFunction.Factory());
        registerElement(new PsiInverseCosineFunction.Factory());
        registerElement(new PsiInverseSineFunction.Factory());
        registerElement(new PsiInverseTangentFunction.Factory());
        registerElement(new PsiLocationFunction.Factory());
        registerElement(new PsiLogarithmFunction.Factory());
        registerElement(new PsiMaximumFunction.Factory());
        registerElement(new PsiMinimumFunction.Factory());
        registerElement(new PsiModuloFunction.Factory());
        registerElement(new PsiNaturalLogarithmFunction.Factory());
        registerElement(new PsiProductFunction.Factory());
        registerElement(new PsiRoundFunction.Factory());
        registerElement(new PsiSineFunction.Factory());
        registerElement(new PsiSquareRootFunction.Factory());
        registerElement(new PsiSumFunction.Factory());
        registerElement(new PsiTangentFunction.Factory());
        registerElement(new PsiVectorFunction.Factory());
        registerElement(new PsiWorldFunction.Factory());
        
        //literals
        registerElement(new PsiNumberLiteral.Factory());
        registerElement(new PsiStringLiteral.Factory());

        //converter
        registerConverter("number", new PsiNumberLiteral.Converter());
        registerConverter("text", new PsiStringLiteral.Converter());
    }
    
    /**
     * Registers the specified {@link PsiFactory}.
     *
     * @param elementFactory the {@link PsiFactory} of the {@link PsiElement} being registered
     * @since 0.1.0
     */
    private static void registerElement(@NotNull PsiFactory<? extends PsiElement<?>> elementFactory) {
		Validate.isTrue(FACTORIES.add(elementFactory), "The specified PsiFactory has already been registered.");
    }

    /**
     * Registers the specified {@link PsiConverter} by its name.
     *
     * @param name the name of the converter
     * @param converter the converter
     * @since 0.1.0
     */
    private static void registerConverter(@NotNull String name, PsiConverter<?> converter) {
        Validate.isTrue(CONVERTERS.put(name, converter) == null, "The specified PsiConverter has already been registered.");
    }
}
