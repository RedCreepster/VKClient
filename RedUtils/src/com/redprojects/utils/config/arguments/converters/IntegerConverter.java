package com.redprojects.utils.config.arguments.converters;

public class IntegerConverter implements IConverter<Integer> {
    @Override
    public Integer convert(Object value) {
        try {
            return Integer.valueOf(value.toString());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
