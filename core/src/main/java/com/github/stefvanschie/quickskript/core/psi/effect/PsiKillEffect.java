package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Kills the entity
 *
 * @since 0.1.0
 */
public class PsiKillEffect extends PsiElement<Void> {

    /**
     * The entity to kill
     */
    @NotNull
    protected final PsiElement<?> entity;

    /**
     * Creates a new element with the given line number
     *
     * @param entity the entity to kill, see {@link #entity}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiKillEffect(@NotNull PsiElement<?> entity, int lineNumber) {
        super(lineNumber);

        this.entity = entity;
    }

    /**
     * A factory for creating {@link PsiKillEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiKillEffect}s
         */
        @NotNull
        private final SkriptPattern pattern = SkriptPattern.parse("kill %entities%");

        /**
         * Parses the {@link #pattern} and invokes this method with its types if the match succeeds
         *
         * @param entity the entity to kill
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("pattern")
        public PsiKillEffect tryParse(@NotNull PsiElement<?> entity, int lineNumber) {
            return create(entity, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param entity the entity to kill
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiKillEffect create(@NotNull PsiElement<?> entity, int lineNumber) {
            return new PsiKillEffect(entity, lineNumber);
        }
    }
}
