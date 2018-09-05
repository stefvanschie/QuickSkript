package com.github.stefvanschie.quickskript.psi;

import com.github.stefvanschie.quickskript.TestClassBase;
import com.github.stefvanschie.quickskript.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.psi.function.*;
import com.github.stefvanschie.quickskript.psi.literal.PsiCollection;
import com.github.stefvanschie.quickskript.skript.SkriptLoader;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

/**
 * Tests whether psi numbers can be executed without any exceptions
 * other than {@link ExecutionException} being raised.
 * This test does not validate the result of the functions,
 * apart from them being pre computed or not.
 */
class PsiNumberExecutabilityTest extends TestClassBase {

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    private final Supplier<? extends PsiElement<Number>> valueSupplier = () -> (PsiElement<Number>) SkriptLoader.get()
            .forceParseElement(random.nextDouble() < 0.9 ? "0"
                    : String.valueOf(random.nextLong(Integer.MIN_VALUE, Integer.MAX_VALUE)));

    private final List<Class<?>> monoFunctions = ImmutableList.of(
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
    );

    private final List<Class<?>> biFunctions = ImmutableList.of(
            PsiAtan2Function.class,
            PsiModuloFunction.class,
            PsiLogarithmFunction.class
    );

    private final List<Class<?>> collectionFunctions = ImmutableList.of(
            PsiMaximumFunction.class,
            PsiMinimumFunction.class,
            PsiProductFunction.class,
            PsiSumFunction.class
    );

    private final Class[] monoConstructorParams = {PsiElement.class};
    private final Class[] biConstructorParams = {PsiElement.class, PsiElement.class};
    private final Class[] collectionConstructorParams = {PsiElement.class};


    @RepeatedTest(1000)
    void test() throws ReflectiveOperationException {
        try {
            PsiElement<Number> result = next(new DirectValueChanceHolder());
            Assertions.assertTrue(result.isPreComputed());
            result.execute(null, Number.class);
            //TODO should NaN, Infinity, -Infinity be valid results?
        } catch (ExecutionException e) {
            System.out.println("Acceptable exception was thrown");
        }

        //TODO force random numbers -> force not pre computable
    }


    private PsiElement<Number> next(DirectValueChanceHolder chanceHolder) throws ReflectiveOperationException {
        if (random.nextDouble() < chanceHolder.getAndIncrease())
            return valueSupplier.get();

        int group = random.nextInt(monoFunctions.size() + biFunctions.size() + collectionFunctions.size());

        if ((group -= monoFunctions.size()) < 0) {
            return getRandom(monoFunctions, monoConstructorParams, next(chanceHolder));
        }

        if (group < biFunctions.size()) {
            return getRandom(biFunctions, biConstructorParams, next(chanceHolder), next(chanceHolder));
        }

        PsiElement<Number>[] parameters = (PsiElement<Number>[]) new PsiElement[random.nextInt(0, 5)];
        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = next(chanceHolder);
        }

        return getRandom(collectionFunctions, collectionConstructorParams, new PsiCollection<>(Arrays.asList(parameters)));
    }

    private PsiElement<Number> getRandom(List<Class<?>> list, Class[] paramClasses, Object... params)
            throws ReflectiveOperationException {
        Class<PsiElement<Number>> clazz = (Class<PsiElement<Number>>) list.get(random.nextInt(list.size()));

        Constructor<? extends PsiElement<Number>> constructor = clazz.getDeclaredConstructor(paramClasses);
        constructor.setAccessible(true);
        return constructor.newInstance(params);
    }


    private static class DirectValueChanceHolder {
        private double chance;

        public double getAndIncrease() {
            double value = chance;
            chance += 0.0025;
            chance *= 1.025;
            return value;
        }
    }
}
