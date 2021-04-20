package com.github.stefvanschie.quickskript.core.file.alias;

import com.github.stefvanschie.quickskript.core.file.alias.exception.AliasFileFormatException;
import com.github.stefvanschie.quickskript.core.file.alias.exception.AliasFileResolveException;
import com.github.stefvanschie.quickskript.core.file.alias.exception.UndefinedAliasFileVariation;
import com.github.stefvanschie.quickskript.core.file.alias.manager.AliasFileManager;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * An alias file. This alias file contains parsed data about its contents.
 *
 * @since 0.1.0
 */
public class AliasFile {

    /**
     * All the directives in this file
     */
    private final Set<AliasFileUseDirective> directives = new HashSet<>();

    /**
     * All the variations in this file and the names of these variations
     */
    private final Map<String, AliasFileVariation> variations = new HashMap<>();

    /**
     * All the entries in this file
     */
    private final Set<AliasFileEntry> entries = new HashSet<>();

    /**
     * Resolves all possible entries from this file. This means that each possible entry that differ from each other
     * will be a separate entry. The entries are represented by patterns - each pattern permutation is the same entry.
     * The alias file manager should have loaded all alias files that this file depends on via a {@code :use} directive.
     * It is not necessary for these dependencies to be resolved, however.
     *
     * @param manager the alias file manager
     * @return all entries and their categories
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public Collection<? extends ResolvedAliasEntry> resolveAll(@NotNull AliasFileManager manager) {
        Map<String, AliasFileVariation> variations = new HashMap<>();

        for (AliasFileUseDirective directive : directives) {
            String filePath = directive.getFilePath();
            AliasFile file = manager.getFile(filePath);

            if (file == null) {
                throw new AliasFileResolveException("Unable to find specified file named " + filePath);
            }

            variations.putAll(file.variations);
        }

        variations.putAll(this.variations);

        var fileEntries = new HashSet<ResolvedAliasEntry>();
        Map<String, SkriptPattern> categoryCache = new HashMap<>();

        for (AliasFileEntry entry : entries) {
            Map<String, Map<String, String>> combinations = variationCombinations(entry.getEntry(), variations);

            for (Map.Entry<String, Map<String, String>> combination : combinations.entrySet()) {
                SkriptPattern pattern = SkriptPattern.parse(combination.getKey());
                var categories = new HashSet<SkriptPattern>(entry.getCategories().size());

                for (String category : entry.getCategories()) {
                    categoryCache.putIfAbsent(category, SkriptPattern.parse(category));

                    categories.add(categoryCache.get(category));
                }

                Map<String, String> attributes = combination.getValue();
                String key = entry.getKey();

                if (key != null) {
                    int openSquareBracket = key.indexOf('[');

                    if (openSquareBracket != -1) {
                        int closedSquareBracket = key.indexOf(']');

                        if (closedSquareBracket == -1 || closedSquareBracket != key.length() - 1) {
                            throw new AliasFileResolveException(
                                "Key has missing closing square bracket, expected this at the end of the key"
                            );
                        }

                        String attribute = key.substring(openSquareBracket + 1, key.length() - 1);

                        key = key.substring(0, openSquareBracket);

                        int equalsIndex = attribute.indexOf('=');

                        if (equalsIndex == -1) {
                            throw new AliasFileResolveException("Invalid attribute '" + attribute + "', missing '='");
                        }

                        String attributeKey = attribute.substring(0, equalsIndex);
                        String attributeValue = attribute.substring(equalsIndex + 1);

                        attributes.put(attributeKey, attributeValue);
                    }
                }

                fileEntries.add(new ResolvedAliasEntry(pattern, key, categories, attributes));
            }
        }

        return Collections.unmodifiableCollection(fileEntries);
    }

    /**
     * Gets a collection of all possible combinations of the specified pattern with the variations as specified
     * replaced.
     *
     * @param pattern the pattern to replace the variations in
     * @param variations the variations to replace in the pattern
     * @return all possible combinations of variations
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    private Map<String, Map<String, String>> variationCombinations(@NotNull String pattern,
        @NotNull Map<String, AliasFileVariation> variations) {
        Map<String, Map<String, String>> patterns = new HashMap<>();

        int openIndex = pattern.indexOf('{');
        int closeIndex = pattern.indexOf('}');

        if (openIndex == -1 || closeIndex == -1 || openIndex + 1 > closeIndex) {
            patterns.put(pattern, new HashMap<>());
            return patterns;
        }

        String name = pattern.substring(openIndex + 1, closeIndex);
        AliasFileVariation variation = variations.get(name);

        if (variation == null) {
            throw new UndefinedAliasFileVariation("The variation '" + name + "' is undefined");
        }

        String fullName = variation.getFullName();
        int variationIndex = pattern.indexOf(fullName);

        String firstSubstring = pattern.substring(0, variationIndex);
        String secondSubstring = pattern.substring(variationIndex + fullName.length());

        for (String entry : variation.getEntries()) {
            String replacedPattern = firstSubstring + entry + secondSubstring;
            Map<String, Map<String, String>> combinations = variationCombinations(replacedPattern, variations);

            for (Map.Entry<String, Map<String, String>> combination : combinations.entrySet()) {
                Map<String, String> attributes = combination.getValue();
                attributes.put(variation.getName(), entry);

                patterns.put(combination.getKey(), attributes);
            }
        }

        if (variation.isOptional()) {
            String replaced;

            if (variationIndex - 1 >= 0 && pattern.charAt(variationIndex - 1) == ' ') {
                replaced = pattern.substring(0, variationIndex - 1) + secondSubstring;
            } else {
                replaced = firstSubstring + (
                    secondSubstring.length() > 0 && secondSubstring.charAt(0) == ' '
                        ? secondSubstring.substring(1)
                        : secondSubstring
                );
            }

            patterns.putAll(variationCombinations(replaced, variations));
        }

        return patterns;
    }

    /**
     * Parses an alias file from the specified input stream
     *
     * @param inputStream the input stream that contains the contents of the file
     * @return the alias file
     * @throws IOException when something went wrong while reading the file
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public static AliasFile parse(@NotNull InputStream inputStream) throws IOException {
        AliasFile file = new AliasFile();

        String[] lines = new String(inputStream.readAllBytes()).split("[\r\n]+");

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];

            if (line.startsWith(":use ")) {
                file.directives.add(new AliasFileUseDirective(line.substring(":use ".length()).trim()));
                continue;
            }

            int equalsIndex = line.indexOf('=');
            int arrowIndex = line.indexOf("->");

            if (arrowIndex != -1 || equalsIndex == -1) {
                String entryAndCategories;
                String key = null;
                AliasFileEntry entry;

                if (arrowIndex == -1) {
                    entryAndCategories = line;
                } else {
                    entryAndCategories = line.substring(0, arrowIndex);
                    key = line.substring(arrowIndex + "->".length()).trim();
                }

                int colonIndex = entryAndCategories.indexOf(':');

                if (colonIndex == -1) {
                    entry = new AliasFileEntry(entryAndCategories.trim(), key);
                } else {
                    entry = new AliasFileEntry(entryAndCategories.substring(0, colonIndex).trim(), key);

                    int lastCommaIndex = 0;
                    String categories = entryAndCategories.substring(colonIndex + 1);

                    for (int index = 0; index < categories.length(); index++) {
                        char character = categories.charAt(index);

                        if (character != ',') {
                            continue;
                        }

                        entry.addCategory(categories.substring(lastCommaIndex, index).trim());
                        lastCommaIndex = index;
                    }

                    entry.addCategory(categories.substring(lastCommaIndex).trim());
                }

                file.entries.add(entry);
            } else {
                boolean optional = false;
                String variationName = line.substring(0, equalsIndex).trim();
                int variationNameLength = variationName.length();

                if (variationNameLength > 0 && variationName.charAt(0) == '[' &&
                    variationName.charAt(variationNameLength - 1) == ']') {
                    optional = true;

                    variationName = variationName.substring(1, variationNameLength - 1);
                }

                StringBuilder variation = new StringBuilder(line.substring(equalsIndex + 1).trim());

                if (variation.charAt(0) != '{') {
                    throw new AliasFileFormatException(
                        "Variation declaration should have an opening curly bracket followed by it."
                    );
                }

                variation.deleteCharAt(0);

                while (!lines[i].contains("}")) {
                    variation.append(lines[++i]);
                }

                variation.deleteCharAt(variation.length() - 1);

                List<String> entries = new ArrayList<>();

                for (String entry : variation.toString().split(",")) {
                    entries.add(entry.trim());
                }

                file.variations.put(variationName, new AliasFileVariation(entries, variationName, optional));
            }
        }

        return file;
    }
}
