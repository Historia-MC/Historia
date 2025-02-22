package dev.boooiil.historia.core.player.culture;

import java.util.Set;

/**
 * Enum storing all possibe regions and the cultures within them.
 */
public enum Regions {

    WESTERN_EUROPE(
            Cultures.FRANK,
            Cultures.NORMAN,
            Cultures.ANGLO_SAXON,
            Cultures.GERMAN,
            Cultures.IBERIAN_CHRISTIAN,
            Cultures.ITALIAN,
            Cultures.CELT),

    EASTERN_EUROPE(
            Cultures.BYZANTINE,
            Cultures.SLAV,
            Cultures.HUNGARIAN),

    ISLAMIC(
            Cultures.MOOR,
            Cultures.SELJUK_TURK,
            Cultures.PERSIAN,
            Cultures.ARAB),

    CENTRAL_ASIAN(
            Cultures.MONGOL,
            Cultures.TURKIC,
            Cultures.TIBETAN),

    SOUTH_ASIAN(
            Cultures.HINDU,
            Cultures.MUSLIM_SULTANATE),

    EAST_ASIAN(
            Cultures.CHINESE,
            Cultures.KOREAN,
            Cultures.JAPANESE),

    NONE(
            Cultures.NONE);

    private final Set<Cultures> cultures;

    Regions(Cultures... cultures) {
        this.cultures = Set.of(cultures);
    }

    public Set<Cultures> getCultures() {
        return cultures;
    }

    public static Regions getRegion(Cultures culture) {
        for (Regions region : Regions.values()) {
            if (region.getCultures().contains(culture)) {
                return region;
            }
        }
        return NONE;
    }

    public static Regions getRegion(String region) {
        for (Regions reg : Regions.values()) {
            if (reg.name().equalsIgnoreCase(region)) {
                return reg;
            }
        }
        return NONE;
    }
}
