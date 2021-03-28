package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.util.Type;
import com.github.stefvanschie.quickskript.core.util.registry.VisualEffectRegistry;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a visual effect
 *
 * @since 0.1.0
 */
public class PsiVisualEffectLiteral extends PsiPrecomputedHolder<VisualEffectRegistry.Entry> {

    /**
     * Creates a new element with the given line number
     *
     * @param visualEffect the visual effect
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiVisualEffectLiteral(@NotNull VisualEffectRegistry.Entry visualEffect, int lineNumber) {
        super(visualEffect, lineNumber);
    }

    /**
     * A factory for creating {@link PsiVisualEffectLiteral}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * This gets called whenever an attempt at parsing a tree type is made
         *
         * @param loader the underlying skript loader
         * @param text the text to be parsed
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Fallback
        public PsiVisualEffectLiteral parse(@NotNull SkriptLoader loader, @NotNull String text, int lineNumber) {
            for (VisualEffectRegistry.Entry entry : loader.getVisualEffectRegistry().getEntries()) {
                for (SkriptMatchResult match : entry.getPattern().match(text)) {
                    if (match.hasUnmatchedParts()) {
                        continue;
                    }

                    return create(entry, lineNumber);
                }
            }

            return null;
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param visualEffect the visual effect
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiVisualEffectLiteral create(@NotNull VisualEffectRegistry.Entry visualEffect, int lineNumber) {
            return new PsiVisualEffectLiteral(visualEffect, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.VISUAL_EFFECT;
        }
    }
}
