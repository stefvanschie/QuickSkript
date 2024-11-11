package com.github.stefvanschie.quickskript.bukkit.psi.condition;

import com.destroystokyo.paper.ClientOption;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiHasChatFilteringCondition;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the players has chat filtering enabled.
 *
 * @since 0.1.0
 */
public class PsiHasChatFilteringConditionImpl extends PsiHasChatFilteringCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param players the players to check if they have chat filtering enabled
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiHasChatFilteringConditionImpl(@NotNull PsiElement<?> players, boolean positive, int lineNumber) {
        super(players, positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return super.positive == super.players.executeMulti(environment, context, Player.class).test(player ->
            player.getClientOption(ClientOption.TEXT_FILTERING_ENABLED));
    }

    /**
     * A factory for creating {@link PsiHasChatFilteringConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiHasChatFilteringCondition.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiHasChatFilteringCondition create(@NotNull PsiElement<?> players, boolean positive, int lineNumber) {
            return new PsiHasChatFilteringConditionImpl(players, positive, lineNumber);
        }
    }
}
