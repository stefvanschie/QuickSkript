package com.github.stefvanschie.quickskript.core.pattern.group;

import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.pattern.exception.SkriptPatternParseException;
import com.github.stefvanschie.quickskript.core.util.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * A group that marks possible choices.
 *
 * @since 0.1.0
 */
public class ChoiceGroup implements SkriptPatternGroup {

    /**
     * The different choices
     */
    @NotNull
    private final SkriptPattern[] patterns;

    /**
     * The parse marks assigned to each individual choice. 0 if no parse mark was explicitly assigned.
     */
    @NotNull
    private final int[] parseMarks;

    /**
     * Creates an optional group
     *
     * @param patterns the patterns inside this optional group
     * @param parseMarks the parse marks for each pattern
     * @since 0.1.0
     */
    private ChoiceGroup(@NotNull SkriptPattern[] patterns, @NotNull int[] parseMarks) {
        this.patterns = patterns;
        this.parseMarks = parseMarks;
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public List<SkriptMatchResult> match(@NotNull SkriptPatternGroup[] followingGroups, @NotNull String input) {
        List<SkriptMatchResult> results = new ArrayList<>();

        for (int i = 0; i < patterns.length; i++) {
            SkriptPattern pattern = patterns[i];
            List<SkriptMatchResult> patternResults = pattern.match(input);

            for (SkriptMatchResult patternResult : patternResults) {
                patternResult.addParseMark(parseMarks[i]);
            }

            if (followingGroups.length == 0) {
                patternResults.forEach(result ->
                    result.addMatchedGroup(this, result.getMatchedString(), 0));

                results.addAll(patternResults);
                continue;
            }

            SkriptPatternGroup[] newArray = Arrays.copyOfRange(followingGroups, 1, followingGroups.length);

            patternResults.forEach(result -> {
                List<SkriptMatchResult> calleeResults = followingGroups[0].match(newArray, result.getRestingString());

                //we didn't match anything in the choice group, so any space after is optional
                if (result.getMatchedString().isEmpty() && followingGroups[0] instanceof SpaceGroup) {
                    SkriptPatternGroup[] groups = Arrays.copyOfRange(followingGroups, 2, followingGroups.length);

                    calleeResults.addAll(followingGroups[1].match(groups, result.getRestingString()));
                }

                List<Pair<SkriptPatternGroup, String>> matchedGroups = new ArrayList<>(result.getMatchedGroups());

                Collections.reverse(matchedGroups);

                calleeResults.forEach(res -> {
                    SkriptMatchResult copy = result.shallowCopy();

                    copy.addMatchedGroup(this, copy.getMatchedString(), 0);
                    res.getMatchedGroups().forEach(pair ->
                        copy.addMatchedGroup(pair.getX(), pair.getY(), copy.getMatchedGroups().size()));
                    copy.setRestingString(res.getRestingString());
                    copy.addParseMark(res.getParseMark());

                    results.add(copy);
                });
            });
        }

        return results;
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public List<SkriptPatternGroup> getChildren() {
        var groups = new ArrayList<SkriptPatternGroup>();

        for (SkriptPattern pattern : getPatterns()) {
            groups.addAll(pattern.getGroups());
        }

        return Collections.unmodifiableList(groups);
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public <T extends SkriptPatternGroup> List<T> getGroups(Class<T> groupClass) {
        var groups = new ArrayList<T>();

        if (getClass() == groupClass) {
            groups.add((T) this);
        }

        for (SkriptPattern pattern : getPatterns()) {
            groups.addAll(pattern.getGroups(groupClass));
        }

        return groups;
    }

    /**
     * Gets the possible choices as skript patterns
     *
     * @return the choices
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public SkriptPattern[] getPatterns() {
        return patterns;
    }

    /**
     * Gets the parse marks for each choice
     *
     * @return the parse marks
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public int[] getParseMarks() {
        return parseMarks;
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
    public static Pair<ChoiceGroup, StringBuilder> parseStarting(@NotNull StringBuilder input) {
        if (input.charAt(0) != '(') {
            return null;
        }

        input.deleteCharAt(0);

        int openingOptionals = 0;
        int openingChoices = 1;

        int currentParseMark = 0;
        int groupStartIndex = 0;

        List<SkriptPattern> patterns = new ArrayList<>();
        List<Integer> parseMarks = new ArrayList<>();

        int lastIndex;

        for (int index = 0; ; index++) {
            if (index == input.length()) {
                throw new SkriptPatternParseException(
                    "Expected an ending optional group character ')' at index " + input.length() + " but wasn't found"
                );
            }

            char character = input.charAt(index);

            if (character == ']') {
                openingOptionals--;
            } else if (character == '[') {
                openingOptionals++;
            } else if (character == '(') {
                openingChoices++;
            } else if (character == ')') {
                openingChoices--;

                if (openingChoices == 0) {
                    patterns.add(SkriptPattern.parse(input.substring(groupStartIndex, index)));
                    parseMarks.add(currentParseMark);

                    lastIndex = index;

                    break;
                }
            }

            if (openingOptionals == 0 && openingChoices == 1) {
                if (character == '|') {
                    patterns.add(SkriptPattern.parse(input.substring(groupStartIndex, index)));
                    parseMarks.add(currentParseMark);

                    currentParseMark = 0;
                    groupStartIndex = index + 1;
                } else if (character == '\u00A6') { //broken bar character
                    String parseMarkString = input.substring(groupStartIndex, index);

                    for (int i = 0; i < parseMarkString.length(); i++) {
                        if (parseMarkString.charAt(i) == '-' && i == 0) {
                            continue;
                        }

                        if (parseMarkString.charAt(i) < '0' || parseMarkString.charAt(i) > '9') {
                            throw new SkriptPatternParseException(
                                "Parse mark needs to be an integer, but '" + parseMarkString + "' does not adhere to this"
                            );
                        }
                    }

                    currentParseMark = Integer.parseInt(parseMarkString);
                    groupStartIndex = index + 1;
                }
            }
        }

        int[] parseMarkArray = new int[parseMarks.size()];

        for (int i = 0; i < parseMarks.size(); i++) {
            parseMarkArray[i] = parseMarks.get(i);
        }

        ChoiceGroup choiceGroup = new ChoiceGroup(patterns.toArray(SkriptPattern[]::new), parseMarkArray);

        return new Pair<>(choiceGroup, input.delete(0, lastIndex + 1));
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public Collection<String> unrollFully(@NotNull SkriptPatternGroup @NotNull [] groups) {
        Collection<String> matches = new HashSet<>();

        for (SkriptPattern pattern : patterns) {
            matches.addAll(pattern.unrollFully());
        }

        if (groups.length == 0) {
            return matches;
        }

        int index = 0;

        Collection<String> newMatches = new HashSet<>();
        Collection<String> strings;

        if (groups[0] instanceof OptionalGroup) {
            strings = groups[0].unrollFully(new SkriptPatternGroup[0]);

            for (String match : matches) {
                for (String string : strings) {
                    newMatches.add(match + string);
                }
            }

            if (groups.length == 1) {
                return newMatches;
            }

            matches = new HashSet<>(newMatches);
            index++;
        }

        strings = groups[index].unrollFully(Arrays.copyOfRange(groups, index + 1, groups.length));

        for (String match : matches) {
            for (String string : strings) {
                if (match.isEmpty() && string.startsWith(" ")) {
                    newMatches.add(match + string.substring(1));
                } else {
                    newMatches.add(match + string);
                }
            }
        }

        return newMatches;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        ChoiceGroup choiceGroup = (ChoiceGroup) object;

        return Arrays.equals(patterns, choiceGroup.patterns) && Arrays.equals(parseMarks, choiceGroup.parseMarks);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(patterns);
        result = 31 * result + Arrays.hashCode(parseMarks);
        return result;
    }
}
