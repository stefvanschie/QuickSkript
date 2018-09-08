package com.github.stefvanschie.quickskript.file;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * An abstract skript file node
 *
 * @since 0.1.0
 */
public class SkriptFileNode {

    /**
     * The text of this node
     */
    @NotNull
    private final String text;

    /**
     * Creates a new node with the specified text
     *
     * @param text the text
     * @since 0.1.0
     */
    SkriptFileNode(@NotNull String text) {
        this.text = text;
    }

    /**
     * Gets the text of this node
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
