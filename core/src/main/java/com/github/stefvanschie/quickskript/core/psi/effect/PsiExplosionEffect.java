package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * @throws UnsupportedOperationException implementation is required for this functionality
     */
    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        throw new UnsupportedOperationException("Cannot execute expression without implementation.");
    }

    /**
     * A factory for creating psi explosion effects
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiExplosionEffect> {

        /**
         * The pattern for matching psi explosion effects
         */
        @NotNull
        private final Pattern pattern = Pattern.compile(
            "(?:(?:create|make) )?(?:an? )?(safe )?explosion (?:of|with) (?:force|strength|power) ([\\s\\S]+)"
        );

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Contract(pure = true)
        @Override
        public PsiExplosionEffect tryParse(@NotNull String text, int lineNumber) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches()) {
                return null;
            }

            PsiElement<?> force = SkriptLoader.get().forceParseElement(matcher.group(2), lineNumber);

            return create(force, matcher.group(1) != null, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
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
