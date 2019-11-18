package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Closes the currently opened inventory for the human entity
 *
 * @since 0.1.0
 */
public class PsiCloseInventoryEffect extends PsiElement<Void> {

    /**
     * The human entity to close the inventory for
     */
    @NotNull
    protected final PsiElement<?> humanEntity;

    /**
     * Creates a new element with the given line number
     *
     * @param humanEntity the human entity to close the inventory for, see {@link #humanEntity}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiCloseInventoryEffect(@NotNull PsiElement<?> humanEntity, int lineNumber) {
        super(lineNumber);

        this.humanEntity = humanEntity;
    }

    /**
     * A factory for creating {@link PsiCloseInventoryEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The patterns to match against
         */
        @NotNull
        private final SkriptPattern[] patterns = SkriptPattern.parse(
            "close [the] inventory [view] (to|of|for) %players%",
            "close %players%'[s] inventory [view]"
        );

        /**
         * Parses the {@link #patterns} and invokes this method with its types if the match succeeds
         *
         * @param player the player to close the inventory for
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("patterns")
        public PsiCloseInventoryEffect parse(@NotNull PsiElement<?> player, int lineNumber) {
            return create(player, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param player the player to close the inventory for
         * @param lineNumber the line number of this effect
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiCloseInventoryEffect create(@NotNull PsiElement<?> player, int lineNumber) {
            return new PsiCloseInventoryEffect(player, lineNumber);
        }
    }
}
