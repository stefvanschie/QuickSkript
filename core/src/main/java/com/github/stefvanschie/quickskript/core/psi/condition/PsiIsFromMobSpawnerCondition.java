package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks whether the entities were spawned by a mob spawner.
 *
 * @since 0.1.0
 */
public class PsiIsFromMobSpawnerCondition extends PsiElement<Boolean> {

    /**
     * The entities to check if they were spawned by a mob spawner.
     */
    protected PsiElement<?> entities;

    /**
     * If false, the result is negated.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param entities the entities to check if they were spawned by a mob spawner
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiIsFromMobSpawnerCondition(@NotNull PsiElement<?> entities, boolean positive, int lineNumber) {
        super(lineNumber);

        this.entities = entities;
        this.positive = positive;

        if (entities.isPreComputed()) {
            super.preComputed = executeImpl(null, null);

            this.entities = null;
        }
    }

    /**
     * A factory for creating {@link PsiIsFromMobSpawnerCondition}s.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param entities the entities to check if they were spawned by a mob spawner
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%entities% (is|are) from a [mob] spawner")
        @Pattern("%entities% (was|were) spawned (from|by) a [mob] spawner")
        public PsiIsFromMobSpawnerCondition parsePositive(@NotNull PsiElement<?> entities, int lineNumber) {
            return create(entities, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param entities the entities to check if they were not spawned by a mob spawner
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%entities% (isn't|aren't|is not|are not) from a [mob] spawner")
        @Pattern("%entities% (wasn't|weren't|was not|were not) spawned (from|by) a [mob] spawner")
        public PsiIsFromMobSpawnerCondition parseNegative(@NotNull PsiElement<?> entities, int lineNumber) {
            return create(entities, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param entities the entities to check if they were spawned by a mob spawner
         * @param positive false if the result of the execution should be negated, true otherwise
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        public PsiIsFromMobSpawnerCondition create(@NotNull PsiElement<?> entities, boolean positive, int lineNumber) {
            return new PsiIsFromMobSpawnerCondition(entities, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
