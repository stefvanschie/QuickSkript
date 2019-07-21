package com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.*;

/**
 * Indicates the skript pattern used for matching against this method
 *
 * @since 0.1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
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
}
