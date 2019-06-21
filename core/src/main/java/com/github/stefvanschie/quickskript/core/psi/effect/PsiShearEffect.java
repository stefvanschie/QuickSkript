package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

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
    protected PsiElement<?> sheep;

    /**
     * True if the {@link #sheep} will be sheared, otherwise the sheep will grow back wool
     */
    protected boolean shear;

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
     * @throws UnsupportedOperationException implementation is required for this functionality
     */
    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        throw new UnsupportedOperationException("Cannot execute expression without implementation.");
    }

    /**
     * A factory for creating {@link PsiShearEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiShearEffect> {

        /**
         * The pattern for matching this element
         */
        @NotNull
        private final Pattern pattern = Pattern.compile("(un-?)?shear (?<sheep>.+)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiShearEffect tryParse(@NotNull String text, int lineNumber) {
            var matcher = pattern.matcher(text);

            if (!matcher.matches()) {
                return null;
            }

            PsiElement<?> sheep = SkriptLoader.get().forceParseElement(matcher.group("sheep"), lineNumber);

            return create(sheep, matcher.groupCount() < 2, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
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
