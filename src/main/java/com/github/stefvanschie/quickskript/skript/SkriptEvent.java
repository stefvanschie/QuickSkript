package com.github.stefvanschie.quickskript.skript;

import com.github.stefvanschie.quickskript.context.EventContext;
import com.github.stefvanschie.quickskript.file.SkriptFileSection;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents an event we're listening for.
 *
 * @since 0.1.0
 */
public class SkriptEvent {

    /**
     * A list of elements that should get executed
     */
    private final List<PsiElement<?>> elements;

    /**
     * Constructs a new skript event.
     *
     * @param section the file section to load the elements from
     * @since 0.1.0
     */
    SkriptEvent(@NotNull SkriptFileSection section) {
        elements = section.getNodes().stream()
                .filter(node -> node.getText() != null)
                .map(node -> SkriptLoader.get().tryParseElement(node.getText()))
                .collect(Collectors.toList());
    }

    /**
     * Executes the contents of this event
     */
    public void execute(@NotNull Event event) {
        EventContext context = new EventContext(event);

        elements.forEach(element -> element.execute(context));
    }
}
