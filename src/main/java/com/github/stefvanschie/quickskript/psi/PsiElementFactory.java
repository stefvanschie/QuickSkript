package com.github.stefvanschie.quickskript.psi;

import com.github.stefvanschie.quickskript.psi.function.*;
import com.github.stefvanschie.quickskript.psi.literal.PsiNumber;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
     * Parses text into pis elements. May return null if no element was found.
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

    /**
     * Returns the class types map
     *
     * @return the class types
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public static Map<Class<? extends PsiElement<?>>, Class<?>> getClassTypes() {
        return CLASS_TYPES;
    }

    static {
        //functions
        FACTORIES.add(new PsiAbsoluteValueFunction.Factory());
        FACTORIES.add(new PsiAtan2Function.Factory());
        FACTORIES.add(new PsiCalculateExperienceFunction.Factory());
        FACTORIES.add(new PsiCeilFunction.Factory());
        FACTORIES.add(new PsiCosineFunction.Factory());
        FACTORIES.add(new PsiDateFunction.Factory());
        FACTORIES.add(new PsiExponentialFunction.Factory());
        FACTORIES.add(new PsiFloorFunction.Factory());
        FACTORIES.add(new PsiInverseCosineFunction.Factory());
        FACTORIES.add(new PsiInverseSineFunction.Factory());
        FACTORIES.add(new PsiInverseTangentFunction.Factory());
        FACTORIES.add(new PsiLocationFunction.Factory());
        FACTORIES.add(new PsiLogarithmFunction.Factory());
        FACTORIES.add(new PsiNaturalLogarithmFunction.Factory());

        //literals
        FACTORIES.add(new PsiNumber.Factory());
    }
}
