package dev.boooiil.historia.core.classes.enums.file;

public enum FileKeys {

    /** armor.yml */
    ARMOR("armor.yml"),
    /** proficiency.yml */
    PROFICIENCY("proficiency.yml"),
    /** config.yml */
    CONFIG("config.yml"),
    /** plugin.yml */
    PLUGIN("plugin.yml"),
    /** weapons.yml */
    WEAPONS("weapons.yml"),
    /** tools.yml */
    TOOLS("tools.yml");

    private final String key;

    FileKeys(String key) {

        this.key = key;

    }

    public String getKey() {

        return this.key;

    }

}