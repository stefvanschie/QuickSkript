package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.util.registry.TypeRegistry;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a type.
 *
 * @since 0.1.0
 */
public class PsiTypeLiteral extends PsiPrecomputedHolder<TypeRegistry.Entry> {

    /**
     * Creates a new element with the given line number
     *
     * @param type the type
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiTypeLiteral(@NotNull TypeRegistry.Entry type, int lineNumber) {
        super(type, lineNumber);
    }

    /**
     * A factory for creating {@link PsiTypeLiteral}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * This gets called whenever an attempt at parsing a type is made
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
            TypeRegistry.Entry entry = skriptLoader.getTypeRegistry().byName(text.toLowerCase());

            if (entry == null) {
                return null;
            }

            return create(entry, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param type the type
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiTypeLiteral create(@NotNull TypeRegistry.Entry type, int lineNumber) {
            return new PsiTypeLiteral(type, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public String getType() {
            return "object";
        }
    }
}
