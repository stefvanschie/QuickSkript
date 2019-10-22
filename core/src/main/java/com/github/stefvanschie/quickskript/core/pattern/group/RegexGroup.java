package com.github.stefvanschie.quickskript.core.pattern.group;

import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.pattern.exception.SkriptPatternParseException;
import com.github.stefvanschie.quickskript.core.util.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A group that matches according to a regular expression.
 *
 * @since 0.1.0
 */
public class RegexGroup implements SkriptPatternGroup {

    /**
     * The pattern of this regex group
     */
    @NotNull
    private final Pattern pattern;

    /**
     * Creates a new regex group
     *
     * @param pattern the pattern to match
     * @since 0.1.0
     */
    private RegexGroup(@NotNull String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public List<SkriptMatchResult> match(@NotNull SkriptPatternGroup[] followingGroups, @NotNull String input) {
        List<SkriptMatchResult> results = new ArrayList<>();
        StringBuilder builder = new StringBuilder(input);

        do {
            String subInput = builder.toString();
            Matcher matcher = pattern.matcher(subInput);

            matcher.results().forEach(matchResult -> {
                if (matcher.start() != 0) {
                    return;
                }

                String match = matcher.group();
                int subIndex = Math.min(matcher.end(), builder.length());

                if (followingGroups.length == 0) {
                    SkriptMatchResult result = new SkriptMatchResult();
                    result.addMatchedGroup(this, match, 0);
                    result.setRestingString(input.substring(subIndex));

                    results.add(result);
                    return;
                }

                SkriptPatternGroup[] newArray = Arrays.copyOfRange(followingGroups, 1, followingGroups.length);
                List<SkriptMatchResult> calleeResults = followingGroups[0].match(newArray, input.substring(subIndex));

                calleeResults.forEach(result -> result.addMatchedGroup(this, match, 0));

                results.addAll(calleeResults);
            });

            //in case we got passed an empty input
            if (builder.length() > 0) {
                builder.deleteCharAt(builder.length() - 1);
            }
        } while (builder.length() > 0);

        return results;
    }

    /**
     * Gets the pattern this group holds
     *
     * @return the pattern
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public Pattern getPattern() {
        return pattern;
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public List<SkriptPatternGroup> getChildren() {
        return Collections.emptyList();
    }

    /**
     * Parses the group at the starting of the match, returning the correct group if it matches correctly. This will
     * only match if the match is at the start and continuous i.e. there are no gaps in the match.
     *
     * @param input the input to match against
     * @return the created group and the input stripped with the match if successful, null if the match was
     *         unsuccessful.
     */
    @Nullable
    public static Pair<RegexGroup, String> parseStarting(@NotNull String input) {
        if (!input.startsWith("<")) {
            return null;
        }

        int endCharacter = input.indexOf('>');

        if (endCharacter == -1) {
            throw new SkriptPatternParseException(
                "Expected an ending regex group character '>' at index " + input.length() + " but wasn't found"
            );
        }

        return new Pair<>(new RegexGroup(input.substring(1, endCharacter)), input.substring(endCharacter + 1));
    }
}
