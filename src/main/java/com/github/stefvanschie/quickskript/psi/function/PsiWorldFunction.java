package com.github.stefvanschie.quickskript.psi.function;

import com.github.stefvanschie.quickskript.context.Context;
import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.util.text.Text;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Gets a world from a given string. This will never be pre computed, since there is no guarantee that any world
 * management plugin is loaded when this plugin is enabled.
 *
 * @since 0.1.0
 */
public class PsiWorldFunction extends PsiElement<World> {

    /**
     * The parameter for getting the world
     */
    private final PsiElement<?> parameter;

    /**
     * Creates a new world function
     *
     * @param parameter the parameter
     * @since 0.1.0
     */
    private PsiWorldFunction(PsiElement<?> parameter, int lineNumber) {
        super(lineNumber);

        this.parameter = parameter;
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public World executeImpl(@Nullable Context context) {
        return Bukkit.getWorld(parameter.execute(context, Text.class).construct());
    }

    /**
     * A factory for creating world functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory<PsiWorldFunction> {

        /**
         * The pattern for matching world function expressions
         */
        private final Pattern pattern = Pattern.compile("world\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiWorldFunction tryParse(@NotNull String text, int lineNumber) {
            Matcher matcher = pattern.matcher(text);

            if (!matcher.matches())
                return null;

            String expression = matcher.group(1);
            PsiElement<?> element = SkriptLoader.get().forceParseElement(expression, lineNumber);

            return new PsiWorldFunction(element, lineNumber);
        }
    }
}
