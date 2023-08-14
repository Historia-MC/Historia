package dev.boooiil.historia.classes.enums;

public class FileMap {
    /**
     * This enum represents the keys for the various resource files used in the
     * plugin.
     */
    public static enum ResourceKeys {

        /** armor.yml */
        ARMOR("armor.yml"),
        /** proficiency.yml */
        PROFICIENCY("proficiency.yml"),
        /** config.yml */
        CONFIG("config.yml"),
        /** expiry.yml */
        EXPIRY("expiry.yml"),
        /** plugin.yml */
        PLUGIN("plugin.yml"),
        /** weapons.yml */
        WEAPONS("weapons.yml"),
        /** tools.yml */
        TOOLS("tools.yml");

        private String key;

        private ResourceKeys(String key) {

            this.key = key;

        }

        public String getKey() {

            return this.key;

        }

    }

}
