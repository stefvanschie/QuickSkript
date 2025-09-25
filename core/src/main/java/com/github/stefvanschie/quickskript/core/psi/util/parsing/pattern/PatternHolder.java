package com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A basic holder class to group multiple {@link Pattern}s if specified.
 *
 * @since 0.1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PatternHolder {

    /**
     * Contains all metadata specified by {@link Pattern}, grouped together into an array.
     *
     * @return the specified metadata
     */
    @NotNull
    Pattern[] value();
}
