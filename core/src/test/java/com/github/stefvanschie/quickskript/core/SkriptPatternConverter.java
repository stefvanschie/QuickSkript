package com.github.stefvanschie.quickskript.core;

import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;
import org.junit.jupiter.params.support.FieldContext;

public class SkriptPatternConverter implements ArgumentConverter {

    @Override
    public Object convert(Object source, ParameterContext context) throws ArgumentConversionException {
        return convert(source);
    }

    @Override
    public Object convert(Object source, FieldContext context) throws ArgumentConversionException {
        return convert(source);
    }

    private Object convert(Object source) {
        if (!(source instanceof String input)) {
            throw new IllegalArgumentException("Source should be a String, but is '" + source + "'");
        }

        return SkriptPattern.parse(input);
    }
}
