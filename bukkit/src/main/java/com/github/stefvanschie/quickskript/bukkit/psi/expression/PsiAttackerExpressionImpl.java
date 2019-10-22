package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.bukkit.context.EventContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.context.EventContext;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiAttackerExpression;
import org.apache.commons.lang.Validate;
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

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * The entity that attacked something in events where an entity is being damaged.
 *
 * @since 0.1.0
 */
public class PsiAttackerExpressionImpl extends PsiAttackerExpression {

    /**
     * A collection of valid event classes and attacker extractor pairs
     */
    private static final Map<Class<? extends Event>, Function<? extends Event, Entity>> ATTACKER_EXTRACTORS = new IdentityHashMap<>();

    /**
     * Registers an event and e function responsible for extracting the attacker from said event.
     *
     * @param clazz the class of the event to register, parameter is necessary due to type erasure
     * @param extractor the function that gets the attacker from an event instance
     * @param <T> the type of the event
     */
    private static <T extends Event> void registerExtractor(@NotNull Class<T> clazz,
            @NotNull Function<T, Entity> extractor) {
        Validate.isTrue(ATTACKER_EXTRACTORS.put(clazz, extractor) == null, "An event can only be registered once");
    }

    static {
        registerExtractor(EntityDamageByEntityEvent.class, EntityDamageByEntityEvent::getDamager);
        registerExtractor(VehicleDamageEvent.class, VehicleDamageEvent::getAttacker);
        registerExtractor(VehicleDestroyEvent.class, VehicleDestroyEvent::getAttacker);

        registerExtractor(EntityDeathEvent.class, event -> {
            EntityDamageEvent lastDamageCause = event.getEntity().getLastDamageCause();
            return lastDamageCause instanceof EntityDamageByEntityEvent
                    ? ((EntityDamageByEntityEvent) lastDamageCause).getDamager()
                    : null;
        });
    }

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiAttackerExpressionImpl(int lineNumber) {
        super(lineNumber);
    }

    @Nullable
    @Contract(pure = true)
    @Override
    protected Object executeImpl(@Nullable Context context) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("The attacker can only be gotten in events", lineNumber);
        }

        EventContextImpl eventContext = (EventContextImpl) context;
        Event event = eventContext.getEvent();

        for (Map.Entry<Class<? extends Event>, Function<? extends Event, Entity>> entry : ATTACKER_EXTRACTORS.entrySet()) {
            if (entry.getClass().isInstance(event)) {
                return ((Function<Event, Entity>) entry.getValue()).apply(event);
            }
        }

        throw new ExecutionException("The attacker can only be gotten from damage events", lineNumber);
    }

    /**
     * A factory for creating {@link PsiAttackerExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiAttackerExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        protected PsiAttackerExpression create(int lineNumber) {
            return new PsiAttackerExpressionImpl(lineNumber);
        }
    }
}
