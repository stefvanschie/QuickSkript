package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("(show|reveal) [all] player [related] info[rmation] [(in|to|on|from) [the] server list]")
        public PsiPlayerInfoEffect parseShow(int lineNumber) {
            return create(true, lineNumber);
        }

        /**
         * Parses the pattern and invokes this method with its types if the match succeeds
         *
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("hide [all] player [related] info[rmation] [(in|on|from) [the] server list]")
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

        @Nullable
        @Contract(pure = true)
        @Override
        public Type getType() {
            return null;
        }
    }
}
