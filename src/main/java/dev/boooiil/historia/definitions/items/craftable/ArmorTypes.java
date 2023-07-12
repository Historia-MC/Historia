package dev.boooiil.historia.definitions.items.craftable;

/**
 * Enum of all possible weight classes of armors.
 */
public enum ArmorTypes { 
    
    //Type heavy
    HEAVY("Heavy"), 
    //Type medium
    MEDIUM("Medium"), 
    //Type light
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
