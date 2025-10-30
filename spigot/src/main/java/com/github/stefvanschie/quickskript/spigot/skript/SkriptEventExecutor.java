package com.github.stefvanschie.quickskript.spigot.skript;

import com.github.stefvanschie.quickskript.spigot.plugin.QuickSkript;
import com.github.stefvanschie.quickskript.spigot.context.EventContextImpl;
import com.github.stefvanschie.quickskript.core.context.EventContext;
import com.github.stefvanschie.quickskript.core.file.skript.SkriptFileSection;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.section.PsiBaseSection;
import com.github.stefvanschie.quickskript.core.skript.Skript;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
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
     * The skript this event belongs to
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
     * Constructs a new skript event.
     *
     * @param skript the source of this event handler code
     * @param environment the environment this executor operates in
     * @param section the file section to load the elements from
     * @since 0.1.0
     */
    SkriptEventExecutor(@NotNull SkriptLoader skriptLoader, @NotNull SkriptRunEnvironment environment,
            @NotNull Skript skript, @NotNull SkriptFileSection section) {
        this.skript = skript;
        this.environment = environment;
        elements = new PsiBaseSection(skriptLoader, skript, section, EventContext.class);
    }

    /**
     * Executes the contents of this event
     *
     * @param event the event being executed
     * @since 0.1.0
     */
    public void execute(@NotNull Event event) {
        try {
            elements.execute(environment, new EventContextImpl(skript, event));
        } catch (ExecutionException e) {
            QuickSkript.getInstance().getLogger().log(Level.SEVERE, "Error while executing", e);
        }
    }

    /**
     * Gets the script this event executor is for.
     *
     * @return the script
     * @since 0.1.0
     */
    @NotNull
    public Skript getScript() {
        return this.skript;
    }
}
