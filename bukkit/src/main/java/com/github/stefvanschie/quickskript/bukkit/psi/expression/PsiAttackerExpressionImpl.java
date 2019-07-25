package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.bukkit.context.EventContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.context.EventContext;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiAttackerExpression;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Set;

/**
 * The entity that attacked something in events where an entity is being damaged.
 *
 * @since 0.1.0
 */
public class PsiAttackerExpressionImpl extends PsiAttackerExpression {

    /**
     * A set of valid event classes
     */
    private static final Collection<Class<? extends Event>> VALID_EVENT_CLASSES = Set.of(
        EntityDamageByEntityEvent.class,
        EntityDeathEvent.class,
        VehicleDamageEvent.class,
        VehicleDestroyEvent.class
    );

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiAttackerExpressionImpl(int lineNumber) {
        super(lineNumber);
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Contract(pure = true)
    @Override
    protected Object executeImpl(@Nullable Context context) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("The attacker can only be gotten in events", lineNumber);
        }

        EventContextImpl eventContext = (EventContextImpl) context;
        Event event = eventContext.getEvent();

        if (VALID_EVENT_CLASSES.stream().noneMatch(clazz -> clazz.isInstance(event))) {
            throw new ExecutionException("The attacker can only be gotten from damage events", lineNumber);
        }

        return getDamager(event);
    }

    /**
     * Gets the damager from a specific event
     *
     * @param event the event to get the damager from
     * @return the damager
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    private Entity getDamager(@NotNull Event event) {
        if (event instanceof EntityDamageByEntityEvent) {
            return ((EntityDamageByEntityEvent) event).getDamager();
        }

        if (event instanceof EntityDeathEvent) {
            EntityDamageEvent lastDamageCause = ((EntityDeathEvent) event).getEntity().getLastDamageCause();

            if (lastDamageCause == null) {
                return null;
            }

            return getDamager(lastDamageCause);
        }

        if (event instanceof VehicleDamageEvent) {
            return ((VehicleDamageEvent) event).getAttacker();
        }

        if (event instanceof VehicleDestroyEvent) {
            return ((VehicleDestroyEvent) event).getAttacker();
        }

        return null;
    }

    /**
     * A factory for creating {@link PsiAttackerExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiAttackerExpression.Factory {

        /**
         * {@inheritDoc}
         */
        @NotNull
        @Contract(pure = true)
        @Override
        protected PsiAttackerExpression create(int lineNumber) {
            return new PsiAttackerExpressionImpl(lineNumber);
        }
    }
}
