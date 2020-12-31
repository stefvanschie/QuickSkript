package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.util.literal.TreeType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a tree type
 *
 * @since 0.1.0
 */
public class PsiTreeTypeLiteral extends PsiPrecomputedHolder<TreeType> {

    /**
     * Creates a new element with the given line number
     *
     * @param treeType the tree type
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiTreeTypeLiteral(@NotNull TreeType treeType, int lineNumber) {
        super(treeType, lineNumber);
    }

    /**
     * A factory for creating {@link PsiTreeTypeLiteral}s
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
        public PsiTreeTypeLiteral parse(@NotNull String text, int lineNumber) {
            TreeType treeType = TreeType.byName(text.toLowerCase());

            if (treeType == null) {
                return null;
            }

            return create(treeType, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param treeType the tree type
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiTreeTypeLiteral create(@NotNull TreeType treeType, int lineNumber) {
            return new PsiTreeTypeLiteral(treeType, lineNumber);
        }
    }
}
