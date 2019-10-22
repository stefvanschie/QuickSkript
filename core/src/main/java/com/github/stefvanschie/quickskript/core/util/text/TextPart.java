package com.github.stefvanschie.quickskript.core.util.text;

/**
 * An interface for text parts, serves no other purpose than creating an abstract layer for all parts.
 * All implementations must be immutable.
 *
 * @since 0.1.0
 */
public interface TextPart {

    /**
     * Adds this instance to the end of the specified {@link StringBuilder}.
     *
     * @param builder the builder to append to
     * @since 0.1.0
     */
    void append(StringBuilder builder);
}
