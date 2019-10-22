package com.github.stefvanschie.quickskript.core.skript;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A {@link Skript} that only contains a single line and no sections, indentation, etc.
 */
public class SingleLineSkript implements Skript {

    /**
     * The parsed input Skript line or null if the parsing failed
     */
    @Nullable
    private final PsiElement<?> element;

    /**
     * Creates and attempts to parse a new single line Skript instance
     *
     * @param input the Skript line to parse
     */
    public SingleLineSkript(@NotNull String input) {
        element = SkriptLoader.get().tryParseElement(input, 1);
    }

    @NotNull
    @Override
    public String getName() {
        return "SingleLineSkript";
    }

    /**
     * Gets the element that was parsed from the input.
     * Returns null in case the parsing failed.
     *
     * @return the parsed element or null if the parsing failed
     */
    @Nullable
    public PsiElement<?> getParsedElement() {
        return element;
    }

    /**
     * Executes the {@link #getParsedElement()} and returns the result.
     * Returns null if {@link #getParsedElement()} is null.
     *
     * @param context the context in which to execute the parsed element
     * @return the result of the execution or null if the parsing failed
     */
    @Nullable
    public Object execute(@NotNull Context context) {
        return element == null ? null : element.execute(context);
    }
}
