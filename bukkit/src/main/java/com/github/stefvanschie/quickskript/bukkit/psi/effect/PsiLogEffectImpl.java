package com.github.stefvanschie.quickskript.bukkit.psi.effect;

import com.github.stefvanschie.quickskript.bukkit.plugin.QuickSkript;
import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.effect.PsiLogEffect;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
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
public class PsiLogEffectImpl extends PsiLogEffect {

    /**
     * Creates a new element with the given line number
     *
     * @param text       the text to log, see {@link #text}
     * @param fileName   the file name to write to, see {@link #fileName}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiLogEffectImpl(@NotNull PsiElement<?> text, @Nullable PsiElement<?> fileName, int lineNumber) {
        super(text, fileName, lineNumber);
    }

    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        var instance = QuickSkript.getInstance();
        String text = this.text.execute(context, Text.class).toString();

        if (fileName == null) {
            String prefix = context == null ? "" : '[' + context.getSkript().getName() + "] ";

            instance.getLogger().info(prefix + text);
        } else {
            String fileName = this.fileName.execute(context, Text.class).toString();

            if (!fileName.endsWith(".log")) {
                fileName += ".log";
            }

            if (fileName.equalsIgnoreCase("server.log")) {
                instance.getLogger().info(text);
            } else {
                try {
                    File parentFolder = new File(instance.getDataFolder(), "logs");

                    if (!parentFolder.exists() && !parentFolder.mkdirs()) {
                        throw new ExecutionException("Unable to create folder(s)", lineNumber);
                    }

                    File file = new File(parentFolder, fileName);

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
     * A factory for creating {@link PsiLogEffectImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiLogEffect.Factory {

        @NotNull
        @Contract(pure = true)
        @Override
        public PsiLogEffect create(@NotNull PsiElement<?> text, @Nullable PsiElement<?> fileName, int lineNumber) {
            return new PsiLogEffectImpl(text, fileName, lineNumber);
        }
    }
}
