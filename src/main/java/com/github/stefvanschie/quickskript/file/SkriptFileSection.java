package com.github.stefvanschie.quickskript.file;

import com.github.stefvanschie.quickskript.psi.PsiElement;
import com.github.stefvanschie.quickskript.psi.PsiSection;
import com.github.stefvanschie.quickskript.psi.exception.ParseException;
import com.github.stefvanschie.quickskript.psi.section.PsiIf;
import com.github.stefvanschie.quickskript.skript.SkriptLoader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Supplier;

/**
 * Represents a section of skript lines
 *
 * @since 0.1.0
 */
public class SkriptFileSection extends SkriptFileNode {

    /**
     * The underlying nodes in this section
     */
    @NotNull
    private final List<SkriptFileNode> nodes = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    SkriptFileSection(@NotNull String text, int lineNumber) {
        super(text, lineNumber);
    }

    /**
     * Returns a list of nodes
     *
     * @return the nodes
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public List<SkriptFileNode> getNodes() {
        return nodes;
    }

    /**
     * Parses all of the nodes, including the ones inside nested
     * {@link SkriptFileSection}s into a psi structure
     *
     * @return the nodes parsed into a psi structure
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public PsiElement<?>[] parseNodes() {
        //TODO stuff to look out for:
        // - there is a 'wait # <timestamp>' expression (maybe a preprocessor for stuff like this? - indent it?)
        // - exit (optional number) expression exists
        // - stop <?> expression exists

        Deque<PsiElement<?>> result = new ArrayDeque<>(nodes.size());
        SkriptLoader loader = SkriptLoader.get();

        PsiIf latestValidIf = null;

        for (SkriptFileNode node : nodes) {
            if (!(node instanceof SkriptFileSection)) {
                result.add(loader.forceParseElement(node.getText(), node.getLineNumber()));
                continue;
            }

            String text = node.getText();
            boolean elseSection = text.startsWith("else ");

            if (elseSection) {
                if (latestValidIf == null)
                    throw new ParseException("No available if statement was found for the else statement", node.getLineNumber());

                text = node.getText().substring("else ".length());
            }

            SkriptFileSection fileSection = (SkriptFileSection) node;
            Supplier<PsiElement<?>[]> elementsSupplier = fileSection::parseNodes;
            PsiSection section = loader.forceParseSection(text, elementsSupplier, node.getLineNumber());

            if (elseSection) {
                latestValidIf.setElseSection(section);
            }

            latestValidIf = section instanceof PsiIf ? (PsiIf) section : null;
            result.add(section);
        }

        return result.toArray(PsiElement[]::new);
    }

    /**
     * Parses a section from the given header and strings
     *
     * @param nodes the underlying nodes
     * @param lineNumberOffset the offset of the line number from the starting node. This value represents the line
     * number of the first node in the list.
     * @since 0.1.0
     */
    void parse(@NotNull List<String> nodes, int lineNumberOffset) {
        for (int index = 0; index < nodes.size(); index++) {
            String node = nodes.get(index);

            if (node.isEmpty())
                continue;

            int newLineNumberOffset = lineNumberOffset + index;

            //isn't a section
            if (!node.endsWith(":")) {
                getNodes().add(new SkriptFileLine(node, newLineNumberOffset));
                continue;
            }

            int i = index;
            do {
                if (++i == nodes.size())
                    break;
            } while (nodes.get(i).startsWith("    ") && i != nodes.size() - 1);

            if (i == nodes.size()) {
                continue;
            }

            List<String> strings = new ArrayList<>(nodes).subList(index + 1,
                    i == nodes.size() - 1 && nodes.get(i).startsWith("    ") ? i + 1 : i);

            for (int j = 0; j < strings.size(); j++)
                strings.set(j, strings.get(j).substring(4));

            String header = nodes.get(index);

            SkriptFileSection skriptFileSection =
                    new SkriptFileSection(header.substring(0, header.length() - 1), newLineNumberOffset);

            skriptFileSection.parse(strings, newLineNumberOffset + 1);

            getNodes().add(skriptFileSection);

            index += strings.size();
        }
    }
}
