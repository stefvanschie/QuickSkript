package com.github.stefvanschie.quickskript.core.pattern.group;

import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
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

    /**
     * Sees if the input matches according to this group. This may partially match with trailing, unmatched characters
     * at the end, only if this is not the last group (followingGroups is not empty). If this is the last group
     * (followingGroups is empty), the rest of the input should be consumed in its entirety.
     *
     * This method returns a {@link SkriptMatchResult} if a successful match has been found. If this returns null, this,
     * or any called groups, were unable to match the given input, while satisfying their group's respective
     * restrictions. The list containing these results is mutable.
     *
     * Once this method has found a way to consume the input, it should call this method on the next appropriate group
     * from followingGroups by itself. The passed in followingGroups should be the same, however the first element
     * should be removed (the array needs to be resized as well, the first element may not be null). The input passed
     * should be the same input, with the characters that this method consumed removed. Once this called method returns
     * a list of {@link SkriptMatchResult}, the caller should add itself to every one of these by calling
     * {@link SkriptMatchResult#addMatchedGroup(SkriptPatternGroup, String, int)}. After this, the method should try yet
     * another combination and call any group after it again. If there are no possible combinations, the method should
     * return an empty list. All returned lists should be combined into one and, once there are no possibilities left,
     * be returned to the calling method. If this is the last group, it should create a new {@link SkriptMatchResult}
     * for every possible combination, add those to a list and return those.
     *
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    List<SkriptMatchResult> match(@NotNull SkriptPatternGroup[] followingGroups, @NotNull String input);
}
