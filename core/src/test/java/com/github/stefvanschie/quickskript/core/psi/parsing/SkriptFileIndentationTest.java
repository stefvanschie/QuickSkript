package com.github.stefvanschie.quickskript.core.psi.parsing;

import com.github.stefvanschie.quickskript.core.file.FileSkript;
import com.github.stefvanschie.quickskript.core.file.SkriptFileNode;
import com.github.stefvanschie.quickskript.core.file.SkriptFileSection;
import com.github.stefvanschie.quickskript.core.psi.TestClassBase;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Tests whether a file has the indentations parsed correctly. This performs tests the Basic-sections.sk file.
 */
class SkriptFileIndentationTest extends TestClassBase {

    @Test
    void test() throws NoSuchFieldException, IllegalAccessException {
        FileSkript skript = getSampleSkripts().stream()
            .filter(sk -> sk.getName().equals("Dynamic-indentation"))
            .findAny()
            .orElseThrow();

        Field sectionField = FileSkript.class.getDeclaredField("section");
        sectionField.setAccessible(true);
        SkriptFileSection section = (SkriptFileSection) sectionField.get(skript);

        assertSection(section, 1, "on command");

        SkriptFileSection nodesIndentation1 = (SkriptFileSection) skript.getNodes().get(0);

        assertSection(nodesIndentation1, 5,
            "message \"test\" to the console",
            "true",
            "if true",
            "message \"test\" to the console",
            "while false");

        SkriptFileSection nodesIndentation2 = (SkriptFileSection) nodesIndentation1.getNodes().get(2);

        assertSection(nodesIndentation2, 2,
            "message \"test\" to the console",
            "if true");

        SkriptFileSection nodesIndentation3 = (SkriptFileSection) nodesIndentation2.getNodes().get(1);

        assertSection(nodesIndentation3, 1, "false");

        SkriptFileSection nodesIndentation4 = (SkriptFileSection) nodesIndentation1.getNodes().get(4);

        assertSection(nodesIndentation4, 2,
            "true",
            "message \"test\" to the console");
    }

    void assertSection(@NotNull SkriptFileSection section, int expectedSize, @NotNull String... lines) {
        assertEquals(expectedSize, section.getNodes().size());

        List<SkriptFileNode> nodes = section.getNodes();
        for (int i = 0; i < nodes.size(); i++) {
            assertEquals(lines[i], nodes.get(i).getText());
        }
    }
}
