package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Kicks a player from the server.
 *
 * @since 0.1.0
 */
public class PsiKickEffect extends PsiElement<Void> {

    /**
     * The player to kick
     */
    @NotNull
    protected final PsiElement<?> player;

    /**
     * The reason for kicking the {@link #player}, or null
     */
    @Nullable
    protected final PsiElement<?> reason;

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to kick, see {@link #player}
     * @param reason the reason for kicking the {@link #player}, see {@link #reason}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiKickEffect(@NotNull PsiElement<?> player, @Nullable PsiElement<?> reason, int lineNumber) {
        super(lineNumber);

        this.player = player;
        this.reason = reason;
    }

    /**
     * A factory for creating {@link PsiKickEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiKickEffect}s
         */
        @NotNull
        private final SkriptPattern pattern =
            SkriptPattern.parse("kick %players% [(by reason of|because [of]|on account of|due to) %text%]");

        /**
         * Parses the {@link #pattern} and invokes this method with its types if the match succeeds
         *
         * @param player the player to kick
         * @param reason the reason for kicking this player, might be null
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("pattern")
        public PsiKickEffect parse(@NotNull PsiElement<?> player, @NotNull PsiElement<?> reason, int lineNumber) {
            return create(player, reason, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param player the player to kick
         * @param reason the reason for kicking the player, or null
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiKickEffect create(@NotNull PsiElement<?> player, @Nullable PsiElement<?> reason, int lineNumber) {
            return new PsiKickEffect(player, reason, lineNumber);
        }
    }
}
