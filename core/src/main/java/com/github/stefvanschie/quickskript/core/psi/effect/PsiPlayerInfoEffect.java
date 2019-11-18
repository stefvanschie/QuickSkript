package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Hides/Shows player info when pinging the server
 *
 * @since 0.1.0
 */
public class PsiPlayerInfoEffect extends PsiElement<Void> {

    /**
     * If true, the information will be shown, otherwise the information will be hidden
     */
    protected final boolean show;

    /**
     * Creates a new element with the given line number
     *
     * @param show whether the information should be shown or hidden, see {@link #show}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiPlayerInfoEffect(boolean show, int lineNumber) {
        super(lineNumber);

        this.show = show;
    }

    /**
     * A factory for creating {@link PsiPlayerInfoEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching effects that should show the information
         */
        @NotNull
        private final SkriptPattern showPattern = SkriptPattern
            .parse("(show|reveal) [all] player [related] info[rmation] [(in|to|on|from) [the] server list]");

        /**
         * The pattern for matching effects that should hide the information
         */
        @NotNull
        private final SkriptPattern hidePattern =
            SkriptPattern.parse("hide [all] player [related] info[rmation] [(in|on|from) [the] server list]");

        /**
         * Parses the {@link #showPattern} and invokes this method with its types if the match succeeds
         *
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("showPattern")
        public PsiPlayerInfoEffect parseShow(int lineNumber) {
            return create(true, lineNumber);
        }

        /**
         * Parses the {@link #hidePattern} and invokes this method with its types if the match succeeds
         *
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("hidePattern")
        public PsiPlayerInfoEffect parseHide(int lineNumber) {
            return create(false, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param show true to show info, false to hide it
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        protected PsiPlayerInfoEffect create(boolean show, int lineNumber) {
            return new PsiPlayerInfoEffect(show, lineNumber);
        }
    }
}
