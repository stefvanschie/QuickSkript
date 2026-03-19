package com.github.stefvanschie.quickskript.spigot.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiScriptIsLoadedCondition;
import com.github.stefvanschie.quickskript.core.skript.ScriptManager;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.spigot.plugin.QuickSkript;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if a script is loaded.
 *
 * @since 0.1.0
 */
public class PsiScriptIsLoadedConditionImpl extends PsiScriptIsLoadedCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param scripts the scripts to check if they are loaded
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiScriptIsLoadedConditionImpl(@NotNull PsiElement<?> scripts, boolean positive, int lineNumber) {
        super(scripts, positive, lineNumber);
    }

    @NotNull
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        ScriptManager scriptManager = QuickSkript.getInstance().getScriptManager();

        return super.positive == super.scripts.executeMulti(environment, context).test(scriptManager::isScriptLoaded);
    }

    /**
     * A factory for creating instances of {@link PsiScriptIsLoadedConditionImpl}.
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiScriptIsLoadedCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        @Override
        public PsiScriptIsLoadedConditionImpl create(@NotNull PsiElement<?> scripts, boolean positive, int lineNumber) {
            return new PsiScriptIsLoadedConditionImpl(scripts, positive, lineNumber);
        }
    }
}
