package com.github.stefvanschie.quickskript.core.psi.util;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
    private final Pattern[] patterns;

    /**
     * The parameter types of this method
     */
    @NotNull
    private final Class<?>[] parameterTypes;

    /**
     * Creates a new cached reflection method by the provided method and patterns
     *
     * @param method the method to cache
     * @param patterns the patterns belonging to the method to cache
     * @since 0.1.0
     */
    public CachedReflectionMethod(
        @NotNull Method method,
        @NotNull com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern @NotNull [] patterns
    ) {
        if (patterns.length == 0) {
            throw new IllegalArgumentException("Method is not annotated with a Pattern annotation");
        }

        this.method = method;
        this.patterns = new Pattern[patterns.length];

        for (int index = 0; index < patterns.length; index++) {
            com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern pattern = patterns[index];

            this.patterns[index] = new Pattern(SkriptPattern.parse(pattern.value()), pattern.typeOrder());
        }

        this.parameterTypes = method.getParameterTypes();
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
    public Pattern[] getPatterns() {
        return this.patterns;
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
     * A pattern specified on this method.
     *
     * @since 0.1.0
     */
    public static class Pattern {

        /**
         * The pattern tha was specified.
         */
        @NotNull
        private final SkriptPattern pattern;

        /**
         * The ordering that was specified. See
         * {@link com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern#typeOrder()}.
         */
        private final int @NotNull [] order;

        /**
         * Creates a new pattern with the given {@link SkriptPattern} and the argument ordering.
         *
         * @param pattern the pattern
         * @param order the order
         * @since 0.1.0
         */
        public Pattern(@NotNull SkriptPattern pattern, int @NotNull [] order) {
            this.pattern = pattern;
            this.order = order;
        }

        /**
         * Gets the skript pattern.
         *
         * @return the skript pattern
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public SkriptPattern getSkriptPattern() {
            return pattern;
        }

        /**
         * Gets the order.
         *
         * @return the order
         * @since 0.1.0
         */
        @Contract(pure = true)
        public int @NotNull [] getOrder() {
            return order;
        }
    }
}
