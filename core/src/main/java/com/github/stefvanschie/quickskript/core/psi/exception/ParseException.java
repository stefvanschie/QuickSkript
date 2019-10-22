package com.github.stefvanschie.quickskript.core.psi.exception;

import com.github.stefvanschie.quickskript.core.skript.Skript;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * An exception thrown when the parser was unable to execute properly.
 *
 * @since 0.1.0
 */
public class ParseException extends RuntimeException {

    /**
     * Stores the line number this exception occurred at
     */
    private final int lineNumber;

    public ParseException(String message, int lineNumber) {
        super(message);

        this.lineNumber = lineNumber;
    }

    public ParseException(Throwable cause, int lineNumber) {
        super(cause);

        this.lineNumber = lineNumber;
    }

    /**
     * Constructs and returns text containing extra information regarding this exception.
     * A line separator is inserted at the start for convenience.
     * This method should always be used when the exception gets handled.
     *
     * @param skript the Skript in which the code which caused this exception is
     * @return extra information regarding this exception
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public String getExtraInfo(@NotNull Skript skript) {
        return System.lineSeparator() + "Skript name: " + skript.getName() + " | Line number: " + lineNumber;
    }
}
