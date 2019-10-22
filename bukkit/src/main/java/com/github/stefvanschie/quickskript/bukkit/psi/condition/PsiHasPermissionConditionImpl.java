package com.github.stefvanschie.quickskript.bukkit.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiHasPermissionCondition;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import org.bukkit.permissions.Permissible;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks for a specific condition and returns its result. This cannot be pre computed, since there is no guarantee that
 * permissions won't be changed in an earlier piece of code.
 *
 * @since 0.1.0
 */
public class PsiHasPermissionConditionImpl extends PsiHasPermissionCondition {

    private PsiHasPermissionConditionImpl(PsiElement<?> permissible, PsiElement<?> permission, boolean positive,
                                      int lineNumber) {
        super(permissible, permission, positive, lineNumber);
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable Context context) {
        return positive == permissible
            .execute(context, Permissible.class).hasPermission(permission.execute(context, Text.class).toString());
    }

    /**
     * A factory for creating psi has permission conditions
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiHasPermissionCondition.Factory {

        @NotNull
        @Override
        public PsiHasPermissionConditionImpl create(@NotNull PsiElement<?> permissible,
                                                    @NotNull PsiElement<?> permission, boolean positive,
                                                    int lineNumber) {
            return new PsiHasPermissionConditionImpl(permissible, permission, positive, lineNumber);
        }
    }
}
