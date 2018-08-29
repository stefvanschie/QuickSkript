package com.github.stefvanschie.quickskript.skript;

import com.github.stefvanschie.quickskript.QuickSkript;
import com.github.stefvanschie.quickskript.context.CommandContext;
import com.github.stefvanschie.quickskript.file.SkriptFileSection;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an arbitrary skript command
 *
 * @since 0.1.0
 */
public class SkriptCommand implements CommandExecutor {

    /**
     * A list of elements that should get executed
     */
    private List<PsiElement<?>> elements;

    /**
     * Constructs a new skript command from the given file section. The file section should match with the 'trigger'
     * part in a skript file.
     *
     * @param section the file section to laod the elements from
     * @since 0.1.0
     */
    SkriptCommand(@NotNull SkriptFileSection section) {
        elements = new ArrayList<>(section.getNodes().size());

        section.getNodes().stream()
            .filter(node -> node.getText() != null)
            .forEach(node -> elements.add(PsiElementFactory.parseText(node.getText())));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CommandContext context = new CommandContext(sender);

        elements.forEach(element -> element.execute(context));

        return true;
    }
}
