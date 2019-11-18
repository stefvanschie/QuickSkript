package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Feeds the player by a specified amount of hunger bars.
 *
 * @since 0.1.0
 */
public class PsiFeedEffect extends PsiElement<Void> {

    /**
     * The player whose hunger should be restored
     */
    @NotNull
    protected final PsiElement<?> player;

    /**
     * The amount of hunger bars to restore, may be null
     */
    @Nullable
    protected final PsiElement<?> amount;

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to whose hunger should be restored, see {@link #player}
     * @param amount the amount of hunger to restore, or null, see {@link #amount}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiFeedEffect(@NotNull PsiElement<?> player, @Nullable PsiElement<?> amount,  int lineNumber) {
        super(lineNumber);

        this.player = player;
        this.amount = amount;
    }

    /**
     * A factory for creating {@link PsiFeedEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern to match {@link PsiFeedEffect}s
         */
        @NotNull
        private final SkriptPattern pattern = SkriptPattern.parse("feed [the] %players% [by %number% [beef[s]]]");

        /**
         * Parses the {@link #pattern} and invokes this method with its types if the match succeeds
         *
         * @param player the player to feed
         * @param amount the amount of hunger bars to add to the player's hunger meter, or null
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("pattern")
        public PsiFeedEffect parse(@NotNull PsiElement<?> player, @Nullable PsiElement<?> amount, int lineNumber) {
            return create(player, amount, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param player the player to feed
         * @param amount the amount of hunger bars, or null
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiFeedEffect create(@NotNull PsiElement<?> player, @Nullable PsiElement<?> amount, int lineNumber) {
            return new PsiFeedEffect(player, amount, lineNumber);
        }
    }
}
