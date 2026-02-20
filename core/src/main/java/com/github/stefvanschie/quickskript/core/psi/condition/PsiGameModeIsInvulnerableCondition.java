package com.github.stefvanschie.quickskript.core.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.Type;
import com.github.stefvanschie.quickskript.core.util.literal.GameMode;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks whether the game modes are invulnerable.
 *
 * @since 0.1.0
 */
public class PsiGameModeIsInvulnerableCondition extends PsiElement<Boolean> {

    /**
     * The game modes to check if they are invulnerable.
     */
    private PsiElement<?> gameModes;

    /**
     * If false, the result is negated.
     */
    private final boolean positive;

    /**
     * Creates a new element with the given line number
     *
     * @param gameModes the game modes to check if they are invulnerable
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiGameModeIsInvulnerableCondition(@NotNull PsiElement<?> gameModes, boolean positive, int lineNumber) {
        super(lineNumber);

        this.gameModes = gameModes;
        this.positive = positive;

        if (gameModes.isPreComputed()) {
            super.preComputed = executeImpl(null, null);

            this.gameModes = null;
        }
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        return this.positive == this.gameModes.executeMulti(environment, context, GameMode.class)
            .test(gameMode -> !gameMode.isVulnerable());
    }

    /**
     * A factory for creating {@link PsiGameModeIsInvulnerableCondition}s.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param gameModes the game modes to check if they are invulnerable
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%gamemodes% (is|are) (invulnerable|invincible)")
        public PsiGameModeIsInvulnerableCondition parsePositive(@NotNull PsiElement<?> gameModes, int lineNumber) {
            return create(gameModes, true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param gameModes the game modes to check if they are invulnerable
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("%gamemodes% (isn't|is not|aren't|are not) (invulnerable|invincible)")
        public PsiGameModeIsInvulnerableCondition parseNegative(@NotNull PsiElement<?> gameModes, int lineNumber) {
            return create(gameModes, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param gameModes the game modes to check if they are in rain
         * @param positive if false, the result is negated
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        public PsiGameModeIsInvulnerableCondition create(
            @NotNull PsiElement<?> gameModes,
            boolean positive,
            int lineNumber
        ) {
            return new PsiGameModeIsInvulnerableCondition(gameModes, positive, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BOOLEAN;
        }
    }
}
