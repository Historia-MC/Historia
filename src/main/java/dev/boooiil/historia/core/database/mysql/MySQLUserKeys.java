package dev.boooiil.historia.core.database.mysql;

public enum MySQLUserKeys {

    UUID("uuid"),
    USERNAME("username"),
    CLASS("class"),
    LEVEL("level"),
    EXPERIENCE("experience"),
    LOGIN("login"),
    LOGOUT("logout"),
    PLAYTIME("playtime");

    private final String key;

    MySQLUserKeys(String key) {

        this.key = key;

    }

    public String getKey() {

        return this.key;

    }

}