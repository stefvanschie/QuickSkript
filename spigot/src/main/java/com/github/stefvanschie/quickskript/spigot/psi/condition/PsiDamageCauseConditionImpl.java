package com.github.stefvanschie.quickskript.spigot.psi.condition;

import com.github.stefvanschie.quickskript.spigot.context.EventContextImpl;
import com.github.stefvanschie.quickskript.spigot.util.DamageCauseUtil;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiDamageCauseCondition;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.literal.DamageCause;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks whether the damage cause of a damage event is as specified.
 *
 * @since 0.1.0
 */
public class PsiDamageCauseConditionImpl extends PsiDamageCauseCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiDamageCauseConditionImpl(@NotNull PsiElement<?> damageCause, boolean positive, int lineNumber) {
        super(damageCause, positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        if (!(context instanceof EventContextImpl eventContext)) {
            throw new ExecutionException("Can only check damage cause from a damage event", super.lineNumber);
        }

        Event event = eventContext.getEvent();

        if (!(event instanceof EntityDamageEvent damageEvent)) {
            throw new ExecutionException("Can only check damage cause from a damage event", super.lineNumber);
        }

        DamageCause damageCause = super.damageCause.execute(environment, context, DamageCause.class);

        return super.positive == (DamageCauseUtil.convert(damageCause) == damageEvent.getCause());
    }

    /**
     * A factory for creating {@link PsiDamageCauseConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiDamageCauseCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiDamageCauseCondition create(@NotNull PsiElement<?> damageCause, boolean positive, int lineNumber) {
            return new PsiDamageCauseConditionImpl(damageCause, positive, lineNumber);
        }
    }
}
