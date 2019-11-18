package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiPlayerListFooterExpression;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the player list footer of a player. This cannot be pre-computed, since this may change during gameplay.
 *
 * @since 0.1.0
 */
public class PsiPlayerListFooterExpressionImpl extends PsiPlayerListFooterExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to get the footer from
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiPlayerListFooterExpressionImpl(@NotNull PsiElement<?> player, int lineNumber) {
        super(player, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Text executeImpl(@Nullable Context context) {
        return Text.parseNullable(player.execute(context, Player.class).getPlayerListFooter());
    }

    @Override
    public void delete(@Nullable Context context) {
        player.execute(context, Player.class).setPlayerListFooter("");
    }

    @Override
    public void reset(@Nullable Context context) {
        delete(context);
    }

    @Override
    public void set(@Nullable Context context, @NotNull PsiElement<?> object) {
        player.execute(context, Player.class).setPlayerListFooter(object.execute(context, Text.class).toString());
    }

    /**
     * A factory for creating {@link PsiPlayerListFooterExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiPlayerListFooterExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiPlayerListFooterExpression create(@NotNull PsiElement<?> player, int lineNumber) {
            return new PsiPlayerListFooterExpressionImpl(player, lineNumber);
        }
    }
}
