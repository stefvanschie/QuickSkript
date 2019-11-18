package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Shears or grows back wool on a sheep
 *
 * @since 0.1.0
 */
public class PsiShearEffect extends PsiElement<Void> {

    /**
     * The sheep to shear or grow back wool on
     */
    @NotNull
    protected final PsiElement<?> sheep;

    /**
     * True if the {@link #sheep} will be sheared, otherwise the sheep will grow back wool
     */
    protected final boolean shear;

    /**
     * Creates a new element with the given line number
     *
     * @param sheep the sheep to shear, see {@link #sheep}
     * @param shear true if the {@link #sheep} will be sheared, otherwise wool will grow back on, see {@link #shear}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiShearEffect(@NotNull PsiElement<?> sheep, boolean shear, int lineNumber) {
        super(lineNumber);

        this.sheep = sheep;
        this.shear = shear;
    }

    /**
     * A factory for creating {@link PsiShearEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching this element
         */
        @NotNull
        private final SkriptPattern shearPattern = SkriptPattern.parse("shear %living entities%");

        /**
         * The pattern for matching this element
         */
        @NotNull
        private final SkriptPattern unshearPattern = SkriptPattern.parse("un[-]shear %living entities%");

        /**
         * Parses the {@link #shearPattern} and invokes this method with its types if the match succeeds
         *
         * @param sheep the sheep to shear
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("shearPattern")
        public PsiShearEffect parseShear(@NotNull PsiElement<?> sheep, int lineNumber) {
            return create(sheep, true, lineNumber);
        }

        /**
         * Parses the {@link #unshearPattern} and invokes this method with its types if the match succeeds
         *
         * @param sheep the sheep to unshear
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("unshearPattern")
        public PsiShearEffect parseUnshear(@NotNull PsiElement<?> sheep, int lineNumber) {
            return create(sheep, false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param sheep the sheep to shear
         * @param shear true to shear the sheep, otherwise grows back their wool
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiShearEffect create(@NotNull PsiElement<?> sheep, boolean shear, int lineNumber) {
            return new PsiShearEffect(sheep, shear, lineNumber);
        }
    }
}
