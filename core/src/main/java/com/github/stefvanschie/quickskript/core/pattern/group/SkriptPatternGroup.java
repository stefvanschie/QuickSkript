package com.github.stefvanschie.quickskript.core.pattern.group;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Indicates a group of a skript pattern
 *
 * @since 0.1.0
 */
public interface SkriptPatternGroup {

    /**
     * Gets all children of this group and all nested children. The returned list is immutable.
     *
     * @return all children
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    List<SkriptPatternGroup> getChildren();
}
