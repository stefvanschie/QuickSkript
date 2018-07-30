package com.github.stefvanschie.quickskript.psi;

import com.github.stefvanschie.quickskript.psi.function.*;
import com.github.stefvanschie.quickskript.psi.literal.PsiNumber;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
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
    private static final Set<PsiFactory> FACTORIES = new HashSet<>();
    
    /**
     * A class which holds a psi element class and it's return type
     */
    /*
    Map FAQ:
    Q: Can't we use PsiElement's T to check this?
    A: No, because type erasure.

    Q: Can't we make the PsiElement impl. have a method for this?
    A: No, cause Java doesn't allow overridable static methods in interfaces.
     */
    @NotNull
    private static final Map<Class<? extends PsiElement<?>>, Class<?>> CLASS_TYPES = new HashMap<>();
    
    /**
     * Parses text into psi elements. May return null if no element was found.
     *
     * @param input the text to be parsed
     * @return the parsed psi element, or null if none were found
     * @since 0.1.0
     */
    @Nullable
    @Contract("null, _ -> fail")
    public static PsiElement<?> parseText(@NotNull String input, @NotNull Class<?> classType) {
        input = input.trim();
        
        for (PsiFactory<?> factory : FACTORIES) {
            PsiElement<?> element = factory.parse(input);
            
            if (element != null && classType.isAssignableFrom(CLASS_TYPES.get(element.getClass())))
                return element;
        }
        
        return null;
    }
    
    static {
        //functions
        registerElement(PsiAbsoluteValueFunction.class, Double.class, new PsiAbsoluteValueFunction.Factory());
        registerElement(PsiAtan2Function.class, Double.class, new PsiAtan2Function.Factory());
        registerElement(PsiCalculateExperienceFunction.class, Long.class, new PsiCalculateExperienceFunction.Factory());
        registerElement(PsiCeilFunction.class, Double.class, new PsiCeilFunction.Factory());
        registerElement(PsiCosineFunction.class, Double.class, new PsiCosineFunction.Factory());
        registerElement(PsiDateFunction.class, LocalDateTime.class, new PsiDateFunction.Factory());
        registerElement(PsiExponentialFunction.class, Double.class, new PsiExponentialFunction.Factory());
        registerElement(PsiFloorFunction.class, Double.class, new PsiFloorFunction.Factory());
        registerElement(PsiInverseCosineFunction.class, Double.class, new PsiInverseCosineFunction.Factory());
        registerElement(PsiInverseSineFunction.class, Double.class, new PsiInverseSineFunction.Factory());
        registerElement(PsiInverseTangentFunction.class, Double.class, new PsiInverseTangentFunction.Factory());
        registerElement(PsiLocationFunction.class, Location.class, new PsiLocationFunction.Factory());
        registerElement(PsiLogarithmFunction.class, Double.class, new PsiLogarithmFunction.Factory());
        registerElement(PsiMaximumFunction.class, Double.class, new PsiMaximumFunction.Factory());
        registerElement(PsiMinimumFunction.class, Double.class, new PsiMinimumFunction.Factory());
        registerElement(PsiModuloFunction.class, Double.class, new PsiModuloFunction.Factory());
        registerElement(PsiNaturalLogarithmFunction.class, Double.class, new PsiNaturalLogarithmFunction.Factory());
        registerElement(PsiProductFunction.class, Double.class, new PsiProductFunction.Factory());
        registerElement(PsiRoundFunction.class, Long.class, new PsiRoundFunction.Factory());
        registerElement(PsiSineFunction.class, Double.class, new PsiSineFunction.Factory());
        registerElement(PsiSquareRootFunction.class, Double.class, new PsiSquareRootFunction.Factory());
        registerElement(PsiSumFunction.class, Double.class, new PsiSumFunction.Factory());
        registerElement(PsiTangentFunction.class, Double.class, new PsiTangentFunction.Factory());
        registerElement(PsiVectorFunction.class, Vector.class, new PsiVectorFunction.Factory());
        registerElement(PsiWorldFunction.class, World.class, new PsiWorldFunction.Factory());
        
        //literals
        registerElement(PsiNumber.class, Double.class, new PsiNumber.Factory());
    }
    
    /**
     * Registers the specified {@link PsiElement} by linking its {@link Class}
     * to its return type ({@link Class}) and by saving its {@link PsiFactory}.
     *
     * @param elementClass the class of the {@link PsiElement} being registered
     * @param elementReturnType the return type of the {@link PsiElement} being registered
     * @param elementFactory the {@link PsiFactory} of the {@link PsiElement} being registered
     * @since 0.1.0
     */
    private static void registerElement(Class<? extends PsiElement<?>> elementClass, Class<?> elementReturnType,
                                        PsiFactory<? extends PsiElement<?>> elementFactory) {
        CLASS_TYPES.put(elementClass, elementReturnType);
        FACTORIES.add(elementFactory);
    }
    
    /**
     * Registers the specified {@link PsiElement} by linking its {@link Class}
     * to its return type ({@link Class}) and by saving its {@link PsiFactory}.
     *
     * @param elementClass the class of the {@link PsiElement} being registered
     * @param elementReturnType the return type of the {@link PsiElement} being registered
     * @param elementFactories the {@link PsiFactory}s of the {@link PsiElement} being registered
     * @since 0.1.0
     */
    @SafeVarargs
    private static void registerElement(Class<? extends PsiElement<?>> elementClass, Class<?> elementReturnType,
                                        PsiFactory<? extends PsiElement<?>>... elementFactories) {
        CLASS_TYPES.put(elementClass, elementReturnType);
        FACTORIES.addAll(Arrays.asList(elementFactories));
    }
}
