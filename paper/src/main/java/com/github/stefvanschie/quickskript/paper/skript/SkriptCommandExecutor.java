package com.github.stefvanschie.quickskript.paper.skript;

import com.github.stefvanschie.quickskript.paper.plugin.QuickSkript;
import com.github.stefvanschie.quickskript.paper.context.CommandContextImpl;
import com.github.stefvanschie.quickskript.paper.skript.util.ExecutionTarget;
import com.github.stefvanschie.quickskript.core.context.CommandContext;
import com.github.stefvanschie.quickskript.core.file.skript.SkriptFileSection;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.section.PsiBaseSection;
import com.github.stefvanschie.quickskript.core.skript.Skript;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

/**
 * Represents an arbitrary skript command handler.
 *
 * @since 0.1.0
 */
public class SkriptCommandExecutor implements CommandExecutor {

    /**
     * The skript this command belongs to
     */
    @NotNull
    private final Skript skript;

    /**
     * The run environment this executor operates in
     */
    @NotNull
    private final SkriptRunEnvironment environment;

    /**
     * The elements that should get executed
     */
    @NotNull
    private final PsiBaseSection elements;

    /**
     * Specifies the execution target. When null, everything/everyone can use this command.
     */
    @Nullable
    private final ExecutionTarget executionTarget;

    /**
     * Constructs a new skript command from the given file section. The file section should match with the 'trigger'
     * part in a skript file.
     *
     * @param skript the source of this command executor code
     * @param environment the environment this executor operates in
     * @param section the file section to load the elements from
     * @param executionTarget the group which can execute this command
     * @since 0.1.0
     */
    SkriptCommandExecutor(@NotNull SkriptLoader skriptLoader, @NotNull SkriptRunEnvironment environment,
            @NotNull Skript skript, @NotNull SkriptFileSection section, @Nullable ExecutionTarget executionTarget) {
        this.skript = skript;
        this.environment = environment;
        this.executionTarget = executionTarget;
        elements = new PsiBaseSection(skriptLoader, skript, section, CommandContext.class);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (executionTarget != null && !executionTarget.matches(sender)) {
            return false;
        }

        try {
            elements.execute(environment, new CommandContextImpl(skript, sender));
        } catch (ExecutionException e) {
            QuickSkript.getInstance().getLogger().log(Level.SEVERE, "Error while executing skript:", e);
        }
        return true;
    }
}
