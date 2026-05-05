package com.dylan.logintelligence.utils;

public enum LogLevel {
    DEBUG("DEBUG"),
    INFO("INFO"),
    WARN("WARN"),
    ERROR  ("ERROR");

    private String level;

    LogLevel(String debug) {
        this.level = debug;
    }

    private String getLevel() {
        return this.level;
    }

    private static LogLevel fromString(String level) {
        for (LogLevel logLevel : LogLevel.values()) {
            if (logLevel.getLevel().equalsIgnoreCase(level)) {
                return logLevel;
            }
        }
        throw new IllegalArgumentException("Unknown log level: " + level);
    }
}
