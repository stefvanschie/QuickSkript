package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Checks whether the entities are riding an entity with the provided entity data.
 *
 * @since 0.1.0
 */
public class PsiEntityDataIsRidingCondition extends PsiElement<Boolean> {

    /**
     * The entities to check if they are riding an entity with the provided entity data.
     */
    @NotNull
    protected final PsiElement<?> entities;

    /**
     * The entity datas the provided entities must be riding.
     */
    @NotNull
    protected final PsiElement<?> entityDatas;

    /**
     * If false, the result is negated
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param entities the entities to check if they are riding an entity
     * @param entityDatas the entity datas the provided entities must be riding
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiEntityDataIsRidingCondition(@NotNull PsiElement<?> entities, @NotNull PsiElement<?> entityDatas,
                                             boolean positive, int lineNumber) {
        super(lineNumber);

        this.entities = entities;
        this.entityDatas = entityDatas;
        this.positive = positive;
    }

    /**
     * A factory to create instances of {@link PsiEntityDataIsRidingCondition}.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param entities the entities to check if they are riding an entity
         * @param entityDatas the entity datas the provided entities must be riding
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%entities% (is|are) riding %entity datas%")
        public PsiEntityDataIsRidingCondition parsePositive(@NotNull PsiElement<?> entities,
                                                            @NotNull PsiElement<?> entityDatas, int lineNumber) {
            return create(entities, entityDatas, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param entities the entities to check if they are riding an entity
         * @param entityDatas the entity datas the provided entities must be riding
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%entities% (isn't|is not|aren't|are not) riding %entity datas%")
        public PsiEntityDataIsRidingCondition parseNegative(@NotNull PsiElement<?> entities,
                                                            @NotNull PsiElement<?> entityDatas, int lineNumber) {
            return create(entities, entityDatas, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param entities the entities to check if they are responsive
         * @param entityDatas the entity datas the provided entities must be riding
         * @param positive false if the result of the execution should be negated, true otherwise
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(value = "_, _, _, _ -> new", pure = true)
        public PsiEntityDataIsRidingCondition create(@NotNull PsiElement<?> entities,
                                                     @NotNull PsiElement<?> entityDatas, boolean positive,
                                                     int lineNumber) {
            return new PsiEntityDataIsRidingCondition(entities, entityDatas, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public String getType() {
            return "boolean";
        }
    }
}
