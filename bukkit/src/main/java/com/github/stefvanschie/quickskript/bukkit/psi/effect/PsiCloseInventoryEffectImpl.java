package com.github.stefvanschie.quickskript.bukkit.psi.effect;

import com.github.stefvanschie.quickskript.bukkit.util.Platform;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.effect.PsiCloseInventoryEffect;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Closes the currently opened inventory for the human entity
 *
 * @since 0.1.0
 */
public class PsiCloseInventoryEffectImpl extends PsiCloseInventoryEffect {

    /**
     * Creates a new element with the given line number
     *
     * @param humanEntity the human entity to close the inventory for, see {@link #humanEntity}
     * @param lineNumber  the line number this element is associated with
     * @since 0.1.0
     */
    private PsiCloseInventoryEffectImpl(@NotNull PsiElement<?> humanEntity, int lineNumber) {
        super(humanEntity, lineNumber);
    }

    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        HumanEntity humanEntity = this.humanEntity.execute(context, HumanEntity.class);

        if (Platform.getPlatform().isAvailable(Platform.PAPER)) {
            humanEntity.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
        } else {
            humanEntity.closeInventory();
        }

        return null;
    }

    /**
     * A factory for creating {@link PsiCloseInventoryEffectImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiCloseInventoryEffect.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiCloseInventoryEffect create(@NotNull PsiElement<?> player, int lineNumber) {
            return new PsiCloseInventoryEffectImpl(player, lineNumber);
        }
    }
}
