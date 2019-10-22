package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.github.stefvanschie.quickskript.bukkit.context.EventContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.context.EventContext;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiHoverListExpression;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Gets the hover list. This cannot be pre-computed, since it may change during gameplay.
 *
 * @since 0.1.0
 */
public class PsiHoverListExpressionImpl extends PsiHoverListExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiHoverListExpressionImpl(int lineNumber) {
        super(lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Collection<Text> executeImpl(@Nullable Context context) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("This expression can only be used inside events", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof PaperServerListPingEvent)) {
            throw new ExecutionException("This expression can only be used inside ping events", lineNumber);
        }

        return ((PaperServerListPingEvent) event).getPlayerSample().stream()
            .map(playerProfile -> {
                String name = playerProfile.getName();

                return Text.parseLiteral(name == null ? "null" : name);
            }).collect(Collectors.toList());
    }

    @Override
    public void add(@Nullable Context context, @NotNull PsiElement<?> object) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("This expression can only be used inside events", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof PaperServerListPingEvent)) {
            throw new ExecutionException("This expression can only be used inside ping events", lineNumber);
        }

        ((PaperServerListPingEvent) event).getPlayerSample().add(forceGetProfile(context, object));
    }

    @Override
    public void delete(@Nullable Context context) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("This expression can only be used inside events", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof PaperServerListPingEvent)) {
            throw new ExecutionException("This expression can only be used inside ping events", lineNumber);
        }

        ((PaperServerListPingEvent) event).getPlayerSample().clear();
    }

    @Override
    public void remove(@Nullable Context context, @NotNull PsiElement<?> object) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("This expression can only be used inside events", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof PaperServerListPingEvent)) {
            throw new ExecutionException("This expression can only be used inside ping events", lineNumber);
        }

        ((PaperServerListPingEvent) event).getPlayerSample().remove(forceGetProfile(context, object));
    }

    @Override
    public void reset(@Nullable Context context) {
        delete(context);
    }

    @Override
    public void set(@Nullable Context context, @NotNull PsiElement<?> object) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("This expression can only be used inside events", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof PaperServerListPingEvent)) {
            throw new ExecutionException("This expression can only be used inside ping events", lineNumber);
        }

        List<PlayerProfile> playerSample = ((PaperServerListPingEvent) event).getPlayerSample();

        playerSample.clear();
        playerSample.add(forceGetProfile(context, object));
    }

    /**
     * Forcefully gets a profile from the specified parameter.
     *
     * @param context the context
     * @param object the object to get the profile from
     * @return the player profile
     * @since 0.1.0
     * @throws ExecutionException if the context is incorrect
     */
    @NotNull
    @Contract(pure = true)
    private PlayerProfile forceGetProfile(@Nullable Context context, @NotNull PsiElement<?> object) {
        if (!(context instanceof EventContext)) {
            throw new ExecutionException("This expression can only be used inside events", lineNumber);
        }

        Event event = ((EventContextImpl) context).getEvent();

        if (!(event instanceof PaperServerListPingEvent)) {
            throw new ExecutionException("This expression can only be used inside ping events", lineNumber);
        }

        Object obj = object.execute(context);

        if (obj instanceof Player) {
            return Bukkit.createProfile(((Player) obj).getUniqueId(), ((Player) obj).getName());
        }

        return Bukkit.createProfile(UUID.randomUUID(), obj == null ? "null" : obj.toString());
    }

    /**
     * A factory for creating {@link PsiHoverListExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiHoverListExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiHoverListExpression create(int lineNumber) {
            return new PsiHoverListExpressionImpl(lineNumber);
        }
    }
}
