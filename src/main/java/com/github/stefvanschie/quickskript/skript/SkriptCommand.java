package com.github.stefvanschie.quickskript.skript;

import com.github.stefvanschie.quickskript.QuickSkript;
import com.github.stefvanschie.quickskript.context.CommandContext;
import com.github.stefvanschie.quickskript.file.SkriptFileSection;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.skript.util.ExecutionTarget;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * Represents an arbitrary skript command
 *
 * @since 0.1.0
 */
public class SkriptCommand implements CommandExecutor {

    /**
     * The skript this command belongs to
     */
    @NotNull
    private final Skript skript;

    /**
     * A list of elements that should get executed
     */
    private final List<PsiElement<?>> elements;

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
     * @param section the file section to load the elements from
     * @param executionTarget the group which can execute this command
     * @since 0.1.0
     */
    SkriptCommand(@NotNull Skript skript, @NotNull SkriptFileSection section, @Nullable ExecutionTarget executionTarget) {
        this.skript = skript;
        this.executionTarget = executionTarget;

        elements = section.getNodes().stream()
                .filter(node -> node.getText() != null)
                .map(node -> SkriptLoader.get().forceParseElement(node.getText()))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (executionTarget != null &&
                ((executionTarget == ExecutionTarget.CONSOLE && !(sender instanceof ConsoleCommandSender)) ||
                        (executionTarget == ExecutionTarget.PLAYERS && !(sender instanceof Player)) ||
                        (executionTarget == ExecutionTarget.CONSOLE_AND_PLAYERS &&
                                !(sender instanceof Player) && !(sender instanceof ConsoleCommandSender))))
            return false;

        CommandContext context = new CommandContext(sender);

        try {
            elements.forEach(element -> element.execute(context));
        } catch (ExecutionException e) {
            QuickSkript.getInstance().getLogger().log(Level.SEVERE, "Error while executing skript named " + skript.getName(), e);
        }

        return true;
    }
}
