package com.github.stefvanschie.quickskript.core.pattern.group;

import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.util.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Matches a space character (U+0020). No other spaces should be matched by this group; those belong to
 * {@link LiteralGroup}. This special group is needed, because of the special cases with {@link OptionalGroup}s and
 * spaces around them.
 *
 * @since 0.1.0
 */
public class SpaceGroup implements SkriptPatternGroup {

    @NotNull
    @Contract(pure = true)
    @Override
    public List<SkriptMatchResult> match(@NotNull SkriptPatternGroup[] followingGroups, @NotNull String input) {
        List<SkriptMatchResult> results = new ArrayList<>();
        int groupLength = followingGroups.length;

        if (groupLength >= 1 && followingGroups[0] instanceof OptionalGroup) {
            if (input.length() > 0 && input.charAt(0) == ' ') {
                String subInput = input.substring(" ".length());
                SkriptPatternGroup[] firstRemovedArray = Arrays.copyOfRange(followingGroups, 1, groupLength);
                List<SkriptMatchResult> calleeResults = followingGroups[0].match(firstRemovedArray, subInput);

                calleeResults.forEach(result -> result.addMatchedGroup(this, " ", 0));

                results.addAll(calleeResults);
            }

            if (groupLength >= 2) {
                SkriptPatternGroup[] newArray = Arrays.copyOfRange(followingGroups, 2, groupLength);
                results.addAll(followingGroups[1].match(newArray, input));
            } else {
                SkriptMatchResult result = new SkriptMatchResult();
                result.setRestingString(input);
                results.add(result);
            }
        } else if (input.startsWith(" ")) {
            String subInput = input.substring(" ".length());

            if (groupLength == 0) {
                SkriptMatchResult result = new SkriptMatchResult();
                result.addMatchedGroup(this, " ", 0);
                result.setRestingString(subInput);

                results.add(result);
            } else {
                SkriptPatternGroup[] firstRemovedArray = Arrays.copyOfRange(followingGroups, 1, groupLength);
                List<SkriptMatchResult> calleeResults = followingGroups[0].match(firstRemovedArray, subInput);

                calleeResults.forEach(result -> result.addMatchedGroup(this, " ", 0));

                results.addAll(calleeResults);
            }
        }

        return results;
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public List<SkriptPatternGroup> getChildren() {
        return Collections.emptyList();
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public <T extends SkriptPatternGroup> List<T> getGroups(Class<T> groupClass) {
        if (getClass() == groupClass) {
            return Collections.singletonList((T) this);
        }

        return Collections.emptyList();
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public Collection<String> unrollFully(@NotNull SkriptPatternGroup @NotNull [] groups) {
        if (groups.length == 0) {
            return Collections.singleton(" ");
        }

        int index = 0;

        Collection<String> matches = new HashSet<>();

        matches.add(" ");

        Collection<String> newMatches = new HashSet<>();
        Collection<String> strings;

        if (groups[0] instanceof OptionalGroup) {
            strings = groups[0].unrollFully(new SkriptPatternGroup[0]);

            for (String string : strings) {
                if (string.isEmpty()) {
                    newMatches.add("");
                } else {
                    newMatches.add(" " + string);
                }
            }

            if (groups.length == 1) {
                return newMatches;
            }

            matches = new HashSet<>(newMatches);
            newMatches.clear();
            index++;
        }

        strings = groups[index].unrollFully(Arrays.copyOfRange(groups, index + 1, groups.length));

        for (String match : matches) {
            for (String string : strings) {
                newMatches.add(match + string);
            }
        }

        return newMatches;
    }

    /**
     * Parses the group at the starting of the match, returning the correct group if it matches correctly. This will
     * only match if the match is at the start and continuous i.e. there are no gaps in the match.
     *
     * @param input the input to match against
     * @return the created group and the input stripped with the match if successful, null if the match was
     *         unsuccessful.
     * @since 0.1.0
     */
    @Nullable
    public static Pair<SpaceGroup, StringBuilder> parseStarting(@NotNull StringBuilder input) {
        if (input.charAt(0) == ' ') {
            return new Pair<>(new SpaceGroup(), input.deleteCharAt(0));
        }

        return null;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        return object != null && getClass() == object.getClass();
    }
}