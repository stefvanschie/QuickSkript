package com.github.stefvanschie.quickskript.psi.function;

import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.psi.PsiFactory;
import com.github.stefvanschie.quickskript.psi.exception.ParseException;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Gets a world from a given string
 *
 * @since 0.1.0
 */
public class PsiWorldFunction implements PsiElement<World> {

    /**
     * The parameter for getting the world
     */
    private PsiElement<String> parameter;

    /**
     * Creates a new world function
     *
     * @param parameter the parameter
     * @since 0.1.0
     */
    private PsiWorldFunction(PsiElement<String> parameter) {
        this.parameter = parameter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public World execute() {
        return Bukkit.getWorld(parameter.execute());
    }

    /**
     * A factory for creating world functions
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiFactory<PsiWorldFunction> {

        /**
         * The pattern for matching world function expressions
         */
        private static final Pattern PATTERN = Pattern.compile("world\\(([\\s\\S]+)\\)");

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public PsiWorldFunction parse(@NotNull String text) {
            Matcher matcher = PATTERN.matcher(text);

            if (!matcher.matches())
                return null;

            String expression = matcher.group(1);
            PsiElement<String> element = (PsiElement<String>) PsiElementFactory.parseText(expression, String.class);

            if (element == null)
                throw new ParseException("Function was unable to find an expression named " + expression);

            return new PsiWorldFunction(element);
        }
    }

    static {
        PsiElementFactory.getClassTypes().put(PsiWorldFunction.class, World.class);
    }
}
