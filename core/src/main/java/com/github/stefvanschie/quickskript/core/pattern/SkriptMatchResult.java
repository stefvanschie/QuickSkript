package com.github.stefvanschie.quickskript.core.pattern;

import com.github.stefvanschie.quickskript.core.pattern.group.ChoiceGroup;
import com.github.stefvanschie.quickskript.core.pattern.group.OptionalGroup;
import com.github.stefvanschie.quickskript.core.pattern.group.SkriptPatternGroup;
import com.github.stefvanschie.quickskript.core.util.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

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
    private final List<Pair<SkriptPatternGroup, String>> matchedGroups = new ArrayList<>();

    /**
     * The final parse mark of the match
     */
    private int parseMark;

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
    public void addMatchedGroup(@NotNull SkriptPatternGroup group, @NotNull String text, int index) {
        matchedGroups.add(index, new Pair<>(group, text));
    }

    /**
     * Adds a parse mark to the final parse mark
     *
     * @param parseMark the parse mark
     * @throws IllegalStateException if this match was already terminated
     * @since 0.1.0
     */
    public void addParseMark(int parseMark) {
        this.parseMark ^= parseMark;
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

        matchedGroups.forEach(pair -> {
            SkriptPatternGroup skriptPatternGroup = pair.getX();

            if (skriptPatternGroup instanceof ChoiceGroup || skriptPatternGroup instanceof OptionalGroup) {
                return;
            }

            builder.append(pair.getY());
        });

        return builder.toString();
    }

    /**
     * Produces a shallow copy of this SkriptMatchResult.
     *
     * @return a copy of this
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public SkriptMatchResult shallowCopy() {
        SkriptMatchResult result = new SkriptMatchResult();
        result.matchedGroups.addAll(matchedGroups);
        result.restingString = restingString;
        result.parseMark = parseMark;

        return result;
    }

    /**
     * Gets the string that matched the specified group. Null will be returned if the specified group wasn't matched.
     *
     * @param group the group that was matched
     * @return the string that matched this group
     * @since 0.1.0
     */
    @Nullable
    @Contract(pure = true)
    public String getMatchedString(@NotNull SkriptPatternGroup group) {
        return matchedGroups.stream()
            .filter(pair -> pair.getX().equals(group))
            .map(Pair::getY)
            .findAny()
            .orElse(null);
    }

    /**
     * Checks whether this matched has unmatched parts left. If so, this method returns true, if not this returns false.
     *
     * @return whether this math has unmatched parts
     * @since 0.1.0
     */
    @Contract(pure = true)
    public boolean hasUnmatchedParts() {
        return restingString != null && !restingString.isEmpty();
    }

    /**
     * Gets a list of the matched groups and the strings that were matched. This is a copy of the original list and is
     * immutable.
     *
     * @return the matched groups
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public List<Pair<SkriptPatternGroup, String>> getMatchedGroups() {
        return Collections.unmodifiableList(matchedGroups);
    }

    /**
     * Sets the resting string of this match
     *
     * @param restingString the resting string
     * @since 0.1.0
     */
    public void setRestingString(@NotNull String restingString) {
        this.restingString = restingString;
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
}
