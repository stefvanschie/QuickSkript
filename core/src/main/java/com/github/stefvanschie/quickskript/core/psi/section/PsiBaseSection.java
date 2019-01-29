package com.github.stefvanschie.quickskript.core.psi.section;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.file.SkriptFileSection;
import com.github.stefvanschie.quickskript.core.psi.PsiSection;
import com.github.stefvanschie.quickskript.core.skript.Skript;
import com.github.stefvanschie.quickskript.core.skript.profiler.SkriptProfiler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    public PsiBaseSection(@NotNull Skript skript, @NotNull SkriptFileSection section,
                          @NotNull Class<? extends Context> contextType) {
        super(section.parseNodes(), section.getLineNumber());

        this.skript = skript;
        this.contextType = contextType;

        profilerIdentifier = new SkriptProfiler.Identifier(skript, section.getLineNumber());
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        long startTime = System.nanoTime();

        super.executeImpl(context);

        skript.getSkriptProfiler().onTimeMeasured(contextType, profilerIdentifier,
            System.nanoTime() - startTime);
        return null;
    }
}
