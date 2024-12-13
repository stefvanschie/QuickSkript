package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.bukkit.context.EventContextImpl;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiEventBlockExpression;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.literal.Block;
import com.github.stefvanschie.quickskript.core.util.literal.Location;
import com.github.stefvanschie.quickskript.core.util.literal.World;
import org.bukkit.event.block.BlockEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the block involved in an event.
 *
 * @since 0.1.0
 */
public class PsiEventBlockExpressionImpl extends PsiEventBlockExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiEventBlockExpressionImpl(int lineNumber) {
        super(lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Block executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        if (!(context instanceof EventContextImpl eventContext) ||
            !(eventContext.getEvent() instanceof BlockEvent event)) {
            throw new ExecutionException(
                "The event block expression is only applicable in block events",
                super.lineNumber
            );
        }

        org.bukkit.Location bukkitLocation = event.getBlock().getLocation();

        var world = new World(bukkitLocation.getWorld().getName());

        double x = bukkitLocation.getX();
        double y = bukkitLocation.getY();
        double z = bukkitLocation.getZ();

        float yaw = bukkitLocation.getYaw();
        float pitch = bukkitLocation.getPitch();

        return new Block(new Location(world, x, y, z, yaw, pitch));
    }

    /**
     * A factory for creating {@link PsiEventBlockExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiEventBlockExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiEventBlockExpression create(int lineNumber) {
            return new PsiEventBlockExpressionImpl(lineNumber);
        }
    }
}
