package dev.boooiil.historia.core.player.culture;

/**
 * Enum storing all possible cultures.
 * 
 * TODO: make this into a configuration class.
 */
public enum Cultures {

    // Western European Cultures
    FRANK("Frank", "Franks", "Frankish"),
    NORMAN("Norman", "Normans", "Norman"),
    ANGLO_SAXON("Anglo-Saxon", "Anglo-Saxons", "Anglo-Saxon"),
    GERMAN("German", "Germans", "German"),
    IBERIAN_CHRISTIAN("Iberian Christian", "Iberian Christians", "Iberian Christian"),
    ITALIAN("Italian", "Italians", "Italian"),
    CELT("Celt", "Celts", "Celtic"),

    // Eastern European & Byzantine Cultures
    BYZANTINE("Byzantine", "Byzantines", "Byzantine"),
    SLAV("Slav", "Slavs", "Slavic"),
    HUNGARIAN("Hungarian", "Hungarians", "Hungarian"),

    // Islamic Cultures
    MOOR("Moor", "Moors", "Moorish"),
    SELJUK_TURK("Seljuk Turk", "Seljuk Turks", "Seljuk"),
    PERSIAN("Persian", "Persians", "Persian"),
    ARAB("Arab", "Arabs", "Arab"),

    // Central Asian & Nomadic Cultures
    MONGOL("Mongol", "Mongols", "Mongolic"),
    TURKIC("Turkic", "Turkic Peoples", "Turkic"),
    TIBETAN("Tibetan", "Tibetans", "Tibetan"),

    // South Asian Cultures
    HINDU("Hindu", "Hindus", "Hindu"),
    MUSLIM_SULTANATE("Muslim Sultanate", "Muslim Sultanates", "Sultanate"),

    // East Asian Cultures
    CHINESE("Chinese", "Chinese", "Chinese"),
    KOREAN("Korean", "Koreans", "Korean"),
    JAPANESE("Japanese", "Japanese", "Japanese"),

    NONE("Nomad", "Nomads", "Nomadic");

    private final String noun;
    private final String plural;
    private final String adjective;

    Cultures(String noun, String plural, String adjective) {
        this.noun = noun;
        this.plural = plural;
        this.adjective = adjective;
    }

    public String getNoun() {
        return noun;
    }

    public String getPlural() {
        return plural;
    }

    public String getAdjective() {
        return adjective;
    }

    public static Cultures getCulture(String culture) {
        for (Cultures c : values()) {
            if (c.name().equalsIgnoreCase(culture)) {
                return c;
            }
        }
        return NONE;
    }

}
