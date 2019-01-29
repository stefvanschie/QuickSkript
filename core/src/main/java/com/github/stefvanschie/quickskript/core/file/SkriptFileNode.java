package com.github.stefvanschie.quickskript.core.file;

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
     * The line number in the file this node represents
     */
    private final int lineNumber;

    /**
     * Creates a new node with the specified text
     *
     * @param text the text
     * @param lineNumber the line number of the text this node represents
     * @since 0.1.0
     */
    SkriptFileNode(@NotNull String text, int lineNumber) {
        this.text = text;
        this.lineNumber = lineNumber;
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

    /**
     * Gets the line number of this node. This will return 0 when the node is a skript file.
     *
     * @return the line number
     * @since 0.1.0
     */
    @Contract(pure = true)
    public int getLineNumber() {
        return lineNumber;
    }
}
