package com.github.stefvanschie.quickskript.psi.section;

import com.github.stefvanschie.quickskript.QuickSkript;
import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.file.SkriptFileSection;
import com.github.stefvanschie.quickskript.psi.PsiSection;
import com.github.stefvanschie.quickskript.skript.Skript;
import com.github.stefvanschie.quickskript.skript.profiler.SkriptProfiler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PsiBaseSection extends PsiSection {

    @NotNull
    private final Class<? extends Context> contextClass;

    @NotNull
    private final SkriptProfiler.Identifier profilerIdentifier;

    public PsiBaseSection(@NotNull Skript skript, @NotNull SkriptFileSection section,
            @NotNull Class<? extends Context> contextClass) {
        super(section.parseNodes(), section.getLineNumber());
        this.contextClass = contextClass;
        this.profilerIdentifier = new SkriptProfiler.Identifier(skript, section.getLineNumber());
    }

    @Nullable
    @Override
    protected Void executeImpl(@Nullable Context context) {
        long startTime = System.nanoTime();

        super.executeImpl(context);

        QuickSkript.getInstance().getSkriptProfiler().onTimeMeasured(contextClass,
                profilerIdentifier, System.nanoTime() - startTime);
        return null;
    }
}
