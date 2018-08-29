package com.github.stefvanschie.quickskript.skript;

import com.github.stefvanschie.quickskript.context.EventContext;
import com.github.stefvanschie.quickskript.file.SkriptFileSection;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an event we're listening for.
 *
 * @since 0.1.0
 */
public class SkriptEvent {

    /**
     * A list of elements that should get executed
     */
    private List<PsiElement<?>> elements;

    /**
     * Constructs a new skript event.
     *
     * @param section the file section to load the elements from
     * @since 0.1.0
     */
    SkriptEvent(@NotNull SkriptFileSection section) {
        elements = new ArrayList<>(section.getNodes().size());

        section.getNodes().stream()
            .filter(node -> node.getText() != null)
            .forEach(node -> elements.add(PsiElementFactory.parseText(node.getText())));
    }

    /**
     * Executes the contents of this event
     */
    public void execute() {
        EventContext context = new EventContext();

        elements.forEach(element -> element.execute(context));
    }
}
