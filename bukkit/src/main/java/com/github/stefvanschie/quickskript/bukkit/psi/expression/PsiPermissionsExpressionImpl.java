package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiPermissionsExpression;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets all permissions of a player. This cannot be pre computed, since the permissions may change during gameplay.
 *
 * @since 0.1.0
 */
public class PsiPermissionsExpressionImpl extends PsiPermissionsExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to get the permissions from, see {@link #player}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiPermissionsExpressionImpl(@NotNull PsiElement<?> player, int lineNumber) {
        super(player, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected String[] executeImpl(@Nullable Context context) {
        return player.execute(context, Player.class).getEffectivePermissions().stream()
            .map(PermissionAttachmentInfo::getPermission)
            .toArray(String[]::new);
    }

    /**
     * A factory for creating {@link PsiPermissionsExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiPermissionsExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiPermissionsExpression create(@NotNull PsiElement<?> player, int lineNumber) {
            return new PsiPermissionsExpressionImpl(player, lineNumber);
        }
    }
}
