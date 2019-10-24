package com.github.stefvanschie.quickskript.core.psi.execution;

import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.TestClassBase;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.expression.PsiRandomNumberExpression;
import com.github.stefvanschie.quickskript.core.psi.function.*;
import com.github.stefvanschie.quickskript.core.psi.literal.PsiNumberLiteral;
import com.github.stefvanschie.quickskript.core.psi.util.PsiCollection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests whether psi numbers can be executed without any exceptions
 * other than {@link ExecutionException} being raised.
 * This test does not validate the result of the functions,
 * apart from them being pre computed or not.
 */
class PsiNumberExecutabilityTest extends TestClassBase {

    private static final double CHANCE_OF_ZERO = 0.15;
    private static final long MIN_SOURCE_VALUE = -100;
    private static final long MAX_SOURCE_VALUE = 100;

    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    private int instanceCount;

    @SuppressWarnings("CanBeFinal")
    private Function<Double, PsiElement<Number>> literalConstructor;

    private final Supplier<PsiElement<Number>> literalSupplier = () -> {
        if (random.nextDouble() < CHANCE_OF_ZERO)
            return literalConstructor.apply(0.0);

        return literalConstructor.apply(random.nextDouble(MIN_SOURCE_VALUE, MAX_SOURCE_VALUE));
    };

    @SuppressWarnings("CanBeFinal")
    private BiFunction<Double, Double, PsiElement<Number>> randomConstructor;

    private final Supplier<PsiElement<Number>> randomSupplier = () -> {
        double min = random.nextDouble(MIN_SOURCE_VALUE, MAX_SOURCE_VALUE);

        double max;
        do {
            max = random.nextDouble(MIN_SOURCE_VALUE, MAX_SOURCE_VALUE);
        } while (Double.compare(min, max) == 0);

        return randomConstructor.apply(min, max);
    };

    private final List<Constructor<?>> monoFunctions = Stream.of(
        PsiAbsoluteValueFunction.class,
        PsiCalculateExperienceFunction.class,
        PsiCeilFunction.class,
        PsiCosineFunction.class,
        PsiExponentialFunction.class,
        PsiFloorFunction.class,
        PsiInverseCosineFunction.class,
        PsiInverseSineFunction.class,
        PsiInverseTangentFunction.class,
        PsiNaturalLogarithmFunction.class,
        PsiRoundFunction.class,
        PsiSineFunction.class,
        PsiSquareRootFunction.class,
        PsiTangentFunction.class
    ).map(clazz -> getConstructor(clazz, PsiElement.class, int.class)).collect(Collectors.toList());

    private final List<Constructor<?>> biFunctions = Stream.of(
        PsiAtan2Function.class,
        PsiModuloFunction.class,
        PsiLogarithmFunction.class
    ).map(clazz -> getConstructor(clazz, PsiElement.class, PsiElement.class, int.class)).collect(Collectors.toList());

    private final List<Constructor<?>> collectionFunctions = Stream.of(
        PsiMaximumFunction.class,
        PsiMinimumFunction.class,
        PsiProductFunction.class,
        PsiSumFunction.class
    ).map(clazz -> getConstructor(clazz, PsiElement.class, int.class)).collect(Collectors.toList());


    PsiNumberExecutabilityTest() {
        Constructor<PsiElement<Number>> literal = (Constructor<PsiElement<Number>>)
            getConstructor(PsiNumberLiteral.class, double.class, int.class);
        literalConstructor = value -> {
            try {
                return (PsiElement<Number>) literal.newInstance(value, -1);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        };

        Constructor<PsiElement<Number>> random = (Constructor<PsiElement<Number>>)
            getConstructor(PsiRandomNumberExpression.class, boolean.class, PsiElement.class, PsiElement.class, int.class);
        randomConstructor = (min, max) -> {
            try {
                return (PsiElement<Number>) random.newInstance(false,
                    literalConstructor.apply(min), literalConstructor.apply(max), -1);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        };
    }


    @Test
    void test() throws ReflectiveOperationException {
        int runCount = 100;

        for (int i = 0; i < runCount / 2; i++) {
            test(literalSupplier, true);
            test(randomSupplier, false);
        }

        System.out.println("Average PsiNumberExecutabilityTest instance count: " + ((float)instanceCount / runCount));
    }

    private void test(@NotNull Supplier<PsiElement<Number>> supplier, boolean precomputed) throws ReflectiveOperationException {
        try {
            RunContext context = new RunContext(supplier);
            PsiElement<Number> result = next(context);
            assertEquals(precomputed, result.isPreComputed());
            result.execute(null, Number.class);
        } catch (ExecutionException e) {
            System.out.println("Acceptable exception was thrown");
        }
    }


    @NotNull
    private PsiElement<Number> next(@NotNull RunContext context) throws ReflectiveOperationException {
        PsiElement<Number> direct = context.tryGetDirectValue();
        if (direct != null)
            return direct;

        instanceCount++;
        int clazz = random.nextInt(monoFunctions.size() + biFunctions.size() + collectionFunctions.size());

        if (clazz < monoFunctions.size()) {
            return (PsiElement<Number>) monoFunctions.get(clazz).newInstance(next(context), -1);
        }

        clazz -= monoFunctions.size();
        if (clazz < biFunctions.size()) {
            return (PsiElement<Number>) biFunctions.get(clazz).newInstance(next(context), next(context), -1);
        }

        PsiElement<Number>[] parameters = (PsiElement<Number>[]) new PsiElement[random.nextInt(1, 5)];
        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = next(context);
        }

        clazz -= biFunctions.size();
        return (PsiElement<Number>) collectionFunctions.get(clazz)
            .newInstance(new PsiCollection<>(parameters, -1), -1);
    }


    private static Constructor<?> getConstructor(@NotNull Class<?> clazz, @NotNull Class<?>... parameters) {
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor(parameters);
            constructor.setAccessible(true);
            return constructor;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }


    private class RunContext {
        private final Supplier<PsiElement<Number>> directSupplier;
        private double directChance;

        RunContext(@NotNull Supplier<PsiElement<Number>> directSupplier) {
            this.directSupplier = directSupplier;
        }

        @Nullable
        PsiElement<Number> tryGetDirectValue() {
            PsiElement<Number> number = random.nextDouble() < directChance ? directSupplier.get() : null;
            directChance += 0.00125;
            directChance *= 1.0125;
            return number;
        }
    }
}
