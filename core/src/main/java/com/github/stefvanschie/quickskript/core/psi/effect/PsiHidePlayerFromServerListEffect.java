package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Hides a player from the server list
 *
 * @since 0.1.0
 */
public class PsiHidePlayerFromServerListEffect extends PsiElement<Void> {

    /**
     * The player toh ide from the server list
     */
    @NotNull
    protected final PsiElement<?> player;

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to hide from the server list, see {@link #player}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiHidePlayerFromServerListEffect(@NotNull PsiElement<?> player, int lineNumber) {
        super(lineNumber);

        this.player = player;
    }

    /**
     * A factory for creating {@link PsiHidePlayerFromServerListEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiHidePlayerFromServerListEffect}s with
         */
        @NotNull
        private final SkriptPattern[] patterns = SkriptPattern.parse(
            "hide %players% (in|on|from) [the] server list",
            "hide %players%'[s] info[rmation] (in|on|from) [the] server list"
        );

        /**
         * Parses the {@link #patterns} and invokes this method with its types if the match succeeds
         *
         * @param player the player to hide
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("patterns")
        public PsiHidePlayerFromServerListEffect parse(@NotNull PsiElement<?> player, int lineNumber) {
            return create(player, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param player the player to hide from the server list
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiHidePlayerFromServerListEffect create(@NotNull PsiElement<?> player, int lineNumber) {
            return new PsiHidePlayerFromServerListEffect(player, lineNumber);
        }
    }
}
