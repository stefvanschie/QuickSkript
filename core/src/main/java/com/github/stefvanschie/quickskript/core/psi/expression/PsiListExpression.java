package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.MultiResult;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.connective.Conjunction;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.connective.Connective;
import com.github.stefvanschie.quickskript.core.psi.util.multiresult.connective.Disjunction;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.skript.SkriptLoader;
import com.github.stefvanschie.quickskript.core.skript.SkriptRunEnvironment;
import com.github.stefvanschie.quickskript.core.util.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A list of objects.
 *
 * @since 0.1.0
 */
public class PsiListExpression extends PsiElement<MultiResult<Object>> {

    /**
     * The elements of this list.
     */
    private List<@NotNull PsiElement<?>> elements;

    /**
     * The connective between the elements of this list.
     */
    @NotNull
    private final Connective connective;

    /**
     * Creates a new expression for a list of objects.
     *
     * @param elements the elements in this list
     * @param connective the connective between the elements of this list
     * @param lineNumber the line number
     * @since 0.1.0
     */
    private PsiListExpression(
        @NotNull List<@NotNull PsiElement<?>> elements,
        @NotNull Connective connective,
        int lineNumber
    ) {
        super(lineNumber);

        this.elements = elements;
        this.connective = connective;

        for (PsiElement<?> element : this.elements) {
            if (!element.isPreComputed()) {
                return;
            }
        }

        this.preComputed = executeImpl(null, null);
        this.elements = null;
    }

    @NotNull
    @Override
    protected MultiResult<Object> executeImpl(@Nullable SkriptRunEnvironment environment, @Nullable Context context) {
        Object[] objects = new Object[this.elements.size()];

        for (int index = 0; index < objects.length; index++) {
            objects[index] = this.elements.get(index).execute(environment, context);
        }

        return new MultiResult<>(objects, this.connective);
    }

    /**
     * A factory for creating {@link PsiListExpression}s.
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        @Nullable
        @Contract(pure = true)
        @Fallback
        public PsiElement<?> parse(@NotNull SkriptLoader loader, @NotNull String text, int lineNumber) {
            List<String> segments = Arrays.asList(text.split(","));

            //don't make singleton lists
            if (segments.size() <= 1) {
                return null;
            }

            List<List<String>> partitions = partitions(segments);

        outer:
            for (List<String> partition : partitions) {
                Connective connective = Conjunction.INSTANCE;
                List<PsiElement<?>> elements = new ArrayList<>();

                for (String segment : partition) {
                    segment = segment.trim();

                    PsiElement<?> element = null;

                    if (segment.startsWith("and") || segment.startsWith("nor")) {
                        int prefixLength = 3; //length of "and" / "nor"

                        String noDelimiter = segment.substring(prefixLength).trim();

                        if (noDelimiter.length() > 0 && noDelimiter.charAt(0) == '(' && noDelimiter.endsWith(")")) {
                            String noParenthesis = noDelimiter.substring(1, noDelimiter.length() - 1).trim();
                            element = loader.tryParseElement(noParenthesis, lineNumber);
                        }

                        if (element == null) {
                            element = loader.tryParseElement(segment.substring(prefixLength).trim(), lineNumber);
                        }

                        if (element != null) {
                            connective = Conjunction.INSTANCE;
                        }
                    } else if (segment.startsWith("or")) {
                        String noDelimiter = segment.substring("or".length()).trim();

                        if (noDelimiter.length() > 0 && noDelimiter.charAt(0) == '(' && noDelimiter.endsWith(")")) {
                            String noParenthesis = noDelimiter.substring(1, noDelimiter.length() - 1).trim();
                            element = loader.tryParseElement(noParenthesis, lineNumber);
                        }

                        if (element == null) {
                            element = loader.tryParseElement(noDelimiter, lineNumber);
                        }

                        if (element != null) {
                            connective = Disjunction.INSTANCE;
                        }
                    }

                    if (element == null && segment.length() > 0 && segment.charAt(0) == '(' && segment.endsWith(")")) {
                        String noParenthesis = segment.substring(1, segment.length() - 1).trim();
                        element = loader.tryParseElement(noParenthesis, lineNumber);
                    }

                    if (element == null) {
                        element = loader.tryParseElement(segment, lineNumber);
                    }

                    if (element == null) {
                        continue outer;
                    }

                    elements.add(element);
                }

                return create(elements, connective, lineNumber);
            }

            return null;
        }

        /**
         * Partitions the given list of segments in all possible ways (excluding one large partition). Combined segments
         * are combined by appending the segments with a ',' in between.
         *
         * @param segments the segments to partition
         * @return a list of all possible partitions
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public List<@NotNull List<@NotNull String>> partitions(@NotNull List<@NotNull String> segments) {
            List<List<String>> partitions = new ArrayList<>();

            //cutPoints > 0 : don't generate a singleton partition
            for (int cutPoints = (1 << (segments.size() - 1)) - 1; cutPoints > 0; cutPoints--) {
                List<String> result = new ArrayList<>();
                int lastCut = 0;

                for (int index = 0; index < segments.size() - 1; index++) {
                    if ((1 << index & cutPoints) != 0) {
                        StringBuilder combined = new StringBuilder();
                        List<String> subList = segments.subList(lastCut, index + 1);

                        for (String segment : subList) {
                            combined.append(segment).append(',');
                        }

                        combined.setLength(combined.length() - 1);

                        result.add(combined.toString());
                        lastCut = index + 1;
                    }
                }

                StringBuilder combined = new StringBuilder();
                List<String> subList = segments.subList(lastCut, segments.size());

                for (String segment : subList) {
                    combined.append(segment).append(',');
                }

                combined.setLength(combined.length() - 1);

                result.add(combined.toString());
                partitions.add(result);
            }

            return partitions;
        }

        /**
         * Creates a new {@link PsiListExpression}.
         *
         * @param elements the elements of the list
         * @param connective the interaction between the list's elements
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiListExpression create(
            @NotNull List<@NotNull PsiElement<?>> elements,
            @NotNull Connective connective,
            int lineNumber
        ) {
            return new PsiListExpression(elements, connective, lineNumber);
        }

        @NotNull
        @Contract(pure = true)
        @Override
        public Type getType() {
            return Type.OBJECTS;
        }
    }
}
