package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiSpeedExpression;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *  Gets the player's walking or flying speed
 *
 * @since 0.1.0
 */
public class PsiSpeedExpressionImpl extends PsiSpeedExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param movementType the type of movement to get the value of
     * @param player the player to get the speed from
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiSpeedExpressionImpl(@NotNull MovementType movementType, @NotNull PsiElement<?> player, int lineNumber) {
        super(movementType, player, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Float executeImpl(@Nullable Context context) {
        Player player = this.player.execute(context, Player.class);

        if (movementType == MovementType.WALKING) {
            return player.getWalkSpeed();
        }

        if (movementType == MovementType.FLYING) {
            return player.getFlySpeed();
        }

        throw new ExecutionException(new UnsupportedOperationException("Unknown movement type"), lineNumber);
    }

    @Override
    public void add(@Nullable Context context, @NotNull PsiElement<?> object) {
        Player player = this.player.execute(context, Player.class);
        float delta = object.execute(context, Number.class).floatValue();

        if (movementType == MovementType.WALKING) {
            player.setWalkSpeed((float) Math.max(-1.0, Math.min(1.0, player.getWalkSpeed() + delta)));
        } else if (movementType == MovementType.FLYING) {
            player.setFlySpeed((float) Math.max(-1.0, Math.min(1.0, player.getFlySpeed() + delta)));
        }
    }

    @Override
    public void remove(@Nullable Context context, @NotNull PsiElement<?> object) {
        Player player = this.player.execute(context, Player.class);
        float delta = object.execute(context, Number.class).floatValue();

        if (movementType == MovementType.WALKING) {
            player.setWalkSpeed((float) Math.max(-1.0, Math.min(1.0, player.getWalkSpeed() - delta)));
        } else if (movementType == MovementType.FLYING) {
            player.setFlySpeed((float) Math.max(-1.0, Math.min(1.0, player.getFlySpeed() - delta)));
        }
    }

    @Override
    public void reset(@Nullable Context context) {
        Player player = this.player.execute(context, Player.class);

        if (movementType == MovementType.WALKING) {
            player.setWalkSpeed(0.2f);
        } else if (movementType == MovementType.FLYING) {
            player.setFlySpeed(0.1f);
        }
    }

    @Override
    public void set(@Nullable Context context, @NotNull PsiElement<?> object) {
        Player player = this.player.execute(context, Player.class);
        float value = object.execute(context, Number.class).floatValue();

        if (movementType == MovementType.WALKING) {
            player.setWalkSpeed((float) Math.max(-1.0, Math.min(1.0, value)));
        } else if (movementType == MovementType.FLYING) {
            player.setFlySpeed((float) Math.max(-1.0, Math.min(1.0, value)));
        }
    }

    /**
     * A factory for creating {@link PsiSpeedExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiSpeedExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiSpeedExpression create(@NotNull MovementType movementType, @NotNull PsiElement<?> player,
            int lineNumber) {
            return new PsiSpeedExpressionImpl(movementType, player, lineNumber);
        }
    }
}
