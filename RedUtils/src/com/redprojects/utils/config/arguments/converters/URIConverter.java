package com.redprojects.utils.config.arguments.converters;

import java.io.File;
import java.net.URI;

public class URIConverter implements IConverter<URI> {
    @Override
    public URI convert(Object value) {
        return new File(value.toString()).toURI();
    }
}
