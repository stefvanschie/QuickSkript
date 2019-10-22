package com.github.stefvanschie.quickskript.bukkit.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiLanguageExpression;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets the language the player currently has selected. This cannot be pre-computed, since this may change during
 * gameplay.
 *
 * @since 0.1.0
 */
public class PsiLanguageExpressionImpl extends PsiLanguageExpression {

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to get the language from
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiLanguageExpressionImpl(@NotNull PsiElement<?> player, int lineNumber) {
        super(player, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Text executeImpl(@Nullable Context context) {
        return Text.parseLiteral(player.execute(context, Player.class).getLocale());
    }

    /**
     * A factory for creating {@link PsiLanguageExpressionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiLanguageExpression.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiLanguageExpression create(@NotNull PsiElement<?> player, int lineNumber) {
            return new PsiLanguageExpressionImpl(player, lineNumber);
        }
    }
}
