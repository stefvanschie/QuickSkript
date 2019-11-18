package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Makes the player start/stop flying
 *
 * @since 0.1.0
 */
public class PsiFlyEffect extends PsiElement<Void> {

    /**
     * The player to start/stop flight for
     */
    @NotNull
    protected final PsiElement<?> player;

    /**
     * If true, the {@link #player} will start flying, otherwise they'll stop
     */
    protected final boolean enable;

    /**
     * Creates a new element with the given line number
     *
     * @param player the player to start/stop flight for, see {@link #player}
     * @param enable true if the player will start flight, otherwise stop flight, see {@link #enable}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiFlyEffect(@NotNull PsiElement<?> player, boolean enable, int lineNumber) {
        super(lineNumber);

        this.player = player;
        this.enable = enable;
    }

    /**
     * A factory for creating {@link PsiFlyEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The patterns to match against
         */
        @NotNull
        private final SkriptPattern[] patterns = SkriptPattern.parse(
            "force %players% to [(0\u00A6start|1\u00A6stop)] fly[ing]",
            "make %players% (0\u00A6start|1\u00A6stop) flying",
            "make %players% fly"
        );

        /**
         * Parses the {@link #patterns} and invokes this method with its types if the match succeeds
         *
         * @param result the match result
         * @param player the player to start/stop flight for
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("patterns")
        public PsiFlyEffect parse(@NotNull SkriptMatchResult result, @NotNull PsiElement<?> player, int lineNumber) {
            return create(player, result.getParseMark() == 0, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param player the player to start/stop flight for
         * @param enable true if we start flight, otherwise stop flight
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiFlyEffect create(@NotNull PsiElement<?> player, boolean enable, int lineNumber) {
            return new PsiFlyEffect(player, enable, lineNumber);
        }
    }
}
