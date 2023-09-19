package dev.boooiil.historia.core.classes.enums.file;

public enum FileKeys {

    /** proficiency.yml */
    PROFICIENCY("proficiency.yml"),
    /** config.yml */
    CONFIG("config.yml"),
    /** plugin.yml */
    PLUGIN("plugin.yml");

    private final String key;

    FileKeys(String key) {

        this.key = key;

    }

    public String getKey() {

        return this.key;

    }

}