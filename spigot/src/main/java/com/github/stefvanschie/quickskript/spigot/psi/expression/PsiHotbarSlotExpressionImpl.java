package com.github.stefvanschie.quickskript.spigot.psi.expression;

import com.github.stefvanschie.quickskript.spigot.util.HotbarSlot;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiHotbarSlotExpression;
import com.github.stefvanschie.quickskript.core.psi.expression.util.Settable;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.MultiResult;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.literal.Slot;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the hotbar slot the player has currently selected.
 *
 * @since 0.1.0
 */
public class PsiHotbarSlotExpressionImpl extends PsiHotbarSlotExpression implements Settable {

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to get the hotbar slot from
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiHotbarSlotExpressionImpl(@Nullable PsiElement<?> player, int lineNumber) {
        super(player, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected MultiResult<? extends Slot> executeImpl(
        @Nullable SkriptRunEnvironment environment,
        @Nullable Context context
    ) {
        MultiResult<? extends Player> players = new MultiResult<>();

        if (super.players != null) {
            players = super.players.executeMulti(environment, context, Player.class);
        }

        return players.map(player -> {
            PlayerInventory inventory = player.getInventory();

            return new HotbarSlot(inventory, inventory.getHeldItemSlot());
        });
    }

    @Override
    public void set(
        @Nullable SkriptRunEnvironment environment,
        @Nullable Context context,
        @NotNull PsiElement<?> element
    ) {
        Object object = element.execute(environment, context);
        int index;

        if (object instanceof Number) {
            index = ((Number) object).intValue();
        } else if (object instanceof HotbarSlot) {
            index = ((HotbarSlot) object).getIndex();
        } else {
            throw new IllegalArgumentException("Element must be a number or a slot");
        }

        MultiResult<? extends Player> players = new MultiResult<>();

        if (super.players != null) {
            players = super.players.executeMulti(environment, context, Player.class);
        }

        for (Player player : players) {
            player.getInventory().setHeldItemSlot(index);
        }
    }

    /**
     * A factory for creating {@link PsiHotbarSlotExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiHotbarSlotExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiHotbarSlotExpression create(@Nullable PsiElement<?> player, int lineNumber) {
            return new PsiHotbarSlotExpressionImpl(player, lineNumber);
        }
    }
}
