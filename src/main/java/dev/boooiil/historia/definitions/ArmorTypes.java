package dev.boooiil.historia.definitions;

/**
 * Enum of all possible weight classes of armors.
 */
public enum ArmorTypes { 
    
    HEAVY("Heavy"), 
    MEDIUM("Medium"), 
    LIGHT("Light"); 
    
    private final String type;

    /**
     * Private constructor.
     * @param type - Type of armor
     */
    private ArmorTypes(String type) {

        this.type = type;

    }

    /**
     * Get the resulting weight class.
     * @return Type of armor.
     */
    public String getType() {

        return this.type;

    }

}
