package com.github.stefvanschie.quickskript.util;

/**
 * An interface for text parts, serves no other purpose than creating an abstract layer for all parts
 *
 * @since 0.1.0
 */
interface TextPart {

    /**
     * Adds this {@link TextPart} instance to the end of
     * the specified {@link StringBuilder}.
     *
     * @param builder the builder to append to
     * @since 0.1.0
     */
    void append(StringBuilder builder);
}
