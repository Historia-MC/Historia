package dev.boooiil.historia.core.items.types;

public enum Qualities {

    POOR("Poor"),
    COMMON("Common"),
    PERFECT("Perfect");

    private final String displayName;

    Qualities(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String lowercase() {
        return displayName.toLowerCase();
    }

    public static Qualities fromString(String str) {
        for (Qualities quality : Qualities.values()) {
            if (quality.name().equalsIgnoreCase(str)) {
                return quality;
            }
        }
        return Qualities.POOR;
    }
}
