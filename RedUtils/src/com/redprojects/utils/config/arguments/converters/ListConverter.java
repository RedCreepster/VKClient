package com.redprojects.utils.config.arguments.converters;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ListConverter implements IConverter<List<String>> {
    @Override
    public List<String> convert(Object value) {
        if (value instanceof String)
            return Collections.unmodifiableList(Arrays.asList(new StringConverter().convert(value).split(",")));
        return Collections.emptyList();
    }
}
