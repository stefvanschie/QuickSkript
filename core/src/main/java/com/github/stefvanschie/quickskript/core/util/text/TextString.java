package com.github.stefvanschie.quickskript.core.util.text;

import org.jetbrains.annotations.NotNull;

/**
 * A normal string as part of a text message
 *
 * @since 0.1.0
 */
public class TextString implements TextPart {

    /**
     * The text this object represents
     */
    @NotNull
    private final String text;

    /**
     * Creates a new literal string text part from the given {@link String}.
     * The {@link String} must not be empty.
     *
     * @param text the text this object will represent
     * @since 0.1.0
     */
    public TextString(@NotNull String text) {
        if (text.isEmpty()) throw new IllegalArgumentException("The text mustn't be empty.");
        this.text = text;
    }

    @Override
    public void append(@NotNull StringBuilder builder) {
        builder.append(text);
    }
}
