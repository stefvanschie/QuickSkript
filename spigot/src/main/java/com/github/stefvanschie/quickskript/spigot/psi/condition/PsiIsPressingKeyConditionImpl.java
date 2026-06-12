package com.github.stefvanschie.quickskript.spigot.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsPressingKeyCondition;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.literal.InputKey;
import org.bukkit.Input;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks whether the provided players are pressing the provided input keys.
 *
 * @since 0.1.0
 */
public class PsiIsPressingKeyConditionImpl extends PsiIsPressingKeyCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param players the players to check if they are pressing the input keys
     * @param inputKeys the input keys to check if they are being pressed by the players
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsPressingKeyConditionImpl(@NotNull PsiElement<?> players, @NotNull PsiElement<?> inputKeys,
                                          boolean positive, int lineNumber) {
        super(players, inputKeys, positive, lineNumber);
    }

    @SuppressWarnings("UnstableApiUsage")
    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return super.positive == super.players.executeMulti(environment, context, Player.class).test(player -> {
            Input input = player.getCurrentInput();

            return super.inputKeys.executeMulti(environment, context, InputKey.class).test(inputKey ->
                switch (inputKey) {
                    case BACKWARD -> input.isBackward();
                    case FORWARD -> input.isForward();
                    case JUMP -> input.isJump();
                    case LEFT -> input.isLeft();
                    case RIGHT -> input.isRight();
                    case SNEAK -> input.isSneak();
                    case SPRINT -> input.isSprint();
                });
        });
    }

    /**
     * A factory to create instances of {@link PsiIsPressingKeyCondition}.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsPressingKeyCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _, _ -> new", pure = true)
        @Override
        public PsiIsPressingKeyCondition create(@NotNull PsiElement<?> players, @NotNull PsiElement<?> inputKeys,
                                                boolean positive, int lineNumber) {
            return new PsiIsPressingKeyConditionImpl(players, inputKeys, positive, lineNumber);
        }
    }
}
