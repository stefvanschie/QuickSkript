package com.github.stefvanschie.quickskript.paper.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsOperatorCondition;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.MultiResult;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.OfflinePlayer;
import org.bukkit.permissions.ServerOperator;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks whether the offline players are operators.
 *
 * @since 0.1.0
 */
public class PsiIsOperatorConditionImpl extends PsiIsOperatorCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param offlinePlayers the offline players to check if they are operators
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsOperatorConditionImpl(@NotNull PsiElement<?> offlinePlayers, boolean positive, int lineNumber) {
        super(offlinePlayers, positive, lineNumber);
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        MultiResult<? extends OfflinePlayer> offlinePlayers = super.offlinePlayers.executeMulti(environment, context,
            OfflinePlayer.class);

        return super.positive == offlinePlayers.test(ServerOperator::isOp);
    }

    /**
     * A factory for creating instances of {@link PsiIsOperatorConditionImpl}.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsOperatorCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        @Override
        public PsiIsOperatorCondition create(@NotNull PsiElement<?> offlinePlayers, boolean positive, int lineNumber) {
            return new PsiIsOperatorConditionImpl(offlinePlayers, positive, lineNumber);
        }
    }
}
