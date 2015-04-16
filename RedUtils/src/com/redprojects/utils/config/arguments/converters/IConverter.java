package com.redprojects.utils.config.arguments.converters;

public interface IConverter<T> {
    T convert(Object value);
}
