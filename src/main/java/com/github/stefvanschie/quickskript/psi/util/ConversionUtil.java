package com.github.stefvanschie.quickskript.psi.util;

import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.psi.exception.ParseException;

import java.util.Collection;

/**
 * A utility class for conversion between different objects
 */
public class ConversionUtil {

    /**
     * Converts an array of strings into a collection of numeric psi elements.
     *
     * @param values the textual representation of the values
     * @param numbers the collection
     * @since 0.1.0
     */
    public static void convert(String[] values, Collection<PsiElement<Number>> numbers) {
        for (String value : values) {
            PsiElement<Number> number = (PsiElement<Number>) PsiElementFactory.parseText(value, Number.class);

            if (number == null)
                throw new ParseException("Function was unable to find an expression named " + value);

            numbers.add(number);
        }
    }
}
