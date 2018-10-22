package com.github.stefvanschie.quickskript.skript;

import com.github.stefvanschie.quickskript.QuickSkript;
import com.github.stefvanschie.quickskript.context.CommandContext;
import com.github.stefvanschie.quickskript.file.SkriptFileSection;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.skript.profiler.SkriptProfiler;
import com.github.stefvanschie.quickskript.skript.util.ExecutionTarget;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
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
     * The identifier of this instance to use with the {@link SkriptProfiler}
     */
    @NotNull
    private final SkriptProfiler.Identifier profilerIdentifier;

    /**
     * A list of elements that should get executed
     */
    @NotNull
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
    SkriptCommandExecutor(@NotNull Skript skript, @NotNull SkriptFileSection section, @Nullable ExecutionTarget executionTarget) {
        this.skript = skript;
        profilerIdentifier = new SkriptProfiler.Identifier(skript, section.getLineNumber());
        this.executionTarget = executionTarget;
        elements = section.parseNodes();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (executionTarget != null && !executionTarget.matches(sender))
            return false;

        CommandContext context = new CommandContext(sender);
        long startTime = System.nanoTime();

        try {
            for (PsiElement<?> element : elements) {
                if (element.execute(context) == Boolean.FALSE)
                    break;
            }
        } catch (ExecutionException e) {
            QuickSkript.getInstance().getLogger().log(Level.SEVERE, "Error while executing skript:" +
                    e.getExtraInfo(skript.getName()), e);
            return true;
        }

        QuickSkript.getInstance().getSkriptProfiler().onTimeMeasured(CommandContext.class,
                profilerIdentifier, System.nanoTime() - startTime);
        return true;
    }
}
