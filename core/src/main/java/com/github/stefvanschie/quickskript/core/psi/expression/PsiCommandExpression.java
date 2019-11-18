package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Gets the command used in the event, either the full command, the base command or the arguments
 *
 * @since 0.1.0
 */
public class PsiCommandExpression extends PsiElement<String> {

    /**
     * Which part of the command we want to get
     */
    @NotNull
    protected final Part part;

    /**
     * Creates a new element with the given line number
     *
     * @param part the part of the command we want to get, see {@link #part}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiCommandExpression(@NotNull Part part, int lineNumber) {
        super(lineNumber);

        this.part = part;
    }

    /**
     * A factory for creating {@link PsiCommandExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching full {@link PsiCommandExpression}s
         */
        @NotNull
        private final SkriptPattern fullPattern = SkriptPattern.parse("[the] (full|complete|whole) command");

        /**
         * The pattern for matching label {@link PsiCommandExpression}s
         */
        @NotNull
        private final SkriptPattern labelPattern = SkriptPattern.parse("[the] command [label]");

        /**
         * The pattern for matching arguments {@link PsiCommandExpression}s
         */
        @NotNull
        private final SkriptPattern argumentsPattern = SkriptPattern.parse("[the] arguments");

        /**
         * Parses the {@link #fullPattern} and invokes this method with its types if the match succeeds
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("fullPattern")
        public PsiCommandExpression parseFull(int lineNumber) {
            return create(Part.FULL, lineNumber);
        }

        /**
         * Parses the {@link #labelPattern} and invokes this method with its types if the match succeeds
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("labelPattern")
        public PsiCommandExpression parseLabel(int lineNumber) {
            return create(Part.LABEL, lineNumber);
        }

        /**
         * Parses the {@link #argumentsPattern} and invokes this method with its types if the match succeeds
         *
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("argumentsPattern")
        public PsiCommandExpression parseArguments(int lineNumber) {
            return create(Part.ARGUMENTS, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param part the part of the command we want to get
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiCommandExpression create(@NotNull Part part, int lineNumber) {
            return new PsiCommandExpression(part, lineNumber);
        }
    }

    /**
     * Which part of the command we want to get
     *
     * @since 0.1.0
     */
    public enum Part {

        /**
         * The entire command
         *
         * @since 0.1.0
         */
        FULL,

        /**
         * The command without any of the provided arguments
         *
         * @since 0.1.0
         */
        LABEL,

        /**
         * The arguments specified in the command
         *
         * @since 0.1.0
         */
        ARGUMENTS
    }
}
