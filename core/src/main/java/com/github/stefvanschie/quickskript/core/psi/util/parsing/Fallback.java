package com.github.stefvanschie.quickskript.core.psi.util.parsing;

import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that, when parsing, this method should be tried when all other methods annotated by {@link Pattern} failed.
 * This method will then be executed with a {@link String} and {@literal int} parameter indicating the piece of text
 * that we want to match and the line number of the element. A fallback method does not necessarily imply that the
 * factory is able to consume all text: if the method wants to indicate that it can't parse this text, it can do so by
 * returning {@literal null}.
 *
 * There may not be two methods with a {@link Fallback} annotation as to avoid conflicts as well as random behaviour in
 * terms of which method will be chosen as fallback.
 *
 * @since 0.1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Fallback {}
