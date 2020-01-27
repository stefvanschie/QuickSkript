package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.util.literal.Gene;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets a gene
 *
 * @since 0.1.0
 */
public class PsiGeneLiteral extends PsiPrecomputedHolder<Gene> {

    /**
     * Creates a new element with the given line number
     *
     * @param gene the gene
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiGeneLiteral(@NotNull Gene gene, int lineNumber) {
        super(gene, lineNumber);
    }

    /**
     * A factory for creating {@link PsiGeneLiteral}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * This gets called upon parsing
         *
         * @param text the text to parse
         * @param lineNumber the line number
         * @return the literal, or null to indicate failure
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Fallback
        public PsiGeneLiteral parse(@NotNull String text, int lineNumber) {
            for (Gene gene : Gene.values()) {
                if (gene.name().replace('_', ' ').equalsIgnoreCase(text)) {
                    return create(gene, lineNumber);
                }
            }

            return null;
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param gene the gene
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiGeneLiteral create(@NotNull Gene gene, int lineNumber) {
            return new PsiGeneLiteral(gene, lineNumber);
        }
    }
}
