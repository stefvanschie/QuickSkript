package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Executes a command.
 *
 * @since 0.1.0
 */
public class PsiCommandEffect extends PsiElement<Void> {

    /**
     * The name of the command to execute
     */
    @NotNull
    protected PsiElement<?> commandName;

    /**
     * The command sender to execute the command
     */
    @Nullable
    protected PsiElement<?> commandSender;

    /**
     * Creates a new element with the given line number
     *
     * @param commandName the name of the command to execute
     * @param commandSender the command sender to execute the command
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiCommandEffect(@NotNull PsiElement<?> commandName, @Nullable PsiElement<?> commandSender,
                               int lineNumber) {
        super(lineNumber);

        this.commandName = commandName;
        this.commandSender = commandSender;
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
     * A factory for creating {@link PsiCommandEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiCommandEffect> {

        /**
         * Patterns to match a {@link PsiCommandEffect}
         */
        @NotNull
        @SuppressWarnings("HardcodedFileSeparator")
        private final Set<Pattern> patterns = Stream.of(
            "(?:execute )?(?:the )?command (?<name>[\\s\\S]+?)(?: by (?<sender>[\\s\\S]+))?$",
            "(?:execute )?(?:the )?(?<sender>[\\s\\S]+) command (?<name>[\\s\\S]+)",
            "(?:let|make) (?<sender>[\\s\\S]+) execute (?:(?:the)? command )?(?<name>[\\s\\S]+)"
        ).map(Pattern::compile).collect(Collectors.toUnmodifiableSet());

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Contract(pure = true)
        @Override
        public PsiCommandEffect tryParse(@NotNull String text, int lineNumber) {
            Optional<Matcher> optionalMatcher = patterns.stream()
                .map(pattern -> pattern.matcher(text))
                .filter(Matcher::matches)
                .findAny();

            if (optionalMatcher.isEmpty()) {
                return null;
            }

            Matcher matcher = optionalMatcher.get();

            SkriptLoader skriptLoader = SkriptLoader.get();

            PsiElement<?> commandName = skriptLoader.forceParseElement(matcher.group("name"), lineNumber);

            String senderGroup = matcher.group("sender");
            PsiElement<?> commandSender = null;

            if (senderGroup != null) {
                commandSender = skriptLoader.forceParseElement(senderGroup, lineNumber);
            }

            return create(commandName, commandSender, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters. This should be overridden by impl, instead of the {@link #tryParse(String, int)}
         * method.
         *
         * @param commandName the name of the command to execute
         * @param commandSender the executor of the command
         * @param lineNumber the line number of this effect
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiCommandEffect create(@NotNull PsiElement<?> commandName, @Nullable PsiElement<?> commandSender,
                                       int lineNumber) {
            return new PsiCommandEffect(commandName, commandSender, lineNumber);
        }
    }
}
