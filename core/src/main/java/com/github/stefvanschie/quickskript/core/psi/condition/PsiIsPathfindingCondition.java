package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the living entities are pathfinding.
 *
 * @since 0.1.0
 */
public class PsiIsPathfindingCondition extends PsiElement<Boolean> {

    /**
     * The living entities to check if they are pathfinding.
     */
    @NotNull
    protected final PsiElement<?> livingEntities;

    /**
     * The target to check if this is the destination of the living entities. If null, it does not matter which
     * destination they are pathfinding to.
     */
    @Nullable
    protected final PsiElement<?> target;

    /**
     * If false, the result is negated.
     */
    protected final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param livingEntities the living entities to check if they are pathfinding
     * @param target the target destination or null
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiIsPathfindingCondition(@NotNull PsiElement<?> livingEntities, @Nullable PsiElement<?> target,
                                        boolean positive, int lineNumber) {
        super(lineNumber);

        this.livingEntities = livingEntities;
        this.target = target;
        this.positive = positive;
    }

    /**
     * A factory for creating instances of {@link PsiIsPathfindingCondition}.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param livingEntities the living entities to check if they are pathfinding
         * @param target the target destination
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%living entities% (is|are) pathfinding [to[wards] %living entity/location%]")
        public PsiIsPathfindingCondition parsePositive(@NotNull PsiElement<?> livingEntities,
                                                       @Nullable PsiElement<?> target, int lineNumber) {
            return create(livingEntities, target, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param livingEntities the living entities to check if they are pathfinding
         * @param target the target destination
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%living entities% (isn't|is not|aren't|are not) pathfinding [to[wards] %living entity/location%]")
        public PsiIsPathfindingCondition parseNegative(@NotNull PsiElement<?> livingEntities,
                                                       @Nullable PsiElement<?> target, int lineNumber) {
            return create(livingEntities, target, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param livingEntities the living entities to check if they are pathfinding
         * @param target the target destination
         * @param positive false if the result of the execution should be negated, true otherwise
         * @param lineNumber the line number
         * @return the condition
         * @since 0.1.0
         */
        @NotNull
        @Contract(value = "_, _, _, _ -> new", pure = true)
        public PsiIsPathfindingCondition create(@NotNull PsiElement<?> livingEntities, @Nullable PsiElement<?> target,
                                                boolean positive, int lineNumber) {
            return new PsiIsPathfindingCondition(livingEntities, target, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public String getType() {
            return "boolean";
        }
    }
}
