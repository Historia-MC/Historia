package dev.boooiil.historia.core.items.types;

public enum Weights {

    LIGHT("Light"),
    MEDIUM("Medium"),
    HEAVY("Heavy");

    private final String displayName;

    Weights(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String lowercase() {
        return displayName.toLowerCase();
    }

    public static Weights fromString(String str) {
        for (Weights weight : Weights.values()) {
            if (weight.name().equalsIgnoreCase(str)) {
                return weight;
            }
        }
        return Weights.LIGHT;
    }
}
