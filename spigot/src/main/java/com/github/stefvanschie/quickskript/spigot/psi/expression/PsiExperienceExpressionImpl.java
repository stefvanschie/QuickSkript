package com.github.stefvanschie.quickskript.spigot.psi.expression;

import com.github.stefvanschie.quickskript.spigot.context.EventContextImpl;
import com.github.stefvanschie.quickskript.spigot.util.event.ExperienceOrbSpawnEvent;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.context.EventContext;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiExperienceExpression;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the amount of experience dropped when listening to an experience orb spawn event.
 *
 * @since 0.1.0
 */
public class PsiExperienceExpressionImpl extends PsiExperienceExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiExperienceExpressionImpl(int lineNumber) {
        super(lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Integer executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("Experience expression can only be used inside events", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof ExperienceOrbSpawnEvent)) {
            throw new ExecutionException(
                "Experience expression can only be used inside experience spawn events",
                lineNumber
            );
        }

        return ((ExperienceOrbSpawnEvent) event).getXp();
    }

    @Override
    public void add(@Nullable SkriptRunEnvironment environment, @Nullable Context context, @NotNull PsiElement<?> object) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("Experience expression can only be used inside events", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof ExperienceOrbSpawnEvent)) {
            throw new ExecutionException(
                "Experience expression can only be used inside experience spawn events",
                lineNumber
            );
        }

        ExperienceOrbSpawnEvent experienceOrbSpawnEvent = (ExperienceOrbSpawnEvent) event;
        int newXp = experienceOrbSpawnEvent.getXp() + object.execute(environment, context, Number.class).intValue();

        experienceOrbSpawnEvent.setXp(Math.max(0, newXp));
    }

    @Override
    public void remove(@Nullable SkriptRunEnvironment environment, @Nullable Context context, @NotNull PsiElement<?> object) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("Experience expression can only be used inside events", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof ExperienceOrbSpawnEvent)) {
            throw new ExecutionException(
                "Experience expression can only be used inside experience spawn events",
                lineNumber
            );
        }

        ExperienceOrbSpawnEvent experienceOrbSpawnEvent = (ExperienceOrbSpawnEvent) event;
        int newXp = experienceOrbSpawnEvent.getXp() - object.execute(environment, context, Number.class).intValue();

        experienceOrbSpawnEvent.setXp(Math.max(0, newXp));
    }

    @Override
    public void removeAll(@Nullable SkriptRunEnvironment environment, @Nullable Context context, @NotNull PsiElement<?> object) {
        remove(environment, context, object);
    }

    @Override
    public void set(@Nullable SkriptRunEnvironment environment, @Nullable Context context, @NotNull PsiElement<?> object) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("Experience expression can only be used inside events", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof ExperienceOrbSpawnEvent)) {
            throw new ExecutionException(
                "Experience expression can only be used inside experience spawn events",
                lineNumber
            );
        }

        ExperienceOrbSpawnEvent experienceOrbSpawnEvent = (ExperienceOrbSpawnEvent) event;

        experienceOrbSpawnEvent.setXp(Math.max(0, object.execute(environment, context, Number.class).intValue()));
    }

    /**
     * A factory for creating {@link PsiExperienceExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiExperienceExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiExperienceExpression create(int lineNumber) {
            return new PsiExperienceExpressionImpl(lineNumber);
        }
    }
}
