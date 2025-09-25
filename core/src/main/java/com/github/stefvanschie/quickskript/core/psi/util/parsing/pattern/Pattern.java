package com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern;

import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.*;

/**
 * Indicates the skript pattern used for matching against this method
 *
 * @since 0.1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(PatternHolder.class)
public @interface Pattern {

    /**
     * This points to a field which contains the patterns used for matching. This field may either be a single
     * {@link SkriptPattern} or an array of {@link SkriptPattern} in case multiple patterns may be used.
     *
     * @return a string pointing to a field's name
     * @since 0.1.0
     */
    @NotNull
    String value();

    /**
     * The order in which the types should be entered as parameters when invoking the referencing method. The index of
     * the number specifies the index of the type in the pattern, while the number itself specifies the position of the
     * method parameter. By default, this will act as if the order is set to 0, 1, 2, ..., n - 2, n - 1, n, where n is
     * the amount of types in the pattern, which is represented by an empty array.
     * <p>
     * To, for example, reverse the order in which the types are passed to the method's parameter, you'd specify n,
     * n - 1, n - 2, ..., 2, 1, 0. The amount of numbers specified here must be the same as the amount of types in the
     * representing pattern and a number may not be referenced twice in this order.
     * <p>
     * Index 0 is specified as the first parameter which gets replaced by the type. This may not actually be the first
     * parameter of the method, however. For example, if the parameters start with a parameter indicative of the
     * {@link SkriptMatchResult}, this parameter is not counted towards this order: index 0 will be the first
     * {@link PsiElement}, or {@link String} for types that have a literal constraint, which indicates a type in the
     * pattern. It is not possible to reference index -1 or lower to override earlier parameters from before this array
     * of type parameters. Additional parameters at the end of the parameters, such as a line number, also do not count
     * towards this count. It is also not possible to reference these later parameters by setting an index higher than
     * the type parameter array's size - 1. For example, if a pattern has two types, specifying index 2 or higher is not
     * possible.
     *
     * @return the order in which the types should be inserted into the array
     * @since 0.1.0
     */
    int @NotNull [] typeOrder() default {};
}
