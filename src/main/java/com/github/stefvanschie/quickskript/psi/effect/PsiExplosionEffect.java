package com.github.stefvanschie.quickskript.psi.effect;

import com.github.stefvanschie.quickskript.context.CommandContext;
import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.context.EventContext;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.skript.SkriptLoader;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Creates a an explosion according to the specified arguments
 *
 * @since 0.1.0
 */
//TODO: Not all possibilities for the execution of this element are implemented
public class PsiExplosionEffect extends PsiElement<Void> {

    /**
     * The amount of force that should be applied to the created explosion
     */
    private final PsiElement<?> force;

    /**
     * True if this explosion won't do any damage, false otherwise
     */
    private final boolean safe;

    /**
     * Creates a new element with the given lien number
     *
     * @param force the amount of force that should be applied to this explosion, see {@link #force}
     * @param safe whether this explosion shouldn't break blocks around it, see {@link #safe}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiExplosionEffect(PsiElement<?> force, boolean safe, int lineNumber) {
        super(lineNumber);

        this.force = force;
        this.safe = safe;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Void executeImpl(@Nullable Context context) {
        if (context == null)
            throw new ExecutionException("Context cannot be absent", lineNumber);

        if (context instanceof CommandContext) {
            CommandSender sender = ((CommandContext) context).getSender();

            if (!(sender instanceof Entity))
                throw new ExecutionException("Command wasn't executed by an entity", lineNumber);

            Entity entity = (Entity) sender;
            Location location = entity.getLocation();

            entity.getWorld().createExplosion(location.getX(),
                location.getY(), location.getZ(), force.execute(context, Number.class).floatValue(), !safe, !safe);
        } else if (context instanceof EventContext) {
            Event event = ((EventContext) context).getEvent();

            if (!(event instanceof EntityEvent) && !(event instanceof PlayerEvent))
                throw new ExecutionException("Event wasn't performed by an entity or player", lineNumber);

            Entity entity;

            if (event instanceof EntityEvent)
                entity = ((EntityEvent) event).getEntity();
            else
                entity = ((PlayerEvent) event).getPlayer();

            Location location = entity.getLocation();

            entity.getWorld().createExplosion(location.getX(),
                location.getY(), location.getZ(), force.execute(context, Number.class).floatValue(), !safe, !safe);
        } else
            throw new ExecutionException("Unknown context found when trying to parse an explosion effect", lineNumber);

        return null;
    }

    /**
     * A factory for creating psi explosion effects
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiExplosionEffect> {

        private final Pattern pattern = Pattern.compile(
            "(?:(?:create|make) )?(?:an? )?(safe )?explosion (?:of|with) (?:force|strength|power) ([\\s\\S]+)"
        );

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiExplosionEffect tryParse(@NotNull String text, int lineNumber) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches())
                return null;

            PsiElement<?> force = SkriptLoader.get().forceParseElement(matcher.group(2), lineNumber);

            return new PsiExplosionEffect(force, matcher.group(1) != null, lineNumber);
        }
    }
}
