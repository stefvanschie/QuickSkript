package com.github.stefvanschie.quickskript.core.skript.loader;

import com.github.stefvanschie.quickskript.core.pattern.SkriptMatchResult;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.pattern.group.SkriptPatternGroup;
import com.github.stefvanschie.quickskript.core.pattern.group.TypeGroup;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.Fallback;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.exception.IllegalFallbackAnnotationAmountException;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.PatternTypeOrder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.PatternTypeOrderHolder;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.exception.ParsingAnnotationInvalidValueException;
import com.github.stefvanschie.quickskript.core.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class RegisteredPsiElementFactory {
    //TODO docs
    //TODO which exception to throw?
    //TODO better exception handling?

    @NotNull
    private final PsiElementFactory factory;

    @NotNull
    private final List<CachedNormalMethod> normalMethods = new ArrayList<>();

    @Nullable
    private final CachedFallbackMethod fallbackMethod;

    public RegisteredPsiElementFactory(@NotNull PsiElementFactory factory) {
        this.factory = factory;

        for (Method method : factory.getClass().getMethods()) {
            Pattern patternPointer = method.getAnnotation(Pattern.class);
            if (patternPointer == null) {
                continue;
            }

            Field patternField = findPatternField(factory.getClass(), patternPointer.value());
            if (patternField == null) {
                throw new ParsingAnnotationInvalidValueException("Pattern field does not exist: " + patternPointer.value());
            }

            SkriptPattern[] patterns = getPatternFieldValue(factory, patternField);
            PatternTypeOrder[] orders = null;
            PatternTypeOrderHolder orderHolder = method.getAnnotation(PatternTypeOrderHolder.class);
            if (orderHolder != null) {
                orders = getPatternTypeOrders(patterns.length, orderHolder);
            }

            for (SkriptPattern pattern : patterns) {
                validateMethodParameterCount(method, pattern);
            }

            normalMethods.add(new CachedNormalMethod(method, patterns, orders));
        }

        Method fallback = findFallbackMethod(factory.getClass());
        fallbackMethod = fallback == null ? null : new CachedFallbackMethod(fallback);
    }

    public PsiElement<?> tryParse(@NotNull SkriptLoader skriptLoader, @NotNull String input, int lineNumber) {
        try {
            for (CachedNormalMethod method : normalMethods) {
                for (int patternIndex = 0; patternIndex < method.patterns.length; patternIndex++) {
                    SkriptPattern pattern = method.patterns[patternIndex];
                    PatternTypeOrder order = method.orders == null ? null : method.orders[patternIndex];

                    PsiElement<?> result = tryParse(skriptLoader, method, pattern, order, input, lineNumber);
                    if (result != null) {
                        return result;
                    }
                }
            }

            if (fallbackMethod == null) {
                return null;
            }

            int parameterCount = 2;
            if (fallbackMethod.includeSkriptLoader) { parameterCount++; }

            Object[] parameters = new Object[parameterCount];
            int offset = 0;

            if (fallbackMethod.includeSkriptLoader) {
                parameters[offset++] = skriptLoader;
            }

            parameters[offset++] = input;
            parameters[offset] = lineNumber;

            return (PsiElement<?>) fallbackMethod.method.invoke(factory, parameters);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace(); //TODO am I sure?
            return null;
        }
    }

    @Nullable
    private PsiElement<?> tryParse(@NotNull SkriptLoader skriptLoader, @NotNull CachedNormalMethod method,
            @NotNull SkriptPattern pattern, @Nullable PatternTypeOrder order,
            @NotNull String input, int lineNumber) throws ReflectiveOperationException {
        results:
        for (SkriptMatchResult result : pattern.match(input)) {
            if (result.hasUnmatchedParts()) {
                continue;
            }

            Collection<Pair<SkriptPatternGroup, String>> matchedGroups = result.getMatchedGroups();

            List<TypeGroup> groups = new ArrayList<>();

            for (Pair<SkriptPatternGroup, String> matchedGroup : matchedGroups) {
                SkriptPatternGroup group = matchedGroup.getX();

                if (group instanceof TypeGroup) {
                    groups.add((TypeGroup) group);
                }
            }

            List<String> matchedTypeTexts = new ArrayList<>();
            for (Pair<SkriptPatternGroup, String> matchedGroup : matchedGroups) {
                if (matchedGroup.getX() instanceof TypeGroup) {
                    matchedTypeTexts.add(matchedGroup.getY());
                }
            }

            List<TypeGroup> typeGroups = new ArrayList<>();
            for (SkriptPatternGroup group : pattern.getGroups()) {
                if (group instanceof TypeGroup) {
                    typeGroups.add((TypeGroup) group);
                }
            }

            Object[] elements = new Object[typeGroups.size()];
            for (int i = 0; i < elements.length && i < groups.size(); i++) {
                int elementIndex = typeGroups.indexOf(groups.get(i));

                if (order != null && !Arrays.equals(order.typeOrder(), new int[]{})) {
                    elementIndex = order.typeOrder()[i];

                    if (elements[elementIndex] != null) {
                        throw new ParsingAnnotationInvalidValueException(
                                "PatternTypeOrder contains duplicate number '" + elementIndex + "'"
                        );
                    }
                }

                String matchedTypeText = matchedTypeTexts.get(i);
                if (groups.get(i).getConstraint() == TypeGroup.Constraint.LITERAL) {
                    elements[elementIndex] = matchedTypeText;
                } else {
                    PsiElement<?> parsed = skriptLoader.tryParseElement(matchedTypeText, lineNumber);
                    if (parsed == null) {
                        continue results;
                    } else {
                        elements[elementIndex] = parsed;
                    }
                }
            }

            int parameterCount = elements.length + 1;
            if (method.includeSkriptLoader) { parameterCount++; }
            if (method.includeMatchResult) { parameterCount++; }

            Object[] parameters = new Object[parameterCount];
            int offset = 0;

            if (method.includeSkriptLoader) {
                parameters[offset++] = skriptLoader;
            }

            if (method.includeMatchResult) {
                parameters[offset++] = result;
            }

            System.arraycopy(elements, 0, parameters, offset, elements.length);
            parameters[parameters.length - 1] = lineNumber;

            PsiElement<?> element = (PsiElement<?>) method.method.invoke(factory, parameters);
            if (element == null) {
                continue;
            }

            for (Object child : elements) {
                if (child instanceof PsiElement<?>) {
                    ((PsiElement<?>) child).setParent(element);
                }
            }

            return element;
        }
        return null;
    }

    @Nullable
    private static Field findPatternField(@NotNull Class<?> clazz, @NotNull String name) {
        do {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.getName().equals(name)) {
                    field.setAccessible(true);
                    return field;
                }
            }
            clazz = clazz.getSuperclass();
        } while (clazz != null);
        return null;
    }

    @NotNull
    private static SkriptPattern[] getPatternFieldValue(@NotNull PsiElementFactory factory, @NotNull Field field) {
        try {
            Class<?> type = field.getType();
            if (type == SkriptPattern.class) {
                return new SkriptPattern[]{
                        (SkriptPattern) field.get(factory)
                };
            } else if (type == SkriptPattern[].class) {
                return (SkriptPattern[]) field.get(factory);
            } else {
                throw new ParsingAnnotationInvalidValueException("Invalid pattern field type: " + type.getSimpleName());
            }
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Expected the specified field to be accessible", e);
        }
    }

    @NotNull
    private static PatternTypeOrder[] getPatternTypeOrders(int patternCount, @NotNull PatternTypeOrderHolder orderHolder) {
        PatternTypeOrder[] orders = new PatternTypeOrder[patternCount];
        for (PatternTypeOrder order : orderHolder.value()) {
            for (int patternIndex : order.patterns()) {
                if (orders[patternIndex] == null) {
                    orders[patternIndex] = order;
                } else {
                    throw new ParsingAnnotationInvalidValueException("Multiple PatternTypeOrder on the same method specify the same pattern");
                }
            }
        }
        return orders;
    }

    private static void validateMethodParameterCount(@NotNull Method method, @NotNull SkriptPattern pattern) {
        int count = 0;
        for (SkriptPatternGroup group : pattern.getGroups()) {
            if (group instanceof TypeGroup) {
                count++;
            }
        }

        //the + 1 is for the line number
        if (method.getParameterCount() < count + 1) {
            throw new IllegalStateException("Method '" + method.getName() + "' has "
                    + method.getParameterCount() + " parameters, but we expected at least " +
                    (count + 1) + " parameters");
        }
    }

    @Nullable
    private static Method findFallbackMethod(@NotNull Class<?> clazz) {
        Method fallback = null;
        for (Method method : clazz.getMethods()) {
            if (method.getAnnotation(Fallback.class) == null) {
                continue;
            }

            if (fallback == null) {
                fallback = method;
            } else {
                throw new IllegalFallbackAnnotationAmountException("Illegal amount of fallback annotations detected. Maximum is 1, but there were multiple.");
            }
        }
        return fallback;
    }

    private abstract static class CachedMethod {

        @NotNull
        final Method method;

        final boolean includeSkriptLoader;

        final boolean includeMatchResult; //TODO always false for CachedFallbackMethod

        CachedMethod(@NotNull Method method) {
            this.method = method;

            int offset = 0;
            Class<?>[] parameters = method.getParameterTypes();

            includeSkriptLoader = parameters[offset] == SkriptLoader.class;
            if (includeSkriptLoader) {
                offset++;
            }

            includeMatchResult = parameters[offset] == SkriptMatchResult.class;
        }
    }

    private static class CachedNormalMethod extends CachedMethod {

        @NotNull
        final SkriptPattern[] patterns;

        @Nullable
        final PatternTypeOrder[] orders;

        CachedNormalMethod(@NotNull Method method, @NotNull SkriptPattern[] patterns, @Nullable PatternTypeOrder[] orders) {
            super(method);
            this.patterns = patterns;
            this.orders = orders;
        }
    }

    private static class CachedFallbackMethod extends CachedMethod {

        CachedFallbackMethod(@NotNull Method method) {
            super(method);
        }
    }
}
