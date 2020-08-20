package com.github.stefvanschie.quickskript.core.psi.section;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.file.skript.SkriptFileSection;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiSection;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.util.pointermovement.ExitSectionsPointerMovement;
import com.github.stefvanschie.quickskript.core.skript.Skript;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.profiler.SkriptProfiler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

/**
 * A section which acts as the entry point for Skript code.
 *
 * @since 0.1.0
 */
public class PsiBaseSection extends PsiSection {

    /**
     * The type of context of this entry point.
     */
    @NotNull
    private final Class<? extends Context> contextType;

    /**
     * The identifier of this entry point.
     */
    @NotNull
    private final SkriptProfiler.Identifier profilerIdentifier;

    /**
     * The skript this section is for
     */
    @NotNull
    private final Skript skript;

    /**
     * Creates a new Skript entry point.
     *
     * @param skript the Skript which contains this entry point
     * @param section the section this entry point should be parsed from
     * @param contextType the type of context of this entry point
     * @since 0.1.0
     */
    public PsiBaseSection(@NotNull SkriptLoader skriptLoader, @NotNull Skript skript,
        @NotNull SkriptFileSection section, @NotNull Class<? extends Context> contextType) {
        super(section.parseNodes(skriptLoader), section.getLineNumber());

        this.skript = skript;
        this.contextType = contextType;

        profilerIdentifier = new SkriptProfiler.Identifier(skript, section.getLineNumber());
    }

    @Nullable
    @Override
    protected ExitSectionsPointerMovement executeImpl(@Nullable Context context) {
        long startTime = System.nanoTime();

        for (PsiElement<?> element : elements) {
            Object result = element.execute(context);

            if (result == Boolean.FALSE) {
                break;
            }

            if (result instanceof ExitSectionsPointerMovement) {
                ExitSectionsPointerMovement pointerMovement = (ExitSectionsPointerMovement) result;
                ExitSectionsPointerMovement.Type type = pointerMovement.getType();

                if (type != ExitSectionsPointerMovement.Type.EVERYTHING) {
                    throw new ExecutionException(
                        "Tried to exit trigger, but found a " + type.name().toLowerCase(Locale.getDefault()), lineNumber
                    );
                }

                Integer amount = pointerMovement.getAmount();

                if (amount == null) {
                    return new ExitSectionsPointerMovement(type);
                }

                if (amount > 1) {
                    return new ExitSectionsPointerMovement(type, amount - 1);
                }

                return null;
            }
        }

        SkriptProfiler.getActive().onTimeMeasured(contextType, profilerIdentifier,
            System.nanoTime() - startTime);
        return null;
    }
}
