package com.github.stefvanschie.quickskript.core.util.text;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A text message containing multiple message parts
 *
 * @since 0.1.0
 */
public final class Text implements Comparable<Text> {

    /**
     * An immutable list of all the message parts
     */
    @NotNull
    private final List<TextPart> parts;

    /**
     * The compiled text: all {@link TextPart}s appended after each other
     */
    private final String compiled;

    /**
     * Creates a new text message with the specified {@link TextPart}s
     *
     * @param parts the parts to initialize this message with
     * @since 0.1.0
     */
    public Text(Collection<TextPart> parts) {
        this.parts = List.copyOf(parts);
        StringBuilder builder = new StringBuilder();
        parts.forEach(part -> part.append(builder));
        compiled = builder.toString();
    }

    /**
     * Creates a new text message with one {@link TextPart}
     *
     * @param part the part to initialize this message with
     * @since 0.1.0
     */
    private Text(TextPart part) {
        this(Collections.singletonList(part));
    }

    /**
     * Creates a new text message that is completely empty
     *
     * @since 0.1.0
     */
    private Text() {
        this(Collections.emptyList());
    }

    /**
     * Gets the parts this piece of text is made of. This {@link List} retains the order of the parts as they would
     * appear when printed: a part at index 0 comes before an element at index 1. The returned {@link List} is immutable.
     *
     * @return the parts of this text
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public List<TextPart> getParts() {
        return parts;
    }

    /**
     * Constructs a normal string form the given text message
     *
     * @return the string containing the message
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    @Override
    public String toString() {
        return compiled;
    }

    @Contract(pure = true)
    @Override
    public int hashCode() {
        return compiled.hashCode() ^ getClass().hashCode();
    }

    @Contract(pure = true)
    @Override
    public boolean equals(@NotNull Object obj) {
        if (obj == this) {
            return true;
        }

        return obj instanceof Text && obj.toString().equals(toString());
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
        //TODO this is WIP
        return text.isEmpty() ? new Text() : new Text(new TextString(text));
    }

    /**
     * Parses a text message from the given string.
     * Same as {@link #parse(String)}, except this method parses null values into {@link #empty()}.
     *
     * @param text the text to parse
     * @return the parsed text message
     * @since 0.1.0
     */
    @NotNull
    public static Text parseNullable(@Nullable String text) {
        return text == null ? Text.empty() : parse(text);
    }

    /**
     * Parses a string as text, without applying any kind of formats to it
     *
     * @param text the text to parse
     * @return the parsed text message
     * @since 0.1.0
     */
    @NotNull
    @Contract("null -> fail")
    public static Text parseLiteral(@NotNull String text) {
        return text.isEmpty() ? new Text() : new Text(new TextString(text));
    }

    /**
     * Parses a string as text, without applying any kind of formats to it.
     * Same as {@link #parse(String)}, except this method parses null values into {@link #empty()}.
     *
     * @param text the text to parse
     * @return the parsed text message
     * @since 0.1.0
     */
    @NotNull
    public static Text parseLiteralNullable(@Nullable String text) {
        return text == null ? Text.empty() : parseLiteral(text);
    }

    @Contract(pure = true)
    @Override
    public int compareTo(@NotNull Text text) {
        return toString().compareTo(text.toString());
    }

    /**
     * Gets a new, empty instance
     *
     * @return an empty text
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public static Text empty() {
        return new Text();
    }
}
