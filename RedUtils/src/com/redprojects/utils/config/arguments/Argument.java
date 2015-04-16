package com.redprojects.utils.config.arguments;

import com.redprojects.utils.Log;
import com.redprojects.utils.config.arguments.converters.IConverter;

import java.util.Collections;
import java.util.LinkedList;

@SuppressWarnings("UnusedDeclaration")
public class Argument<T> {

    private final boolean single;
    private final String name;
    private final String shortName;
    private final T defaultValue;
    private final T value;

    public Argument(boolean single, String name, String shortName, String[] args) {
        this(single, name, shortName, args, null);
    }

    public Argument(boolean single, String name, String shortName, String[] args, IConverter<T> converter) {
        this(single, name, shortName, args, converter, null);
    }

    public Argument(boolean single, String name, String shortName, String[] args, IConverter<T> converter, T defaultValue) {
        this.single = single;
        this.name = name;
        this.shortName = shortName;
        this.defaultValue = defaultValue;

        if (args == null || args.length == 0) {
            value = defaultValue;
            Log.console(this, name + ": " + value);
            return;
        }

        LinkedList<String> list = arrayToLinkedList(args);
        if (single)
            //noinspection unchecked
            this.value = (T) (Boolean) list.contains("-" + shortName);
        else {
            int index = list.indexOf("-" + shortName);
            if (list.contains("-" + shortName) && list.size() > index)
                value = converter.convert(list.get(index + 1));
            else
                value = null;
        }
        Log.console(this, name + ": " + value);
    }

    private LinkedList<String> arrayToLinkedList(String[] args) {
        LinkedList<String> list = new LinkedList<>();
        Collections.addAll(list, args);
        return list;
    }

    public boolean isSingle() {
        return single;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public T getValue() {
        return value;
    }

    public boolean isSet() {
        return value != defaultValue && value != null;
    }

    public Argument<T> required(String help) {
        //noinspection ConstantConditions
        if (help == null || (help != null && help.isEmpty()))
            help = "required";
        if (value == defaultValue)
            Log.console(this, name + ": " + help);
        return this;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
