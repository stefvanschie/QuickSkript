package com.github.stefvanschie.quickskript.core.psi.exception;

import com.github.stefvanschie.quickskript.core.file.skript.FileSkript;
import org.jetbrains.annotations.NotNull;

/**
 * An exception thrown when the parser was unable to execute properly.
 *
 * @since 0.1.0
 */
public class ParseException extends RuntimeException {

    /**
     * Creates a new parse exception for the given line number at which the parsing failed.
     *
     * @param message the message for this exception
     * @param lineNumber the line number at which parsing failed
     * @since 0.1.0
     */
    public ParseException(@NotNull String message, int lineNumber) {
        super(message + System.lineSeparator() + "Line number: " + lineNumber);
    }

    /**
     * Creates a new parse exception for the given line number at which the parsing failed.
     *
     * @param cause the underlying exception
     * @param lineNumber the line number at which parsing failed
     * @since 0.1.0
     */
    public ParseException(@NotNull Throwable cause, int lineNumber) {
        super(System.lineSeparator() + "Line number: " + lineNumber, cause);
    }

    /**
     * Creates a new parse exception for the given script and the line number at which the parsing failed.
     *
     * @param script the script that was attempted to be parsed
     * @param message the message for this exception
     * @param lineNumber the line number at which parsing failed
     * @since 0.1.0
     */
    public ParseException(@NotNull FileSkript script, @NotNull String message, int lineNumber) {
        super(message + System.lineSeparator() + "Skript name: " + script.getName() + " | Line number: " + lineNumber);
    }

    /**
     * Creates a new parse exception for the given script and the line number at which the parsing failed.
     *
     * @param script the script that was attempted to be parsed
     * @param cause the underlying exception
     * @param lineNumber the line number at which parsing failed
     * @since 0.1.0
     */
    public ParseException(@NotNull FileSkript script, @NotNull Throwable cause, int lineNumber) {
        super("Skript name: " + script.getName() + " | Line number: " + lineNumber, cause);
    }
}
