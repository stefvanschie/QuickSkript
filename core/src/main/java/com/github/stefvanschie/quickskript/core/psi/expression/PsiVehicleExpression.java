package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Gets the vehicle of an entity, or null if the entity doesn't have a vehicle.
 *
 * @since 0.1.0
 */
public class PsiVehicleExpression extends PsiElement<Object> {

    /**
     * The entity to get the vehicle from
     */
    @NotNull
    protected PsiElement<?> entity;

    /**
     * Creates a new element with the given line number
     *
     * @param entity the entity to get the vehicle from
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiVehicleExpression(@NotNull PsiElement<?> entity, int lineNumber) {
        super(lineNumber);

        this.entity = entity;
    }

    /**
     * A factory for creating {@link PsiVehicleExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiVehicleExpression}s
         */
        @NotNull
        private SkriptPattern[] patterns = SkriptPattern.parse(
            "[the] vehicle[s] of %entities%",
            "%entities%'[s] vehicle[s]"
        );

        /**
         * Parses the {@link #patterns} and invokes this method with its types if the match succeeds
         *
         * @param entity the entity to get the vehicle from
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("patterns")
        public PsiVehicleExpression parse(@NotNull PsiElement<?> entity, int lineNumber) {
            return create(entity, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param entity the entity to get the vehicle from
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiVehicleExpression create(@NotNull PsiElement<?> entity, int lineNumber) {
            return new PsiVehicleExpression(entity, lineNumber);
        }
    }
}
