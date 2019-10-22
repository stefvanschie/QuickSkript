package com.github.stefvanschie.quickskript.bukkit.psi.effect;

import com.github.stefvanschie.quickskript.bukkit.context.EventContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.effect.PsiExplosionEffect;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Creates a an explosion according to the specified arguments
 *
 * @since 0.1.0
 */
//TODO: Not all possibilities for the execution of this element are implemented
public class PsiExplosionEffectImpl extends PsiExplosionEffect {

    /**
     * Creates a new element with the given lien number
     *
     * @param force the amount of force that should be applied to this explosion, see {@link #force}
     * @param safe whether this explosion shouldn't break blocks around it, see {@link #safe}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiExplosionEffectImpl(@NotNull PsiElement<?> force, boolean safe, int lineNumber) {
        super(force, safe, lineNumber);
    }

    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        if (context == null) {
            throw new ExecutionException("Context cannot be absent", lineNumber);
        }

        CommandSender sender = ((EventContextImpl) context).getCommandSender();

        if (sender == null) {
            throw new ExecutionException("Unknown context found when trying to parse an explosion effect", lineNumber);
        }

        if (!(sender instanceof Entity)) {
            throw new ExecutionException("Command wasn't executed by an entity", lineNumber);
        }

        Entity entity = (Entity) sender;
        Location location = entity.getLocation();

        entity.getWorld().createExplosion(location.getX(),
            location.getY(), location.getZ(), force.execute(context, Number.class).floatValue(), !safe, !safe);

        return null;
    }

    /**
     * A factory for creating psi explosion effects
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiExplosionEffect.Factory {

        @NotNull
        @Override
        public PsiExplosionEffect create(@NotNull PsiElement<?> force, boolean safe, int lineNumber) {
            return new PsiExplosionEffectImpl(force, safe, lineNumber);
        }
    }
}
