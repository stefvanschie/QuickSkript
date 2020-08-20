package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.util.literal.SoundCategory;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a sound category
 *
 * @since 0.1.0
 */
public class PsiSoundCategoryLiteral extends PsiPrecomputedHolder<SoundCategory> {

    /**
     * Creates a new element with the given line number
     *
     * @param soundCategory the sound category this represents
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiSoundCategoryLiteral(@NotNull SoundCategory soundCategory, int lineNumber) {
        super(soundCategory, lineNumber);
    }

    /**
     * A factory for creating {@link PsiSoundCategoryLiteral}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * This is called whenever a sound category is attempted to be parsed.
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Fallback
        public PsiSoundCategoryLiteral parse(@NotNull String text, int lineNumber) {
            if (!text.endsWith(" category")) {
                return null;
            }

            text = text.substring(0, text.length() - " category".length());

            for (SoundCategory soundCategory : SoundCategory.values()) {
                if (!soundCategory.name().replace('_', ' ').equalsIgnoreCase(text)) {
                    continue;
                }

                return create(soundCategory, lineNumber);
            }

            return null;
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param soundCategory the sound category
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiSoundCategoryLiteral create(@NotNull SoundCategory soundCategory, int lineNumber) {
            return new PsiSoundCategoryLiteral(soundCategory, lineNumber);
        }
    }
}
