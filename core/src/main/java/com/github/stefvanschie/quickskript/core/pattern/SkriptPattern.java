package com.github.stefvanschie.quickskript.core.pattern;

import com.github.stefvanschie.quickskript.core.pattern.group.*;
import com.github.stefvanschie.quickskript.core.util.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Contains a fully parsed skript pattern
 *
 * @since 0.1.0
 */
public class SkriptPattern {

    /**
     * The groups of this skript pattern
     */
    private final List<SkriptPatternGroup> groups;

    /**
     * True if {@link TypeGroup} and {@link RegexGroup} should match as much as possible, false if they should match as
     * little as possible. In both cases, a valid match has priority over this option: the groups mentioned will not
     * match something if that means the match will become impossible, even though a match is possible if they'd capture
     * more.
     */
    private boolean greedy = true;

    /**
     * A set with functions that can parse groups
     */
    private static final Set<Function<String, Pair<? extends SkriptPatternGroup, String>>> GROUP_PARSERS = Set.of(
        ChoiceGroup::parseStarting,
        LiteralGroup::parseStarting,
        OptionalGroup::parseStarting,
        RegexGroup::parseStarting,
        SpaceGroup::parseStarting,
        TypeGroup::parseStarting
    );

    /**
     * Creates a new skript pattern
     *
     * @param groups the groups
     * @since 0.1.0
     */
    private SkriptPattern(List<SkriptPatternGroup> groups) {
        this.groups = groups;
    }

    /**
     * Tries to match the given input to this pattern. Returns a {@link SkriptMatchResult} detailing information about
     * the successfulness of this match. This is successful even if not the entire string was matched. The string that
     * wasn't matched can be found inside the {@link SkriptMatchResult}. The match has to start at the start of the
     * input, it will not match somewhere in the middle of the string.
     *
     * @param input the input to match
     * @param resting if their may be a resting string
     * @return the match result
     * @since 0.1.0
     */
    @NotNull
    public SkriptMatchResult match(@NotNull String input, boolean resting) {
        SkriptMatchResult result = new SkriptMatchResult();

        //a space we may be able to skip because the next optional may not match as well
        SpaceGroup temporarilySkipped = null;

        for (int index = 0; index < groups.size(); index++) {
            SkriptPatternGroup group = groups.get(index);

            if (group instanceof LiteralGroup) {
                String text = ((LiteralGroup) group).getText();

                if (input.startsWith(text)) {
                    input = input.substring(text.length());
                    result.addMatchedGroup(group, text);
                } else {
                    result.failure(group);
                    return result;
                }
            } else if (group instanceof ChoiceGroup) {
                ChoiceGroup choiceGroup = (ChoiceGroup) group;
                SkriptPattern[] patterns = choiceGroup.getPatterns();

                boolean matched = false;

                for (int i = 0; i < patterns.length; i++) {
                    SkriptPattern pattern = patterns[i];
                    SkriptMatchResult match = pattern.match(input, true);

                    if (match.isSuccessful()) {
                        result.addMatchedGroup(group, match.getMatchedString());
                        result.addParseMark(choiceGroup.getParseMarks()[i]);

                        match.getMatchedGroups().forEach(result::addMatchedGroup);

                        String restingString = match.getRestingString();

                        input = restingString == null ? "" : restingString;
                        matched = true;
                        break;
                    }
                }

                if (!matched) {
                    result.failure(group);

                    return result;
                }
            } else if (group instanceof TypeGroup) {
                if (index + 1 == groups.size()) {
                    result.addMatchedGroup(group, input);
                    result.success(null);
                    return result;
                }

                SkriptPattern pattern = new SkriptPattern(groups.subList(index + 1, groups.size()));

                StringBuilder testInput = new StringBuilder(greedy ? "" : input);
                boolean success = false;

                for (int inputIndex = input.length() - 1; inputIndex >= 0; inputIndex--) {
                    SkriptMatchResult match = pattern.match(testInput.toString(), resting);

                    if (match.isSuccessful()) {
                        result.addMatchedGroup(group, input.substring(0, greedy ? inputIndex + 1 : input.length() - testInput.length()));
                        match.getMatchedGroups().forEach(result::addMatchedGroup);
                        result.addParseMark(match.getParseMark());

                        input = match.getRestingString();
                        success = true;
                        break;
                    }

                    if (greedy) {
                        testInput.insert(0, input.charAt(inputIndex));
                    } else {
                        testInput.deleteCharAt(0);
                    }
                }

                if (success) {
                    if (input == null || input.isEmpty()) {
                        result.success(null);
                    } else {
                        result.success(input);
                    }
                } else {
                    result.failure(group);
                }

                return result;
            } else if (group instanceof RegexGroup) {
                Pattern pattern = ((RegexGroup) group).getPattern();

                if (index + 1 == groups.size()) {
                    Matcher matcher = pattern.matcher(input);

                    while (matcher.find()) {
                        if (matcher.start() == 0) {
                            String match = matcher.group();

                            result.addMatchedGroup(group, match);

                            String restingText = input.substring(match.length());

                            if (restingText.isEmpty()) {
                                result.success(null);
                            } else {
                                result.success(restingText);
                            }
                        }
                    }

                    if (!result.isSuccessful()) {
                        result.failure(group);
                    }

                    return result;
                }

                SkriptPattern skriptPattern = new SkriptPattern(groups.subList(index + 1, groups.size()));

                StringBuilder testInput = new StringBuilder(greedy ? "" : input);
                boolean success = false;

                for (int inputIndex = input.length() - 1; inputIndex >= 0; inputIndex--) {
                    SkriptMatchResult match = skriptPattern.match(testInput.toString(), resting);

                    if (match.isSuccessful()) {
                        var matcher = pattern.matcher(input.substring(0, greedy ? inputIndex + 1 : input.length() - testInput.length()));

                        if (!matcher.matches()) {
                            if (greedy) {
                                testInput.insert(0, input.charAt(inputIndex));
                            } else {
                                testInput.deleteCharAt(0);
                            }

                            continue;
                        }

                        result.addMatchedGroup(group, matcher.group());
                        match.getMatchedGroups().forEach(result::addMatchedGroup);
                        result.addParseMark(match.getParseMark());

                        input = match.getRestingString();
                        success = true;
                        break;
                    }

                    if (greedy) {
                        testInput.insert(0, input.charAt(inputIndex));
                    } else {
                        testInput.deleteCharAt(0);
                    }
                }

                if (success) {
                    if (input == null || input.isEmpty()) {
                        result.success(null);
                    } else {
                        result.success(input);
                    }
                } else {
                    result.failure(group);
                }

                return result;
            } else if (group instanceof SpaceGroup) {
                if (input.startsWith(" ")) {
                    result.addMatchedGroup(group, " ");

                    input = input.substring(1);
                } else {
                    if (index + 1 >= groups.size()) {
                        result.failure(group);

                        return result;
                    }

                    if (groups.get(index + 1) instanceof OptionalGroup
                        && (index + 2 >= groups.size() || groups.get(index + 2) instanceof SpaceGroup)) {
                        temporarilySkipped = (SpaceGroup) group;
                    } else {
                        result.failure(group);

                        return result;
                    }
                }
            } else if (group instanceof OptionalGroup) {
                OptionalGroup optionalGroup = (OptionalGroup) group;
                SkriptPattern[] patterns = optionalGroup.getPatterns();

                boolean matched = false;

                for (int i = 0; i < patterns.length; i++) {
                    SkriptPattern pattern = patterns[i];
                    SkriptMatchResult match = pattern.match(input, true);

                    if (match.isSuccessful()) {
                        result.addMatchedGroup(group, match.getMatchedString());
                        result.addParseMark(optionalGroup.getParseMarks()[i]);

                        match.getMatchedGroups().forEach(result::addMatchedGroup);

                        String restingString = match.getRestingString();

                        input = restingString == null ? "" : restingString;
                        matched = true;
                        break;
                    }
                }

                if (matched) {
                    if (index - 1 >= 0 && groups.get(index - 1) instanceof SpaceGroup
                        && temporarilySkipped == groups.get(index - 1)) {
                        result.removeMatchedGroup(group); //we matched and all, but we skipped a space so we shouldn't have matched, therefore this match fails
                        result.failure(temporarilySkipped);

                        return result;
                    }
                } else {
                    if ((index - 1 < 0 || groups.get(index - 1) instanceof SpaceGroup)
                        && (index + 1 >= groups.size() || groups.get(index + 1) instanceof SpaceGroup)) {
                        if (index - 1 < 0) {
                            index++; //skip next space
                        } else {
                            temporarilySkipped = null;
                        }
                    }
                }
            }
        }

        if (input.isEmpty()) {
            result.success(null);
        } else if (!resting) {
            result.failure(null);
        } else {
            result.success(input);
        }

        return result;
    }

