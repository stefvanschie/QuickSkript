package com.github.stefvanschie.quickskript.core.file.alias;

import com.github.stefvanschie.quickskript.core.file.alias.manager.AliasFileManager;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class AliasFileTest {

    @Test
    void testParse() {
        AliasFileManager manager = new AliasFileManager();

        assertDoesNotThrow(() -> {
            URL aliasURL = getClass().getClassLoader().getResource("test.alias");

            assertNotNull(aliasURL);

            manager.read(aliasURL, "test.alias");
        });

        Collection<? extends ResolvedAliasEntry> entries = manager.resolveAll();

        ResolvedAliasEntry testEntry = null;

        for (ResolvedAliasEntry entry : entries) {
            if ("test:item".equals(entry.getKey())) {
                assertNull(testEntry);

                testEntry = entry;
            }
        }

        assertNotNull(testEntry);

        assertTrue(testEntry.getCategories().contains(SkriptPattern.parse("category1")));
        assertTrue(testEntry.getCategories().contains(SkriptPattern.parse("category2")));
    }
}
