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
 * Loads a server icon for later usage
 *
 * @since 0.1.0
 */
public class PsiLoadServerIconEffect extends PsiElement<Void> {

    /**
     * The path to the server icon
     */
    @NotNull
    protected PsiElement<?> path;

    /**
     * Creates a new element with the given line number
     *
     * @param path the path to the server icon, see {@link #path}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiLoadServerIconEffect(@NotNull PsiElement<?> path, int lineNumber) {
        super(lineNumber);

        this.path = path;
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
     * A factory for creating {@link PsiLoadServerIconEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiLoadServerIconEffect> {

        /**
         * The pattern for matching {@link PsiLoadServerIconEffect}s
         */
        @NotNull
        private final Pattern pattern =
            Pattern.compile("load(?: the)? server icon (?:from|of)(?: the)?(?: image)?(?: file)? (?<path>.+)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiLoadServerIconEffect tryParse(@NotNull String text, int lineNumber) {
            var matcher = pattern.matcher(text);

            if (!matcher.matches()) {
                return null;
            }

            return create(SkriptLoader.get().forceParseElement(matcher.group("path"), lineNumber), lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param path the path to the server icon
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiLoadServerIconEffect create(@NotNull PsiElement<?> path, int lineNumber) {
            return new PsiLoadServerIconEffect(path, lineNumber);
        }
    }
}
