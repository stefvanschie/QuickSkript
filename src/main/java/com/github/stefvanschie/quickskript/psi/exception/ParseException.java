package com.github.stefvanschie.quickskript.psi.exception;

import com.github.stefvanschie.quickskript.file.SkriptFile;
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

    /**
     * {@inheritDoc}
     */
    public ParseException(String message, int lineNumber) {
        super(message);

        this.lineNumber = lineNumber;
    }

    /**
     * Constructs and returns text containing extra information regarding this exception.
     * A line separator is inserted at the start for convenience.
     * This method should always be used when the exception gets handled.
     *
     * @param fileName the name of the {@link SkriptFile}
     * in which the code which caused this exception is
     * @return extra information regarding this exception
     */
    @NotNull
    @Contract(pure = true)
    public String getExtraInfo(String fileName) {
        return System.lineSeparator() + "Skript file: " + fileName + " | Line number: " + lineNumber;
    }
}
