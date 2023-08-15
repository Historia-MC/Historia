package dev.boooiil.historia.core.classes.enums;

/**
 * This class holds enums for the MySQL keys.
 */
public class MySQLMaps {

    /**
     * This enum represents the keys for the User table.
     */
    public static enum HistoriaUserKeys {

        UUID("uuid"),
        USERNAME("username"),
        CLASS("class"),
        LEVEL("level"),
        EXPERIENCE("experience"),
        LOGIN("login"),
        LOGOUT("logout"),
        PLAYTIME("playtime");

        private final String key;

        private HistoriaUserKeys(String key) {

            this.key = key;

        }

        public String getKey() {

            return this.key;

        }

    }

    
    
}
