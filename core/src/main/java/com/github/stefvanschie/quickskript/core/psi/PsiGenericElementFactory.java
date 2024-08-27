package com.github.stefvanschie.quickskript.core.psi;

import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.util.Type;

/**
 * An abstract factory interface for parsing psi elements. Every element needs at least one factory, but may have more.
 * <p>
 * A generic factory indicates that the factory can parse in different ways, to conform to a requested type. All methods
 * used for parsing by this class must have a mandatory {@link Type}[] parameter, after the optional
 * {@link SkriptMatchResult} parameter and before the first parameter corresponding to the first type (or line number if
 * no types exist or the method is annotated with {@link Fallback}). If the type is irrelevant, the {@link Type}[]
 * argument may be null.
 * <p>
 * A factory can reject a type by returning null during parsing, in the same way it would reject a given string if it
 * can't parse it. If a factory succeeds in parsing, i.e. it returns a non-null value, it is assumed the parsing
 * happened such that the returned {@link PsiElement} conforms to the type. That is, execution of this element should
 * yield a return value that conforms to the type given during parsing.
 *
 * @since 0.1.0
 */
public interface PsiGenericElementFactory {
}
