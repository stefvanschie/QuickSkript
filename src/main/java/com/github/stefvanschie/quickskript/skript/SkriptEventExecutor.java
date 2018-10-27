package com.github.stefvanschie.quickskript.skript;

import com.github.stefvanschie.quickskript.QuickSkript;
import com.github.stefvanschie.quickskript.context.EventContext;
import com.github.stefvanschie.quickskript.file.SkriptFileSection;
import com.github.stefvanschie.quickskript.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.psi.section.PsiBaseSection;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

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
     * The elements that should get executed
     */
    @NotNull
    private final PsiBaseSection elements;

    /**
     * Constructs a new skript event.
     *
     * @param skript the source of this event handler code
     * @param section the file section to load the elements from
     * @since 0.1.0
     */
    SkriptEventExecutor(@NotNull Skript skript, @NotNull SkriptFileSection section) {
        this.skript = skript;
        elements = new PsiBaseSection(skript, section, EventContext.class);
    }

    /**
     * Executes the contents of this event
     *
     * @param event the event being executed
     * @since 0.1.0
     */
    public void execute(@NotNull Event event) {
        try {
            elements.execute(new EventContext(event));
        } catch (ExecutionException e) {
            QuickSkript.getInstance().getLogger().log(Level.SEVERE, "Error while executing:" +
                    e.getExtraInfo(skript.getName()), e);
        }
    }
}
