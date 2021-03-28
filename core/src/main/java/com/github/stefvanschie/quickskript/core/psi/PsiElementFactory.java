package com.github.stefvanschie.quickskript.core.psi;

import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

/**
 * An abstract factory interface for parsing psi elements. Every element needs at least one factory, but may have more.
 *
 * @since 0.1.0
 */
public interface PsiElementFactory {

    /**
     * Gets the type of object this psi element's execution will provide. If the given element doesn't yield any objects
     * upon execution, this should return null.
     *
     * @return the returned elements' class
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    Type getType();
}
