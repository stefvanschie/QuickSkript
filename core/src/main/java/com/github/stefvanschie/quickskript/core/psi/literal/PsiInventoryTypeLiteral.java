package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.util.registry.InventoryTypeRegistry;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * Gets an inventory type
 *
 * @since 0.1.0
 */
public class PsiInventoryTypeLiteral extends PsiPrecomputedHolder<InventoryTypeRegistry.Entry> {

    /**
     * Creates a new element with the given line number
     *
     * @param inventoryType the inventory type
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiInventoryTypeLiteral(@NotNull InventoryTypeRegistry.Entry inventoryType, int lineNumber) {
        super(inventoryType, lineNumber);
    }

    /**
     * A factory for creating {@link PsiInventoryTypeLiteral}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * This gets called upon parsing
         *
         * @param skriptLoader the skript loader
         * @param text the text to parse
         * @param lineNumber the line number
         * @return the literal, or null to indicate failure
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Fallback
        public PsiInventoryTypeLiteral parse(@NotNull SkriptLoader skriptLoader, @NotNull String text, int lineNumber) {
            InventoryTypeRegistry inventoryTypeRegistry = skriptLoader.getInventoryTypeRegistry();
            Optional<InventoryTypeRegistry.Entry> inventoryType = inventoryTypeRegistry.getEntries().stream()
                .filter(entry -> entry.getName().equalsIgnoreCase(text))
                .findAny();

            if (inventoryType.isEmpty()) {
                return null;
            }

            return create(inventoryType.get(), lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param inventoryType the inventory type
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiInventoryTypeLiteral create(@NotNull InventoryTypeRegistry.Entry inventoryType, int lineNumber) {
            return new PsiInventoryTypeLiteral(inventoryType, lineNumber);
        }
    }
}
