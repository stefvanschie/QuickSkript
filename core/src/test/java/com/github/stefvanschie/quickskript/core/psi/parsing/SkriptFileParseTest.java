package com.github.stefvanschie.quickskript.core.psi.parsing;

import com.github.stefvanschie.quickskript.core.file.SkriptFile;
import com.github.stefvanschie.quickskript.core.file.SkriptFileLine;
import com.github.stefvanschie.quickskript.core.file.SkriptFileNode;
import com.github.stefvanschie.quickskript.core.psi.TestClassBase;
import com.github.stefvanschie.quickskript.core.psi.exception.ParseException;
import com.github.stefvanschie.quickskript.core.skript.Skript;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * A test which asserts that all specified skript files
 * can be parsed without any exceptions being raised.
 */
class SkriptFileParseTest extends TestClassBase {

    @Test
    void test() {
        for (Skript skript : getSampleSkripts()) {
            try {
                skript.registerCommands();
                skript.registerEventExecutors();
                System.out.println("Successfully parsed: " + skript.getName());
            } catch (ParseException e) {
                throw new AssertionError("Error while parsing:" + e.getExtraInfo(skript.getName()), e);
            }
        }
    }

    @Test
    void testTabCharacterHandling() {
        String tabReplacement = "    ";

        List<SkriptFileNode> nodes = SkriptFile.load(Arrays.asList(
            "\tmessage \"Hello, world!\" to the console",
            "\tmessage \"1532793000\" to the console"
        )).getNodes();
        List<String> expected = List.of(
            tabReplacement + "message \"Hello, world!\" to the console",
            tabReplacement + "message \"1532793000\" to the console"
        );

        for (int i = 0; i < nodes.size(); i++) {
            SkriptFileNode node = nodes.get(i);

            Assertions.assertTrue(node instanceof SkriptFileLine);
            Assertions.assertEquals(expected.get(i), node.getText());
        }
    }
}
