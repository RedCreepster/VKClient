package com.redprojects.utils.config.arguments.converters;

public class StringConverter implements IConverter<String> {
    @Override
    public String convert(Object value) {
        return String.valueOf(value);
    }
}
