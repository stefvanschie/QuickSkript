package com.github.stefvanschie.quickskript.paper.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsNormalizedCondition;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the given vectors are normalized.
 *
 * @since 0.1.0
 */
public class PsiIsNormalizedConditionImpl extends PsiIsNormalizedCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param vectors the vectors to check if they are normalized
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsNormalizedConditionImpl(@NotNull PsiElement<?> vectors, boolean positive, int lineNumber) {
        super(vectors, positive, lineNumber);
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return super.positive == super.vectors.executeMulti(environment, context, Vector.class)
            .test(Vector::isNormalized);
    }

    /**
     * A factory to create instances of {@link PsiIsNormalizedConditionImpl}.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsNormalizedCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        @Override
        public PsiIsNormalizedCondition create(@NotNull PsiElement<?> vectors, boolean positive, int lineNumber) {
            return new PsiIsNormalizedConditionImpl(vectors, positive, lineNumber);
        }
    }
}
