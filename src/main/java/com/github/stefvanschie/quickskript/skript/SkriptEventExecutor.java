package com.github.stefvanschie.quickskript.skript;

import com.github.stefvanschie.quickskript.QuickSkript;
import com.github.stefvanschie.quickskript.context.EventContext;
import com.github.stefvanschie.quickskript.file.SkriptFileSection;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.skript.profiler.SkriptProfiler;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.List;
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
     * Constructs a new skript event.
     *
     * @param skript the source of this event handler code
     * @param section the file section to load the elements from
     * @since 0.1.0
     */
    SkriptEventExecutor(@NotNull Skript skript, @NotNull SkriptFileSection section) {
        this.skript = skript;
        profilerIdentifier = new SkriptProfiler.Identifier(skript, section.getLineNumber());
        elements = section.parseNodes();
    }

    /**
     * Executes the contents of this event
     *
     * @param event the event being executed
     * @since 0.1.0
     */
    public void execute(@NotNull Event event) {
        EventContext context = new EventContext(event);
        long startTime = System.nanoTime();

        try {
            for (PsiElement<?> element : elements) {
                if (element.execute(context) == Boolean.FALSE)
                    break;
            }
        } catch (ExecutionException e) {
            QuickSkript.getInstance().getLogger().log(Level.SEVERE, "Error while executing:" +
                    e.getExtraInfo(skript.getName()), e);
            return;
        }

        QuickSkript.getInstance().getSkriptProfiler().onTimeMeasured(EventContext.class,
                profilerIdentifier, System.nanoTime() - startTime);
    }
}
