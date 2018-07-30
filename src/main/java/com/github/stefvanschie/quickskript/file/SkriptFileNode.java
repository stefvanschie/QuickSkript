package com.github.stefvanschie.quickskript.file;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

/**
 * An abstract skript file node
 *
 * @since 0.1.0
 */
public class SkriptFileNode {

    /**
     * The text of this node, this may be null if the represented node is the skript file itself
     */
    @Nullable
    private String text;

    /**
     * Creates a new node with the specified text
     *
     * @param text the text
     * @since 0.1.0
     */
    SkriptFileNode(@Nullable String text) {
        this.text = text;
    }

    /**
     * Gets the text of this node
     *
     * @return the text
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public String getText() {
        return text;
    }
}
