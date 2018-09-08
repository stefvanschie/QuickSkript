package com.github.stefvanschie.quickskript.skript;

import com.github.stefvanschie.quickskript.QuickSkript;
import com.github.stefvanschie.quickskript.context.EventContext;
import com.github.stefvanschie.quickskript.file.SkriptFileSection;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.exception.ExecutionException;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * Represents an arbitrary skript event handler.
 *
 * @since 0.1.0
 */
public class SkriptEventExecutor {

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
     * Constructs a new skript event.
     *
     * @param skript the source of this event handler code
     * @param section the file section to load the elements from
     * @since 0.1.0
     */
    SkriptEventExecutor(@NotNull Skript skript, @NotNull SkriptFileSection section) {
        this.skript = skript;

        elements = section.getNodes().stream()
                .map(node -> SkriptLoader.get().forceParseElement(node.getText(), node.getLineNumber()))
                .collect(Collectors.toList());
    }

    /**
     * Executes the contents of this event
     *
     * @param event the event being executed
     */
    public void execute(@NotNull Event event) {
        EventContext context = new EventContext(event);

        try {
            elements.forEach(element -> element.execute(context));
        } catch (ExecutionException e) {
            QuickSkript.getInstance().getLogger().log(Level.SEVERE, "Error while executing skript named " + skript.getName(), e);
        }
    }
}
