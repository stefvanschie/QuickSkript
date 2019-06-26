package com.github.stefvanschie.quickskript.core.pattern;

import com.github.stefvanschie.quickskript.core.pattern.group.ChoiceGroup;
import com.github.stefvanschie.quickskript.core.pattern.group.OptionalGroup;
import com.github.stefvanschie.quickskript.core.pattern.group.SkriptPatternGroup;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A class holding information about an attempted match
 *
 * @since 0.1.0
 */
public class SkriptMatchResult {

    /**
     * An ordered list of all groups that were matched and the exact part that matched
     */
    @NotNull
    private final Map<SkriptPatternGroup, String> matchedGroups = new LinkedHashMap<>();

    /**
     * The final parse mark of the match
     */
    private int parseMark;

    /**
     * True if this match was successful, otherwise false
     */
    private boolean success;

    /**
     * The group that failed to match
     */
    @Nullable
    private SkriptPatternGroup failingGroup;

    /**
     * The resting string
     */
    @Nullable
    private String restingString;

    /**
     * Adds a group that was successfully matched
     *
     * @param group the group that was matched
     * @param text the text that made the group match
     * @throws IllegalStateException if this match was already terminated
     * @since 0.1.0
     */
    public void addMatchedGroup(@NotNull SkriptPatternGroup group, @NotNull String text) {
        if (success || failingGroup != null) {
            throw new IllegalStateException("Match was already terminated, cannot change it anymore");
        }

        matchedGroups.put(group, text);
    }

    /**
     * Removes a group from the successful matches. This is useful when doing forward tracking when matching patterns
     * and you need to remove a group that was possibly correct, but appears not to be.
     *
     * @param group the group to remove
     * @since 0.1.0
     */
    public void removeMatchedGroup(@NotNull SkriptPatternGroup group) {
        if (success || failingGroup != null) {
            throw new IllegalStateException("Match was already terminated, cannot change it anymore");
        }

        matchedGroups.remove(group);
    }

    /**
     * Adds a parse mark to the final parse mark
     *
     * @param parseMark the parse mark
     * @throws IllegalStateException if this match was already terminated
     * @since 0.1.0
     */
    public void addParseMark(int parseMark) {
        if (success || failingGroup != null) {
            throw new IllegalStateException("Match was already terminated, cannot change it anymore");
        }

        this.parseMark ^= parseMark;
    }

    /**
     * Marks this match as failed and sets the group that caused this failure. This is a terminating operation. After
     * invoking this method, this match can no longer be changed.
     *
     * @param failingGroup the group that failed to match
     * @throws IllegalStateException if this match was already terminated
     * @since 0.1.0
     */
    public void failure(SkriptPatternGroup failingGroup) {
        if (success || this.failingGroup != null) {
            throw new IllegalStateException("Match was already terminated, cannot change it anymore");
        }

        this.failingGroup = failingGroup;
    }

    /**
     * Marks this match as successful. This is a terminating operation. After invoking this method, this match can no
     * longer be changed.
     *
     * @throws IllegalStateException if this match was already terminated
     * @since 0.1.0
     */
    public void success(@Nullable String restingString) {
        if (success || failingGroup != null) {
            throw new IllegalStateException("Match was already terminated, cannot change it anymore");
        }

        success = true;
        this.restingString = restingString;
    }

    /**
     * Gets the string that was matched
     *
     * @return the matches string
     */
    @NotNull
    @Contract(pure = true)
    public String getMatchedString() {
        StringBuilder builder = new StringBuilder();

        matchedGroups.forEach((skriptPatternGroup, text) -> {
            if (skriptPatternGroup instanceof ChoiceGroup || skriptPatternGroup instanceof OptionalGroup) {
                return;
            }

            builder.append(text);
        });

        return builder.toString();
    }

    /**
     * Gets a map of the matched groups and the strings that were matched
     *
     * @return the matched groups
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public Map<SkriptPatternGroup, String> getMatchedGroups() {
        return matchedGroups;
    }

    /**
     * Gets the resting string, or null if none is present
     *
     * @return the resting string
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public String getRestingString() {
        return restingString;
    }

    /**
     * Gets the parse mark of this match, or up to the point this match failed
     *
     * @return the parse mark
     * @since 0.1.0
     */
    @Contract(pure = true)
    public int getParseMark() {
        return parseMark;
    }

    /**
     * Whether this match was successful or not, true if it was, false otherwise
     *
     * @return whether this match was succesful
     * @since 0.1.0
     */
    @Contract(pure = true)
    public boolean isSuccessful() {
        return success;
    }
}
