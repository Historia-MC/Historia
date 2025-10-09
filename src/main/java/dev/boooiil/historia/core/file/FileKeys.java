package dev.boooiil.historia.core.file;

public enum FileKeys {

    /**
     * proficiency.yml
     */
    PROFICIENCY("proficiency.yml"),
    /**
     * config.yml
     */
    CONFIG("config.yml"),
    /**
     * plugin.yml
     */
    PLUGIN("plugin.yml"),
    /**
     * skills.yml
     */
    SKILLS("skills.yml"),

    TEMPERATURE("temperature.yml"),

    RECIPE("recipe.yml"),

    EXPIRY("expiry.yml"),

    DATE("date.yml");

    private final String key;

    FileKeys(String key) {

        this.key = key;

    }

    public String getKey() {

        return this.key;

    }

}