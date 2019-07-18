package com.github.stefvanschie.quickskript.core.psi.util.parsing;

import com.github.stefvanschie.quickskript.core.pattern.group.TypeGroup;

import java.lang.annotation.*;

/**
 * An annotation indicating that this element is a literal. This is used for correctly following
 * {@link TypeGroup.Constraint}'s {@link TypeGroup.Constraint#LITERAL} and {@link TypeGroup.Constraint#NOT_LITERAL}
 * constraints. Inadequately using this annotation may lead to incorrect parsing behavior. This annotation is
 * {@link Inherited}, so literal elements in sub projects do not need to respecify this.
 *
 * This element should be placed on all factories provided for the literal, not on the psi element of the literal
 * itself.
 *
 * @since 0.1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Literal {}
