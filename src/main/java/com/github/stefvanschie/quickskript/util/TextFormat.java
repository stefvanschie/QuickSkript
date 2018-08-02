package com.github.stefvanschie.quickskript.util;

import org.jetbrains.annotations.Contract;

/**
 * A text format part used to format text
 *
 * @since 0.1.0
 */
public class TextFormat implements TextPart {

    /**
     * The character for this format
     */
    private final char character;

    /**
     * Creates a new format by the given character
     *
     * @param character the character
     * @since 0.1.0
     */
    public TextFormat(char character) {
        this.character = character;
    }

    /**
     * Gets the char currently in use
     *
     * @return the current char
     * @since 0.1.0
     */
    @Contract(pure = true)
    char getChar() {
        return character;
    }
}
