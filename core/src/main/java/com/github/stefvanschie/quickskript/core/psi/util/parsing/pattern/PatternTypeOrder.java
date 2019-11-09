package com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern;

import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.*;

/**
 * Specifies additional information on how to access the underlying method for specific patterns
 *
 * @since 0.1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(PatternTypeOrderHolder.class)
public @interface PatternTypeOrder {

    /**
     * The patterns for which this data holds true. By default all patterns are handled, indicated by an empty array,
     * however this should likely be overridden to specify an exact pattern index in case needed. Note that it may be
     * easier to move the types as defined in your method's parameters than adding this annotation.
     *
     * There may not be multiple of these annotations on the same method which reference the same pattern.
     *
     * @return the patterns as ordered by the referencing {@link Pattern#value()} annotation. If this references a
     * {@link SkriptPattern[]}, the order is as specified there. If this references a {@link SkriptPattern}, the order
     * is always 0 (as a single element can't have order).
     * @since 0.1.0
     */
    @NotNull
    int[] patterns() default {};

    /**
     * The order in which the types should be entered as parameters when invoking the referencing method. The index of
     * the number specifies the index of the type in the pattern, while the number itself specifies the position of the
     * method parameter. By default this will act as if the order is set to 0, 1, 2, ..., n - 2, n - 1, n, where n is
     * the amount of types in the pattern, which is represented by an empty array.
     *
     * To, for example, reverse the order in which the types are passed to the method's parameter, you'd specify n,
     * n - 1, n - 2, ..., 2, 1, 0. The amount of numbers specified here must be the same as the amount of types in the
     * representing pattern and a number may not be referenced twice in this order. In case you have multiple patterns
     * with different custom orders, multiple of these annotations should be specified in the
     * {@link Pattern} annotation, since the order specified here will be applied to all patterns as referenced in
     * {@link #patterns}.
     *
     * Index 0 is specified as the first parameter which gets replaced by the type. This may not actually be the first
     * parameter of the method, however. For example, if the parameters start with a parameter indicative of the
     * {@link SkriptMatchResult}, this parameter is not counted towards this order: index 0 will be the first
     * {@link PsiElement}, or {@link String} for types that have a literal constraint, which indicates a type in the
     * pattern. It is not possible to reference index -1 or lower to override earlier parameters from before this array
     * of type parameters. Additional parameters at the end of the parameters, such as a line number, also do not count
     * towards this count. It is also not possible to reference these later parameters by setting an index higher than
     * the type parameter array's size - 1. For example, if a pattern with two types is referenced by the
     * {@link Pattern}, specifying index 2 or higher is not possible.
     *
     * @return the order in which the types should be inserted into the array
     * @since 0.1.0
     */
    @NotNull
    int[] typeOrder() default {};

}
