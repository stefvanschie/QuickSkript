package com.github.stefvanschie.quickskript.core.pattern.group;

import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.pattern.exception.SkriptPatternParseException;
import com.github.stefvanschie.quickskript.core.util.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.text.html.Option;
import java.util.*;

/**
 * A optional with multiple choices, similar to a {@link ChoiceGroup}, but these choices are optional: none have to
 * match.
 *
 * @since 0.1.0
 */
public class OptionalGroup implements SkriptPatternGroup {

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
    private OptionalGroup(@NotNull SkriptPattern[] patterns, @NotNull int[] parseMarks) {
        this.patterns = patterns;
        this.parseMarks = parseMarks;
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public List<SkriptMatchResult> match(@NotNull SkriptPatternGroup[] followingGroups, @NotNull String input) {
        List<SkriptMatchResult> results = new ArrayList<>();
        int groupLength = followingGroups.length;

        for (int i = 0; i < patterns.length; i++) {
            SkriptPattern pattern = patterns[i];
            List<SkriptMatchResult> patternResults = pattern.match(input);

            for (SkriptMatchResult patternResult : patternResults) {
                patternResult.addParseMark(parseMarks[i]);
            }

            if (groupLength == 0) {
                for (SkriptMatchResult result : patternResults) {
                    result.addMatchedGroup(this, result.getMatchedString(), 0);
                }

                results.addAll(patternResults);
                continue;
            }

            SkriptPatternGroup[] newArray = Arrays.copyOfRange(followingGroups, 1, groupLength);

            for (SkriptMatchResult result : patternResults) {
                List<SkriptMatchResult> calleeResults = followingGroups[0].match(newArray, result.getRestingString());

                List<Pair<SkriptPatternGroup, String>> matchedGroups = new ArrayList<>(result.getMatchedGroups());

                Collections.reverse(matchedGroups);

                for (SkriptMatchResult res : calleeResults) {
                    SkriptMatchResult copy = result.shallowCopy();

                    copy.addMatchedGroup(this, copy.getMatchedString(), 0);
                    res.getMatchedGroups().forEach(pair ->
                        copy.addMatchedGroup(pair.getX(), pair.getY(), copy.getMatchedGroups().size()));
                    copy.setRestingString(res.getRestingString());
                    copy.addParseMark(res.getParseMark());

                    results.add(copy);
                }
            }
        }

        if (groupLength == 0) {
            SkriptMatchResult result = new SkriptMatchResult();
            result.setRestingString(input);

            results.add(result);
        } else {
            results.addAll(followingGroups[0].match(Arrays.copyOfRange(followingGroups, 1, groupLength), input));

            if (groupLength >= 2 && followingGroups[0] instanceof SpaceGroup) {
                SkriptPatternGroup[] newArray = Arrays.copyOfRange(followingGroups, 2, groupLength);

                results.addAll(followingGroups[1].match(newArray, input));
            }
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
     * Gets the parse marks for each optional choice
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
    public static Pair<OptionalGroup, StringBuilder> parseStarting(@NotNull StringBuilder input) {
        if (input.charAt(0) != '[') {
            return null;
        }

        input.deleteCharAt(0);

        int openingOptionals = 1;
        int openingChoices = 0;

        int currentParseMark = 0;
        int groupStartIndex = 0;

        List<SkriptPattern> patterns = new ArrayList<>();
        List<Integer> parseMarks = new ArrayList<>();

        int lastIndex;

        for (int index = 0; ; index++) {
            if (index == input.length()) {
                throw new SkriptPatternParseException(
                    "Expected an ending optional group character ']' at index " + input.length() + " but wasn't found"
                );
            }

            char character = input.charAt(index);

            if (character == ']') {
                openingOptionals--;

                if (openingOptionals == 0) {
                    patterns.add(SkriptPattern.parse(input.substring(groupStartIndex, index)));
                    parseMarks.add(currentParseMark);

                    lastIndex = index;

                    break;
                }
            } else if (character == '[') {
                openingOptionals++;
            } else if (character == '(') {
                openingChoices++;
            } else if (character == ')') {
                openingChoices--;
            }

            if (openingOptionals == 1 && openingChoices == 0) {
                if (character == '|') {
                    patterns.add(SkriptPattern.parse(input.substring(groupStartIndex, index)));
                    parseMarks.add(currentParseMark);

                    currentParseMark = 0;
                    groupStartIndex = index + 1;
                } else if (character == '\u00A6') { //broken bar character
                    String parseMarkString = input.substring(groupStartIndex, index);

                    if (!parseMarkString.matches("-?\\d+")) { //TODO pre-compile pattern
                        throw new SkriptPatternParseException(
                            "Parse mark needs to be an integer, but '" + parseMarkString + "' does not adhere to this"
                        );
                    }

                    currentParseMark = Integer.parseInt(parseMarkString);
                    groupStartIndex = index + 1;
                }
            }
        }

        int[] parseMarkArray = parseMarks.stream().mapToInt(x -> x).toArray();
        OptionalGroup optionalGroup = new OptionalGroup(patterns.toArray(new SkriptPattern[0]), parseMarkArray);

        return new Pair<>(optionalGroup, input.delete(0, lastIndex + 1));
    }

    @NotNull
    @Contract(pure = true)
    @Override
    public Collection<String> unrollFully(@NotNull List<SkriptPatternGroup> groups) {
        int index = SkriptPatternGroup.indexOfSame(groups, this);
        Collection<String> matches = new HashSet<>();

        if (index - 1 >= 0) {
            SkriptPatternGroup previousGroup = groups.get(index - 1);

            if (previousGroup instanceof SpaceGroup || previousGroup instanceof OptionalGroup) {
                return Collections.emptySet();
            }
        }

        List<SkriptPatternGroup> relevantGroups = new ArrayList<>();

        int currentIndex = index;

        do {
            relevantGroups.add(groups.get(currentIndex));

            currentIndex++;
        } while (currentIndex < groups.size() &&
            (groups.get(currentIndex) instanceof SpaceGroup || groups.get(currentIndex) instanceof OptionalGroup));

        boolean isAllOptional = true;

        for (SkriptPatternGroup relevantGroup : relevantGroups.subList(0, relevantGroups.size() - 1)) {
            if (!(relevantGroup instanceof OptionalGroup)) {
                isAllOptional = false;
                break;
            }
        }

        if (isAllOptional) {
            List<OptionalGroup> optionalGroups = new ArrayList<>();

            for (SkriptPatternGroup group : relevantGroups) {
                if (group instanceof OptionalGroup) {
                    optionalGroups.add((OptionalGroup) group);
                }
            }

            Collection<String> permutations = permutations(optionalGroups);

            permutations.add("");

            //no space at the end
            if (optionalGroups.size() == relevantGroups.size()) {
                return permutations;
            }

            boolean isFirstSequence = true;

            for (int sequenceIndex = 0; sequenceIndex < index; sequenceIndex++) {
                SkriptPatternGroup group = groups.get(sequenceIndex);

                if (!(group instanceof SpaceGroup) && !(group instanceof OptionalGroup)) {
                    isFirstSequence = false;
                    break;
                }
            }

            Collection<String> permutationsWithSpace = new HashSet<>();

            for (String permutation : permutations) {
                //don't add space if this is the first sequence and the element is empty
                if (!permutation.isEmpty() || !isFirstSequence) {
                    permutationsWithSpace.add(permutation + ' ');
                } else {
                    permutationsWithSpace.add("");
                }
            }

            return permutationsWithSpace;
        }

        int previousSpaceIndex = -1;

        List<Collection<String>> allPermutations = new ArrayList<>();

        for (int i = 0; i < relevantGroups.size(); i++) {
            SkriptPatternGroup group = relevantGroups.get(i);

            if (group instanceof SpaceGroup || i == relevantGroups.size() - 1) {
                List<OptionalGroup> optionalGroups = new ArrayList<>();

                int listSize = i == relevantGroups.size() - 1 && !(group instanceof SpaceGroup) ? i + 1 : i;

                for (SkriptPatternGroup optionalGroup : relevantGroups.subList(previousSpaceIndex + 1, listSize)) {
                    optionalGroups.add((OptionalGroup) optionalGroup);
                }

                if (optionalGroups.size() > 0) {
                    allPermutations.add(permutations(optionalGroups));
                }

                previousSpaceIndex = i;
            }
        }

        boolean isLastSequence = true;

        for (int sequenceIndex = index + 1; sequenceIndex < groups.size(); sequenceIndex++) {
            SkriptPatternGroup group = groups.get(sequenceIndex);

            if (!(group instanceof SpaceGroup) && !(group instanceof OptionalGroup)) {
                isLastSequence = false;
                break;
            }
        }

        Collection<List<String>> possiblePatterns = possiblePatterns(allPermutations);

        for (List<String> possiblePattern : possiblePatterns) {
            boolean isWhiteSpace = true;
            StringBuilder match = new StringBuilder();

            for (int i = 0; i < possiblePattern.size(); i++) {
                String element = possiblePattern.get(i);

                if (element.isEmpty() &&
                    (i + 1 < possiblePattern.size() && !possiblePattern.get(i + 1).isEmpty())) {
                    match.append(' ');
                } else if (!element.isEmpty()) {
                    if (!isWhiteSpace) {
                        match.append(' ');
                    }

                    match.append(element);

                    isWhiteSpace = false;
                }
            }

            if (match.length() == 0 && !isLastSequence) {
                matches.add(" ");
            } else {
                if (relevantGroups.get(relevantGroups.size() - 1) instanceof SpaceGroup) {
                    match.append(' ');
                }

                matches.add(match.toString());
            }
        }

        return Collections.unmodifiableCollection(matches);
    }

    /**
     * Gets all possible permutations for a list of optional groups. The resulting permutations are ordered by
     * appearance in the provided list.
     *
     * @param optionalGroups the optional groups
     * @return a collection of all permutations
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    private Collection<String> permutations(@NotNull List<OptionalGroup> optionalGroups) {
        if (optionalGroups.size() == 1) {
            Collection<String> strings = new HashSet<>();

            for (SkriptPattern pattern : optionalGroups.get(0).patterns) {
                strings.addAll(pattern.unrollFully());
            }

            strings.add("");

            return strings;
        }

        Collection<String> allPermutations = new HashSet<>();
        Collection<String> permutations = permutations(optionalGroups.subList(1, optionalGroups.size()));

        for (SkriptPattern pattern : optionalGroups.get(0).patterns) {
            for (String permutation : permutations) {
                for (String string : pattern.unrollFully()) {
                    allPermutations.add(string + permutation);
                }

                allPermutations.add(permutation);
            }
        }

        return allPermutations;
    }

    /**
     * Takes a list of collection of strings and returns a collection of list of strings. If the given list are columns
     * with different options, then the output will be all possible permutations of picking a single element from each
     * column. This keeps the initial order.
     *
     * @param allPermutations the permutations
     * @return all possible permutations
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    private Collection<List<String>> possiblePatterns(@NotNull List<Collection<String>> allPermutations) {
        Collection<List<String>> possiblePatterns;

        if (allPermutations.size() == 1) {
            possiblePatterns = new HashSet<>();

            for (String element : allPermutations.get(0)) {
                possiblePatterns.add(Collections.singletonList(element));
            }

            return possiblePatterns;
        }

        Collection<List<String>> allPossiblePatterns = new HashSet<>();
        possiblePatterns = possiblePatterns(allPermutations.subList(1, allPermutations.size()));

        for (String firstListElement : allPermutations.get(0)) {
            for (List<String> possiblePattern : possiblePatterns) {
                List<String> newPattern = new ArrayList<>(possiblePattern);

                newPattern.add(0, firstListElement);

                allPossiblePatterns.add(newPattern);
            }
        }

        return allPossiblePatterns;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        OptionalGroup optionalGroup = (OptionalGroup) object;

        return Arrays.equals(patterns, optionalGroup.patterns) && Arrays.equals(parseMarks, optionalGroup.parseMarks);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(patterns);
        result = 31 * result + Arrays.hashCode(parseMarks);
        return result;
    }
}
