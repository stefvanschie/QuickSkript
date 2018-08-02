package com.github.stefvanschie.quickskript.util;

import org.jetbrains.annotations.Contract;
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
     * Creates a new literal string text part from the given string
     *
     * @param text the text this object will represent
     * @since 0.1.0
     */
    public TextString(@NotNull String text) {
        this.text = text;
    }

    /**
     * Returns the text message
     *
     * @return the text
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public String getText() {
        return text;
    }
}
