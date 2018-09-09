package com.github.stefvanschie.quickskript.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A text message containing multiple message parts
 *
 * @since 0.1.0
 */
public final class Text {

    /**
     * A list of all the message parts
     */
    @NotNull
    private final List<TextPart> parts = new ArrayList<>();

    /**
     * Creates a new text message with one {@link TextPart}
     *
     * @param part the part to initialize this message with
     * @since 0.1.0
     */
    private Text(TextPart part) {
        this.parts.add(part);
    }

    /**
     * Constructs a normal string form the given text message
     *
     * @return the string containing the message
     * @since 0.1.0
     */
    @NotNull
    public String construct() {
        StringBuilder message = new StringBuilder();

        parts.forEach(part -> part.append(message));

        return message.toString();
    }

    /**
     * Parses a text message from the given string
     *
     * @param text the text to parse
     * @return the parsed text message
     * @since 0.1.0
     */
    @NotNull
    @Contract("null -> fail")
    public static Text parse(@NotNull String text) {
        return new Text(new TextString(text));
    }
}
