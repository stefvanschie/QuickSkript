package com.github.stefvanschie.quickskript.core.file;

/**
 * Represents an individual line of skript code
 *
 * @since 0.1.0
 */
public class SkriptFileLine extends SkriptFileNode {

    SkriptFileLine(String text, int lineNumber) {
        super(text, lineNumber);
    }
}
