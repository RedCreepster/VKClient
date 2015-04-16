package com.redprojects.utils.config.arguments;

import java.util.HashMap;

public class ProgramArguments extends HashMap<String, Argument> {

    private static ProgramArguments instance = new ProgramArguments();

    public static ProgramArguments getInstance() {
        return instance;
    }
}
