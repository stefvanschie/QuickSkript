package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
    protected final PsiElement<?> path;

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
     * A factory for creating {@link PsiLoadServerIconEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiLoadServerIconEffect}s
         */
        @NotNull
        private final SkriptPattern pattern =
            SkriptPattern.parse("load [the] server icon (from|of) [the] [image] [file] %text%");

        /**
         * Parses the {@link #pattern} and invokes this method with its types if the match succeeds
         *
         * @param path the path to the image in the server's file system
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("pattern")
        public PsiLoadServerIconEffect parse(@NotNull PsiElement<?> path, int lineNumber) {
            return create(path, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
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
