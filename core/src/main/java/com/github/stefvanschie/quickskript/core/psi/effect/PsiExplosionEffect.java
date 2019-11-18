package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Creates a an explosion according to the specified arguments
 *
 * @since 0.1.0
 */
//TODO: Not all possibilities for the execution of this element are implemented
public class PsiExplosionEffect extends PsiElement<Void> {

    /**
     * The amount of force that should be applied to the created explosion
     */
    @NotNull
    protected final PsiElement<?> force;

    /**
     * True if this explosion won't do any damage, false otherwise
     */
    protected final boolean safe;

    /**
     * Creates a new element with the given lien number
     *
     * @param force the amount of force that should be applied to this explosion, see {@link #force}
     * @param safe whether this explosion shouldn't break blocks around it, see {@link #safe}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiExplosionEffect(@NotNull PsiElement<?> force, boolean safe, int lineNumber) {
        super(lineNumber);

        this.force = force;
        this.safe = safe;
    }

    /**
     * A factory for creating psi explosion effects
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching unsafe psi explosion effects
         */
        @NotNull
        private final SkriptPattern unsafePattern = SkriptPattern.parse(
            "[(create|make)] [an] explosion (of|with) (force|strength|power) %number% [%directions% %locations%]"
        );

        /**
         * The pattern for matching safe psi explosion effects
         */
        @NotNull
        private final SkriptPattern safePattern = SkriptPattern.parse(
            "[(create|make)] [a] safe explosion (of|with) (force|strength|power) %number% [%directions% %locations%]"
        );

        /**
         * Parses the {@link #unsafePattern} and invokes this method with its types if the match succeeds
         *
         * @param force the amount of force behind this explosion
         * @param directions currently unused
         * @param locations currently unused
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("unsafePattern")
        public PsiExplosionEffect parseUnsafe(@NotNull PsiElement<?> force, @NotNull PsiElement<?> directions,
                                        @NotNull PsiElement<?> locations, int lineNumber) {
            return create(force, false, lineNumber);
        }

        /**
         * Parses the {@link #safePattern} and invokes this method with its types if the match succeeds
         *
         * @param force the amount of force behind this explosion
         * @param directions currently unused
         * @param locations currently unused
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("safePattern")
        public PsiExplosionEffect parseSafe(@NotNull PsiElement<?> force, @NotNull PsiElement<?> directions,
                                        @NotNull PsiElement<?> locations, int lineNumber) {
            return create(force, true, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param force the amount of force behind this explosion
         * @param safe whether this explosion will damage the world/entities
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        protected PsiExplosionEffect create(@NotNull PsiElement<?> force, boolean safe, int lineNumber) {
            return new PsiExplosionEffect(force, safe, lineNumber);
        }
    }
}
