package com.github.stefvanschie.quickskript.core.psi.util;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A psi element which stores a {@link Collection} of the {@link PsiElement}s specified in the constructor.
 * It is considered pre computed only if all of its stored elements are also pre computed.
 * Please note that no {@link Collection} cloning is done internally: modifications of
 * the constructor parameter and returned {@link Collection} have visible consequences.
 *
 * @since 0.1.0
 */
public final class PsiCollection<T> extends PsiElement<Collection<T>> {

    /**
     * The stored elements.
     */
    private Collection<PsiElement<T>> elements;

    /**
     * Creates a new psi element which stores the specified {@link Collection}.
     *
     * @param elements the element this psi should store
     * @param lineNumber the line number of this element
     * @since 0.1.0
     */
    public PsiCollection(@NotNull Collection<PsiElement<T>> elements, int lineNumber) {
        super(lineNumber);
        this.elements = elements;

        if (elements.stream().allMatch(PsiElement::isPreComputed)) {
            preComputed = executeImpl(null);
            this.elements = null;
        }
    }

    /**
     * Creates a new psi element which stores the specified ones.
     * The {@link Stream} is internally collected, therefore terminated.
     *
     * @param elements the element this psi should store
     * @param lineNumber the line number of this element
     * @since 0.1.0
     */
    public PsiCollection(@NotNull Stream<PsiElement<T>> elements, int lineNumber) {
        this(elements.collect(Collectors.toList()), lineNumber);
    }

    /**
     * Creates a new psi element which stores the specified ones.
     *
     * @param lineNumber the line number of this element
     * @param elements the element this psi should store
     * @since 0.1.0
     */
    public PsiCollection(@NotNull PsiElement<T>[] elements, int lineNumber) {
        this(Arrays.asList(elements), lineNumber);
    }

    @NotNull
    @Override
    protected Collection<T> executeImpl(@Nullable Context context) {
        return elements.stream()
                .map(e -> (T) e.execute(context))
                .collect(Collectors.toList());
    }



    @Contract(pure = true)
    public static boolean isCollection(@Nullable Object value) {
        return value != null && (value instanceof Iterable<?> || value.getClass().isArray());
    }

    public static int getSize(@Nullable Object value, int notCollectionResult) {
        if (value instanceof Collection<?>) {
            return ((Collection<?>) value).size();
        } else if (value instanceof Iterable<?>) {
            int size = 0;
            for (Object ignored : (Iterable<?>) value) {
                size++;
            }
            return size;
        } else if (value != null && value.getClass().isArray()) {
            return Array.getLength(value);
        } else {
            return notCollectionResult;
        }
    }

    public static void forEach(@Nullable Object value,
            @NotNull Consumer<Object> action, @Nullable Consumer<Object> notCollectionAction) {
        if (value instanceof Iterable<?>) {
            for (Object object : (Iterable<?>) value) {
                action.accept(object);
            }
        } else if (value != null && value.getClass().isArray()) {
            int length = Array.getLength(value);
            for (int i = 0; i < length; i++) {
                action.accept(Array.get(value, i));
            }
        } else {
            (notCollectionAction == null
                    ? action
                    : notCollectionAction)
                    .accept(value);
        }
    }

    @NotNull
    public static Stream<Object> toStreamForgiving(@Nullable Object value) {
        Stream<Object> stream = toStreamStrict(value);
        return stream == null ? Stream.of(value) : stream;
    }

    @Nullable
    public static Stream<Object> toStreamStrict(@Nullable Object value) {
        if (value instanceof Iterable<?>) {
            return StreamSupport.stream(((Iterable<Object>) value).spliterator(), false);
        } else if (value != null && value.getClass().isArray()) {
            Object[] array = new Object[Array.getLength(value)];
            Arrays.setAll(array, i -> Array.get(value, i));
            return Arrays.stream(array);
        } else {
            return null;
        }
    }
}
