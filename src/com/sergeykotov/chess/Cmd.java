package com.sergeykotov.chess;

import java.util.Arrays;

public enum Cmd {
    CALC("n m k figure f - find n x m chess board cell combinations to allocate k non-attacking figures f",
            "\\d+\\s+\\d+\\s+\\d+\\s+[a-z]+"),
    EXPORT("export - create a txt-file in the current folder with last calculation result", "export"),
    EXIT("exit - close the program", "exit");

    private final String description;
    private final String regexp;

    private static final String commands = Arrays.stream(Cmd.values())
            .map(Cmd::getDescription)
            .reduce((c1, c2) -> c1 + System.lineSeparator() + c2)
            .orElse("");

    Cmd(String description, String regexp) {
        this.description = description;
        this.regexp = regexp;
    }

    public String getDescription() {
        return description;
    }

    public String getRegexp() {
        return regexp;
    }

    public static String getCommands() {
        return commands;
    }
}