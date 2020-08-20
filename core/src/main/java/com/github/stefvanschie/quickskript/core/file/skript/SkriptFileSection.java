package com.github.stefvanschie.quickskript.core.file.skript;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiSection;
import com.github.stefvanschie.quickskript.core.psi.exception.ParseException;
import com.github.stefvanschie.quickskript.core.psi.section.PsiIf;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Represents a section of skript lines
 *
 * @since 0.1.0
 */
public class SkriptFileSection extends SkriptFileNode {

    /**
     * An immutable list of the underlying nodes in this section
     */
    @NotNull
    private final List<SkriptFileNode> nodes;

    /**
     * Creates a new section with the specified text
     *
     * @param text the text
     * @param lineNumber the line number of the text this node represents
     * @param nodes the nodes to parse
     * @since 0.1.0
     */
    SkriptFileSection(@NotNull String text, int lineNumber, @NotNull List<String> nodes) {
        super(text, lineNumber);
        this.nodes = Collections.unmodifiableList(parse(nodes));
    }

    /**
     * Returns an immutable list of the underlying nodes in this section
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
     * sections into a psi structure
     *
     * @return the nodes parsed into a psi structure
     * @since 0.1.0
     */
    @NotNull
    @Contract(pure = true)
    public PsiElement<?>[] parseNodes(@NotNull SkriptLoader skriptLoader) {
        Deque<PsiElement<?>> result = new ArrayDeque<>(nodes.size());

        PsiIf latestValidIf = null;

        for (SkriptFileNode node : nodes) {
            if (!(node instanceof SkriptFileSection)) {
                result.add(skriptLoader.forceParseElement(node.getText(), node.getLineNumber()));
                continue;
            }

            String text = node.getText();
            boolean elseSection = text.startsWith("else ");

            if (elseSection) {
                if (latestValidIf == null) {
                    throw new ParseException("No available if statement was found for the else statement", node.getLineNumber());
                }

                text = node.getText().substring("else ".length());
            }

            PsiSection section = skriptLoader.forceParseSection(text,
                () -> ((SkriptFileSection) node).parseNodes(skriptLoader), node.getLineNumber());

            if (elseSection) {
                latestValidIf.setElseSection(section);
            } else {
                result.add(section);
            }

            latestValidIf = section instanceof PsiIf ? (PsiIf) section : null;
        }

        return result.toArray(PsiElement[]::new);
    }

    /**
     * Parses a section from the given header and strings
     *
     * @param nodes the underlying nodes
     * @return a mutable list of parsed nodes
     * @since 0.1.0
     */
    private List<SkriptFileNode> parse(@NotNull List<String> nodes) {
        int lineNumberOffset = getLineNumber();
        List<SkriptFileNode> outputNodes = new ArrayList<>();

        for (int index = 0; index < nodes.size(); index++) {
            lineNumberOffset++;

            String node = nodes.get(index);

            if (node.isEmpty()) {
                continue;
            }

            //isn't a section
            if (!node.endsWith(":")) {
                outputNodes.add(new SkriptFileLine(node, lineNumberOffset));
                continue;
            }

            //amount of leading spaces may be anywhere between 1 and inf. and this calculates the indentation of the section
            StringBuilder indentationBuilder = new StringBuilder();
            int whitespaceIndex = 0;

            do {
                indentationBuilder.append(' ');
                whitespaceIndex++;
            } while (nodes.get(index + 1).charAt(whitespaceIndex) == ' ');

            String indentation = indentationBuilder.toString();

            int i = index;
            do {
                if (++i == nodes.size())
                    break;
            } while (nodes.get(i).startsWith(indentation) && i != nodes.size() - 1);

            if (i == nodes.size()) {
                continue;
            }

            List<String> strings = new ArrayList<>(nodes).subList(index + 1,
                    i == nodes.size() - 1 && nodes.get(i).startsWith(indentation) ? i + 1 : i);

            for (int j = 0; j < strings.size(); j++) {
                strings.set(j, strings.get(j).substring(indentation.length()));
            }

            String header = nodes.get(index);
            header = header.substring(0, header.length() - 1);

            outputNodes.add(new SkriptFileSection(header, lineNumberOffset, strings));

            index += strings.size();
        }

        return outputNodes;
    }
}
