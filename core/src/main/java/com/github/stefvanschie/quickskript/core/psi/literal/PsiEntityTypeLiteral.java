package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.util.Type;
import com.github.stefvanschie.quickskript.core.util.registry.EntityTypeRegistry;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets an entity type
 *
 * @since 0.1.0
 */
public class PsiEntityTypeLiteral extends PsiPrecomputedHolder<EntityTypeRegistry.Entry> {

    /**
     * Creates a new element with the given line number
     *
     * @param entityType the entity type
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiEntityTypeLiteral(@NotNull EntityTypeRegistry.Entry entityType, int lineNumber) {
        super(entityType, lineNumber);
    }

    /**
     * A factory for creating {@link PsiEntityTypeLiteral}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * This gets called upon parsing
         *
         * @param skriptLoader the skript loader
         * @param text the text to parse
         * @param lineNumber the line number
         * @return the literal, or null to indicate failure
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Fallback
        public PsiEntityTypeLiteral parse(@NotNull SkriptLoader skriptLoader, @NotNull String text, int lineNumber) {
            EntityTypeRegistry.Entry entityType = skriptLoader.getEntityTypeRegistry().byName(text.toLowerCase());

            if (entityType == null) {
                return null;
            }

            return create(entityType, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param entityType the entity type
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiEntityTypeLiteral create(@NotNull EntityTypeRegistry.Entry entityType, int lineNumber) {
            return new PsiEntityTypeLiteral(entityType, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.OBJECT;
        }
    }
}
