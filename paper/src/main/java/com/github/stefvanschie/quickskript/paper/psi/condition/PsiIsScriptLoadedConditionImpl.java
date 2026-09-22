package com.github.stefvanschie.quickskript.paper.psi.condition;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.condition.PsiIsScriptLoadedCondition;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.MultiResult;
import com.github.stefvanschie.quickskript.core.skript.ScriptManager;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.paper.plugin.QuickSkript;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the provided scripts are loaded.
 *
 * @since 0.1.0
 */
public class PsiIsScriptLoadedConditionImpl extends PsiIsScriptLoadedCondition {

    /**
     * Creates a new element with the given line number
     *
     * @param scripts the scripts to check if they are loaded
     * @param positive if false, the result is negated
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiIsScriptLoadedConditionImpl(@Nullable PsiElement<?> scripts, boolean positive, int lineNumber) {
        super(scripts, positive, lineNumber);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    protected Boolean executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        MultiResult<? extends String> scripts;

        if (super.scripts == null){
            if (context == null) {
                return false;
            }

            scripts = new MultiResult<>(context.getSkript().getName());
        } else {
            scripts = super.scripts.executeMulti(environment, context, String.class);
        }

        ScriptManager scriptManager = QuickSkript.getInstance().getScriptManager();

        return super.positive == scripts.test(scriptManager::isScriptLoaded);
    }

    /**
     * A factory for creating {@link PsiIsScriptLoadedConditionImpl}s
     *
     * @since 0.1.0
     */
    public static class Factory extends PsiIsScriptLoadedCondition.Factory {

        @NotNull
        @Contract(value = "_, _, _ -> new", pure = true)
        @Override
        public PsiIsScriptLoadedCondition create(@Nullable PsiElement<?> scripts, boolean positive, int lineNumber) {
            return new PsiIsScriptLoadedConditionImpl(scripts, positive, lineNumber);
        }
    }
}