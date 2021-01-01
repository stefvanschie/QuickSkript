package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.util.registry.LiteralRegistry;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a literal
 *
 * @since 0.1.0
 */
public class PsiTypeLiteral extends PsiPrecomputedHolder<Class<?>> {

    /**
     * Creates a new element with the given line number
     *
     * @param clazz the class of the literal
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiTypeLiteral(@NotNull Class<?> clazz, int lineNumber) {
        super(clazz, lineNumber);
    }

    /**
     * A factory for creating {@link PsiTypeLiteral}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * This gets called whenever an attempt at parsing a tree type is made
         *
         * @param text the text to be parsed
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Fallback
        public PsiTypeLiteral parse(@NotNull SkriptLoader skriptLoader, @NotNull String text, int lineNumber) {
            LiteralRegistry.Entry entry = skriptLoader.getLiteralRegistry().byName(text.toLowerCase());

            if (entry == null) {
                return null;
            }

            return create(entry.getType(), lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param clazz the class
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiTypeLiteral create(@NotNull Class<?> clazz, int lineNumber) {
            return new PsiTypeLiteral(clazz, lineNumber);
        }
    }
}
