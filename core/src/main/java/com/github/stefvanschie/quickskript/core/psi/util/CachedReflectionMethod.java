package com.github.stefvanschie.quickskript.core.psi.util;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.PatternTypeOrderHolder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;

/**
 * A cached method belonging to a psi element factory
 *
 * @since 0.1.0
 */
public class CachedReflectionMethod {

    /**
     * The cached method
     */
    @NotNull
    private final Method method;

    /**
     * The patterns belonging to this method
     */
    @NotNull
    private final SkriptPattern[] patterns;

    /**
     * The parameter types of this method
     */
    @NotNull
    private final Class<?>[] parameterTypes;

    /**
     * The pattern type order holder of this method
     */
    @Nullable
    private final PatternTypeOrderHolder holder;

    /**
     * Creates a new cached reflection method by the provided method and patterns
     *
     * @param method the method to cache
     * @param patterns the patterns belonging to the method to cache
     * @since 0.1.0
     */
    public CachedReflectionMethod(@NotNull Method method, @NotNull SkriptPattern[] patterns) {
        this.method = method;
        this.patterns = patterns;
        this.parameterTypes = method.getParameterTypes();
        this.holder = method.getAnnotation(PatternTypeOrderHolder.class);
    }

    /**
     * Gets the method that was cached
     *
     * @return the method
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public Method getMethod() {
        return method;
    }

    /**
     * Gets the patterns that were cached
     *
     * @return the patterns
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public SkriptPattern[] getPatterns() {
        return patterns;
    }

    /**
     * Gets the parameter types that were cached
     *
     * @return the parameter types
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    /**
     * Gets the pattern type order holder that were cached or null if no type order was specified
     *
     * @return the pattern type order holder
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public PatternTypeOrderHolder getPatternTypeOrderHolder() {
        return holder;
    }
}
