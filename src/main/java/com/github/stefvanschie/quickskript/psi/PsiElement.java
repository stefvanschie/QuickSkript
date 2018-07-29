package com.github.stefvanschie.quickskript.psi;

/**
 * An abstract representation of an element in a skript file.
 *
 * @param <T> the object type that will be returned by executing this element. Void if nothing will be returned.
 * @since 0.1.0
 */
public interface PsiElement<T> {

    /**
     * Executes this element
     *
     * @since 0.1.0
     */
    T execute();

}