    /**
     * Parses a skript pattern from the given input
     *
     * @param input the input to transform into a pattern
     * @return the pattern
     * @since 0.1.0
     */
    @NotNull
    public static SkriptPattern parse(@NotNull String input) {
        List<SkriptPatternGroup> groups = new ArrayList<>();

        while (input.length() != 0) {
            for (Function<String, Pair<? extends SkriptPatternGroup, String>> parser : GROUP_PARSERS) {
                Pair<? extends SkriptPatternGroup, String> pair = parser.apply(input);

                if (pair == null) {
                    continue;
                }

                groups.add(pair.getX());

                input = pair.getY();
                break;
            }
        }

        return new SkriptPattern(groups);
    }

    /**
     * Parses skript patterns from the given inputs
     *
     * @param input the input to transform into patterns
     * @return the patterns
     * @since 0.1.0
     */
    @NotNull
    public static SkriptPattern[] parse(@NotNull String... input) {
        SkriptPattern[] patterns = new SkriptPattern[input.length];

        for (int i = 0; i < input.length; i++) {
            patterns[i] = parse(input[i]);
        }

        return patterns;
    }

    /**
     * Gets all groups. This will also return groups inside other groups.
     *
     * @return an immutable list of all groups in the order as they appear in the pattern
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public List<SkriptPatternGroup> getGroups() {
        return groups.stream()
            .flatMap(group -> Stream.concat(Stream.of(group), group.getChildren().stream()))
            .collect(Collectors.toUnmodifiableList());
    }

    /**
     * Changes whether this pattern should be greedy or not.
     *
     * @param greedy true if this should be greedy, false if not
     * @return this pattern
     * @see #greedy
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public SkriptPattern greedy(boolean greedy) {
        this.greedy = greedy;

        return this;
    }

    /**
     * Matches this pattern to the given text.
     *
     * @param input the text to match
     * @return the match result
     * @see #match(String, boolean)
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public SkriptMatchResult match(@NotNull String input) {
        return match(input, false);
    }
}
