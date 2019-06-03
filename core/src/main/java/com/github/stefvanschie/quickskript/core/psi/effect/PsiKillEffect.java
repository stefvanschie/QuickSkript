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
 * Kills the entity
 *
 * @since 0.1.0
 */
public class PsiKillEffect extends PsiElement<Void> {

    /**
     * The entity to kill
     */
    @NotNull
    protected PsiElement<?> entity;

    /**
     * Creates a new element with the given line number
     *
     * @param entity the entity to kill, see {@link #entity}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiKillEffect(@NotNull PsiElement<?> entity, int lineNumber) {
        super(lineNumber);

        this.entity = entity;
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
     * A factory for creating {@link PsiKillEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiKillEffect> {

        /**
         * The pattern for matching {@link PsiKillEffect}s
         */
        @NotNull
        private Pattern pattern = Pattern.compile("kill (?<entity>.+)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiKillEffect tryParse(@NotNull String text, int lineNumber) {
            var matcher = pattern.matcher(text);

            if (!matcher.matches()) {
                return null;
            }

            return create(SkriptLoader.get().forceParseElement(matcher.group("entity"), lineNumber), lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param entity the entity to kill
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiKillEffect create(@NotNull PsiElement<?> entity, int lineNumber) {
            return new PsiKillEffect(entity, lineNumber);
        }
    }
}
