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
    /**
     * armor.yml
     */
    ARMOR("armor.yml"),
    /**
     * weapons.yml
     */
    WEAPONS("weapons.yml"),
    /**
     * tools.yml
     */
    TOOLS("tools.yml"),
    /**
     * customitems.yml
     */
    CUSTOM_ITEMS("items.yml"),
    /**
     * ingots.yml
     */
    INGOTS("ingots.yml"),

    TEMPERATURE("temperature.yml"),

    EXPIRY("expiry.yml");

    private final String key;

    FileKeys(String key) {

        this.key = key;

    }

    public String getKey() {

        return this.key;

    }

}