package dev.boooiil.historia.core.database.sql;

public enum DatabaseType {
    MYSQL("mysql"),
    SQLITE("sqlite"),
    UNKNOWN("unknown");

    private final String key;

    DatabaseType(String key) {

        this.key = key;

    }

    public String getKey() {

        return this.key;

    }

    public static DatabaseType fromString(String key) {

        for (DatabaseType type : DatabaseType.values()) {

            if (type.getKey().equalsIgnoreCase(key)) {

                return type;

            }

        }

        return UNKNOWN;

    }

    public String loggingPrefix() {
        return "[" + this.name() + "] ";
    }
}
