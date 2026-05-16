package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks if the provided entities are of the provided entity types.
 *
 * @since 0.1.0
 */
public class PsiEntityIsOfTypeCondition extends PsiElement<Boolean> {

    /**
     * The entities to check if they are of the provided entity types.
     */
    @NotNull
    protected final PsiElement<?> entities;

    /**
     * The entity types to check if they are the type of the provided entities.
     */
    @NotNull
    protected final PsiElement<?> entityTypes;

    /**
     * If false, the result is negated.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param entities the entities to check if they are of the provided entity types
     * @param entityTypes the entity types to check if they are the type of the provided entities
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiEntityIsOfTypeCondition(@NotNull PsiElement<?> entities, @NotNull PsiElement<?> entityTypes,
                                       boolean positive, int lineNumber) {
        super(lineNumber);

        this.entities = entities;
        this.entityTypes = entityTypes;
        this.positive = positive;
    }

    /**
     * A factory to create instances of {@link PsiEntityIsOfTypeCondition}.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param entities the entities to check if they are of the provided entity types
         * @param entityTypes the entity types to check if they are the type of the provided entities
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%entities% (is|are) of type[s] %entity types%")
        public PsiEntityIsOfTypeCondition parsePositive(@NotNull PsiElement<?> entities,
                                                        @NotNull PsiElement<?> entityTypes, int lineNumber) {
            return create(entities, entityTypes, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param entities the entities to check if they are of the provided entity types
         * @param entityTypes the entity types to check if they are the type of the provided entities
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%entities% (isn't|is not|aren't|are not) of type[s] %entity types%")
        public PsiEntityIsOfTypeCondition parseNegative(@NotNull PsiElement<?> entities,
                                                        @NotNull PsiElement<?> entityTypes, int lineNumber) {
            return create(entities, entityTypes, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param entities the entities to check if they are of the provided entity types
         * @param entityTypes the entity types to check if they are the type of the provided entities
         * @param positive if false, the result is negated
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiEntityIsOfTypeCondition create(@NotNull PsiElement<?> entities, @NotNull PsiElement<?> entityTypes,
                                                 boolean positive, int lineNumber) {
            return new PsiEntityIsOfTypeCondition(entities, entityTypes, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public String getType() {
            return "boolean";
        }
    }
}
