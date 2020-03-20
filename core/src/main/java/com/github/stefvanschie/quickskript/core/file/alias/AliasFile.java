package com.github.stefvanschie.quickskript.core.file.alias;

import com.github.stefvanschie.quickskript.core.file.alias.exception.AliasFileFormatException;
import com.github.stefvanschie.quickskript.core.file.alias.exception.AliasFileResolveException;
import com.github.stefvanschie.quickskript.core.file.alias.manager.AliasFileManager;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.util.registry.ItemTypeRegistry;
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
     * All the variations in this file
     */
    private final Set<AliasFileVariation> variations = new HashSet<>();

    /**
     * All the entries in this file
     */
    private final Set<AliasFileEntry> entries = new HashSet<>();

    /**
     * Resolves all possible item types from this file. This means that each possible item type that differ from each
     * other will be a separate entry. The entries are represented by patterns - each pattern permutation is the same
     * item type. The alias file manager should have loaded all alias files that this file depends on via a {@code :use}
     * directive. It is not necessary for these dependencies to be resolved, however.
     *
     * @param manager the alias file manager
     * @return all item types
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public Collection<ItemTypeRegistry.Entry> resolveAllItemTypes(@NotNull AliasFileManager manager) {
        Set<AliasFileVariation> variations = new HashSet<>();

        for (AliasFileUseDirective directive : directives) {
            String filePath = directive.getFilePath();
            AliasFile file = manager.getFile(filePath);

            if (file == null) {
                throw new AliasFileResolveException("Unable to find specified file named " + filePath);
            }

            variations.addAll(file.variations);
        }

        variations.addAll(this.variations);

        Set<ItemTypeRegistry.Entry> itemTypes = new HashSet<>();

        for (AliasFileEntry entry : entries) {
            for (String combination : variationCombinations(entry.getEntry(), variations)) {
                itemTypes.add(new ItemTypeRegistry.Entry(SkriptPattern.parse(combination)));
            }
        }

        return Collections.unmodifiableSet(itemTypes);
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
    private Collection<String> variationCombinations(@NotNull String pattern,
        @NotNull Collection<AliasFileVariation> variations) {
        Collection<String> patterns = new HashSet<>();

        int openIndex = pattern.indexOf('{');
        int closeIndex = pattern.indexOf('}');

        if (openIndex == -1 || closeIndex == -1 || openIndex + 1 > pattern.indexOf('}')) {
            patterns.add(pattern);
            return patterns;
        }

        for (AliasFileVariation variation : variations) {
            String name = variation.getName();
            String fullName = '{' + name + '}';
            int variationIndex = pattern.indexOf(fullName);

            if (variationIndex == -1) {
                continue;
            }

            String secondSubstring = pattern.substring(variationIndex + fullName.length());
            String firstSubstring = pattern.substring(0, variationIndex);

            for (String entry : variation.getEntries()) {
                patterns.addAll(variationCombinations(firstSubstring + entry + secondSubstring, variations));
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

                patterns.addAll(variationCombinations(replaced, variations));
            }
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
                file.directives.add(new AliasFileUseDirective(line.substring(":use".length()).trim()));
                continue;
            }

            int equalsIndex = line.indexOf('=');

            if (equalsIndex != -1) {
                boolean optional = false;
                String variationName = line.substring(0, equalsIndex).trim();

                if (variationName.startsWith("[") && variationName.endsWith("]")) {
                    optional = true;

                    variationName = variationName.substring(1, variationName.length() - 1);
                }

                StringBuilder variation = new StringBuilder(line.substring(equalsIndex + 1).trim());

                if (variation.charAt(0) != '{') {
                    throw new AliasFileFormatException(
                        "Variation declaration should have an opening curly bracket followed by it."
                    );
                }

                variation.deleteCharAt(0);

                while (!lines[i].contains("}")) {
                    variation.append(lines[i++]);
                }

                variation.deleteCharAt(variation.length() - 1);

                List<String> entries = new ArrayList<>();

                for (String entry : variation.toString().split(",")) {
                    entries.add(entry.trim());
                }

                file.variations.add(new AliasFileVariation(entries, variationName, optional));
                continue;
            }

            file.entries.add(new AliasFileEntry(line.trim()));
        }

        return file;
    }
}
