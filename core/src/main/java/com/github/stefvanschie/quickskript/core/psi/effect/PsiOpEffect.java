package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * An effect for opping/de-opping a player
 *
 * @since 0.1.0
 */
public class PsiOpEffect extends PsiElement<Void> {

    /**
     * The player to op/de-op
     */
    @NotNull
    protected final PsiElement<?> offlinePlayer;

    /**
     * True if this effect will op the player, false if it will de-op them
     */
    protected final boolean op;

    /**
     * Creates a new element with the given line number
     *
     * @param offlinePlayer the offline player to change operator status
     * @param op true to op, false to de-op
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiOpEffect(@NotNull PsiElement<?> offlinePlayer, boolean op, int lineNumber) {
        super(lineNumber);

        this.offlinePlayer = offlinePlayer;
        this.op = op;
    }

    /**
     * A factory for creating {@link PsiOpEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for opping the offline player
         */
        @NotNull
        private final SkriptPattern opPattern = SkriptPattern.parse("op %offline players%");

        /**
         * The pattern for de-opping the offline player
         */
        @NotNull
        private final SkriptPattern deOpPattern = SkriptPattern.parse("de[-]op %offline players%");

        /**
         * Parses the {@link #opPattern} and invokes this method with its types if the match succeeds
         *
         * @param offlinePlayer the offline player to op
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("opPattern")
        public PsiOpEffect parseOp(@NotNull PsiElement<?> offlinePlayer, int lineNumber) {
            return create(offlinePlayer, true, lineNumber);
        }

        /**
         * Parses the {@link #deOpPattern} and invokes this method with its types if the match succeeds
         *
         * @param offlinePlayer the offline player to de-op
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("deOpPattern")
        public PsiOpEffect parseDeOp(@NotNull PsiElement<?> offlinePlayer, int lineNumber) {
            return create(offlinePlayer, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param offlinePlayer the player to change operator state for
         * @param op true to op, false to de-op
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiOpEffect create(@NotNull PsiElement<?> offlinePlayer, boolean op, int lineNumber) {
            return new PsiOpEffect(offlinePlayer, op, lineNumber);
        }
    }
}
