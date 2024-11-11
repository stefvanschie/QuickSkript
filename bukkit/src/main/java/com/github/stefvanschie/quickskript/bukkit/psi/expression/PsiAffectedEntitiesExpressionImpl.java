package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.bukkit.context.EventContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiAffectedEntitiesExpression;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.MultiResult;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.connective.Conjunction;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.AreaEffectCloudApplyEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the affected entities in an area of effect cloud event.
 *
 * @since 0.1.0
 */
public class PsiAffectedEntitiesExpressionImpl extends PsiAffectedEntitiesExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiAffectedEntitiesExpressionImpl(int lineNumber) {
        super(lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected MultiResult<LivingEntity> executeImpl(
        @Nullable SkriptRunEnvironment environment,
        @Nullable Context context
    ) {
        if (!(context instanceof EventContextImpl eventContext) ||
            !(eventContext.getEvent() instanceof AreaEffectCloudApplyEvent event)) {
            throw new ExecutionException(
                "Unable to get affected entities from outside area of effect cloud event",
                super.lineNumber
            );
        }

        return new MultiResult<>(event.getAffectedEntities().toArray(LivingEntity[]::new), Conjunction.INSTANCE);
    }

    /**
     * A factory for creating {@link PsiAffectedEntitiesExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiAffectedEntitiesExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiAffectedEntitiesExpression create(int lineNumber) {
            return new PsiAffectedEntitiesExpressionImpl(lineNumber);
        }
    }
}
