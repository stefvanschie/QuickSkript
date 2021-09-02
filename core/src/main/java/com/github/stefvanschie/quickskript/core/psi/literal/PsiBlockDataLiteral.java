package com.github.stefvanschie.quickskript.core.psi.literal;

import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.PsiPrecomputedHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.util.Type;
import com.github.stefvanschie.quickskript.core.util.literal.BlockData;
import com.github.stefvanschie.quickskript.core.util.registry.BlockDataRegistry;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Gets block data by a specific name. This is always pre-computed.
 *
 * @since 0.1.0
 */
public class PsiBlockDataLiteral extends PsiPrecomputedHolder<BlockData> {

    /**
     * Creates a new element with the given line number
     *
     * @param blockData the block data
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiBlockDataLiteral(@NotNull BlockData blockData, int lineNumber) {
        super(blockData, lineNumber);
    }

    /**
     * A factory for creating {@link PsiBlockDataLiteral}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * Gets called upon an attempt to parse the given text.
         *
         * @param skriptLoader the skript loader to parse with
         * @param text the text to parse
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @Nullable
        @Contract(pure = true)
        @Fallback
        public PsiBlockDataLiteral parse(@NotNull SkriptLoader skriptLoader, @NotNull String text, int lineNumber) {
            String block = text;
            int openBracketIndex = block.indexOf('[');
            boolean hasOpenBracket = openBracketIndex != -1;

            if (hasOpenBracket) {
                block = block.substring(0, openBracketIndex);
            }

            block = block.replace(' ', '_');

            if (block.indexOf(':') == -1) {
                block = "minecraft:" + block;
            }

            BlockDataRegistry.Entry entry = skriptLoader.getBlockDataRegistry().getEntry(block);
            BlockData blockData = new BlockData(block);

            if (entry == null) {
                return null;
            }

            if (hasOpenBracket) {
                if (text.lastIndexOf(']') != text.length() - 1) {
                    return null;
                }

                String data = text.substring(openBracketIndex + 1, text.length() - 1);
                int commaIndex;

                while ((commaIndex = data.indexOf(',')) != -1) {
                    String pair = data.substring(0, commaIndex);
                    int equalsIndex = pair.indexOf('=');

                    if (equalsIndex == -1) {
                        return null;
                    }

                    blockData.addData(pair.substring(0, equalsIndex), pair.substring(equalsIndex + 1));
                }

                int equalsIndex = data.indexOf('=');

                if (equalsIndex == -1) {
                    return null;
                }

                blockData.addData(data.substring(0, equalsIndex), data.substring(equalsIndex + 1));
            }

            return create(blockData, lineNumber);
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param blockData the block data
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiBlockDataLiteral create(@NotNull BlockData blockData, int lineNumber) {
            return new PsiBlockDataLiteral(blockData, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.BLOCK_DATA;
        }
    }
}
