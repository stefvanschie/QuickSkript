package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.PatternTypeOrder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Executes a command.
 *
 * @since 0.1.0
 */
public class PsiCommandEffect extends PsiElement<Void> {

    /**
     * The command sender to execute the command
     */
    @Nullable
    protected final PsiElement<?> commandSender;

    /**
     * The name of the command to execute
     */
    @NotNull
    protected final PsiElement<?> commandName;

    /**
     * Creates a new element with the given line number
     *
     * @param commandSender the command sender to execute the command
     * @param commandName the name of the command to execute
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiCommandEffect(@Nullable PsiElement<?> commandSender, @NotNull PsiElement<?> commandName,
                               int lineNumber) {
        super(lineNumber);

        this.commandSender = commandSender;
        this.commandName = commandName;
    }

    /**
     * A factory for creating {@link PsiCommandEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Patterns to match a {@link PsiCommandEffect}
         */
        @NotNull
        @SuppressWarnings("HardcodedFileSeparator")
        private final SkriptPattern[] patterns = SkriptPattern.parse(
            "[execute] [the] command %texts% [by %players/console%]",
            "[execute] [the] %players/console% command %texts%",
            "(let|make) %players/console% execute [[the] command] %texts%"
        );

        /**
         * Parses the {@link #patterns} and invokes this method with its types if the match succeeds
         *
         * @param commandSender the executor of the command
         * @param commandName the name of the command to execute
         * @param lineNumber the line number of this effect
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("patterns")
        @PatternTypeOrder(patterns = 0, typeOrder = {1, 0})
        public PsiCommandEffect parse(@Nullable PsiElement<?> commandSender, @NotNull PsiElement<?> commandName,
                                         int lineNumber) {
            return create(commandSender, commandName, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param commandSender the executor of the command
         * @param commandName the name of the command to execute
         * @param lineNumber the line number of this effect
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiCommandEffect create(@Nullable PsiElement<?> commandSender, @NotNull PsiElement<?> commandName,
                                       int lineNumber) {
            return new PsiCommandEffect(commandSender, commandName, lineNumber);
        }
    }
}
