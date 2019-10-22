package com.github.stefvanschie.quickskript.core.psi.effect;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.util.text.Text;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * An effect for logging text to log files and the console
 *
 * @since 0.1.0
 */
public class PsiLogEffect extends PsiElement<Void> {

    /**
     * The text to log
     */
    @NotNull
    protected final PsiElement<?> text;

    /**
     * The file name to write to. If this is omitted, the console will be used.
     */
    @Nullable
    protected final PsiElement<?> fileName;

    /**
     * Creates a new element with the given line number
     *
     * @param text the text to log, see {@link #text}
     * @param fileName the file name to write to, see {@link #fileName}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    protected PsiLogEffect(@NotNull PsiElement<?> text, @Nullable PsiElement<?> fileName, int lineNumber) {
        super(lineNumber);

        this.text = text;
        this.fileName = fileName;
    }

    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        String text = this.text.execute(context, Text.class).toString();

        if (fileName == null) {
            String prefix = context == null ? "" : '[' + context.getSkript().getName() + "] ";

            System.out.println(prefix + text);
        } else {
            String fileName = this.fileName.execute(context, Text.class).toString();

            if (!fileName.endsWith(".log")) {
                fileName += ".log";
            }

            if (fileName.equalsIgnoreCase("server.log")) {
                System.out.println(text);
            } else {
                try {
                    File file = new File(File.separatorChar + fileName);

                    if (!file.exists() && !file.createNewFile()) {
                        throw new ExecutionException("Unable to create file", lineNumber);
                    }

                    String prefix = '[' + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] ";
                    String fullString = prefix + text + System.lineSeparator();

                    Files.writeString(file.toPath(), fullString, StandardOpenOption.APPEND);
                } catch (IOException exception) {
                    throw new ExecutionException(exception, lineNumber);
                }
            }
        }

        return null;
    }

    /**
     * A factory for creating {@link PsiLogEffect}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern for matching {@link PsiLogEffect}s
         */
        @NotNull
        private final SkriptPattern pattern = SkriptPattern.parse("log %texts% [(to|in) [file[s]] %texts%]");

        /**
         * Parses the {@link #pattern} and invokes this method with its types if the match succeeds
         *
         * @param text the text to log
         * @param fileName the file name to log to, or null
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("pattern")
        public PsiLogEffect parse(@NotNull PsiElement<?> text, @Nullable PsiElement<?> fileName, int lineNumber) {
            return create(text, fileName, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param text the text to log
         * @param fileName the name of the file to log to
         * @param lineNumber the line number
         * @return the effect
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiLogEffect create(@NotNull PsiElement<?> text, @Nullable PsiElement<?> fileName, int lineNumber) {
            return new PsiLogEffect(text, fileName, lineNumber);
        }
    }
}
