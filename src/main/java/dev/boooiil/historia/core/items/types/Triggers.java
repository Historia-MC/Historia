package dev.boooiil.historia.core.items.types;

import dev.boooiil.historia.core.util.CoreLogger;

public enum Triggers {
    UNKNOWN(0), // Default ID for unknown triggers
    RIGHT_CLICK(1), // ADDED LISTENER
    LEFT_CLICK(2), // ADDED LISTENER
    RIGHT_CLICK_BLOCK(3), // ADDED LISTENER
    LEFT_CLICK_BLOCK(4), // ADDED LISTENER
    SNEAK_RIGHT_CLICK(5), // ADDED LISTENER
    SNEAK_LEFT_CLICK(6), // ADDED LISTENER
    SNEAK_RIGHT_CLICK_BLOCK(7), // ADDED LISTENER
    SNEAK_LEFT_CLICK_BLOCK(8), // ADDED LISTENER
    DAMAGE_ENTITY(9), // ADDED LISTENER
    INTERACT_ENTITY(10), // ADDED LISTENER
    OPEN_INVENTORY(11), // ADDED LISTENER
    CLOSE_INVENTORY(12), // ADDED LISTENER
    SWAP_TO_MAINHAND(13), // ADDED LISTENER
    SWAP_TO_OFFHAND(14), // ADDED LISTENER
    PLACE(15), // ADDED LISTENER
    PICKUP(16), // ADDED LISTENER
    DROP(17), // ADDED LISTENER
    EAT(18), // ADDED LISTENER
    DRINK(19), // ADDED LISTENER
    JUMP(20), // ADDED LISTENER
    SPRINT(21), // ADDED LISTENER
    SWIM(22), // ADDED LISTENER
    THROW(23), // ADDED LISTENER
    CROUCH(24), // ADDED LISTENER
    UNCROUCH(25); // ADDED LISTENER

    private final int id;
    private final String lowercase;

    Triggers(int id) {
        this.id = id;
        this.lowercase = this.name().toLowerCase();
    }

    public int getId() {
        return id;
    }

    public String getLowercase() {
        return lowercase;
    }

    @Override
    public String toString() {
        return lowercase;
    }

    public static Triggers fromId(int id) {
        for (Triggers trigger : values()) {
            if (trigger.id == id) {
                return trigger;
            }
        }

        CoreLogger.errorToConsole("Tried to get trigger with id: " + id, "but it did not exist.");
        return UNKNOWN;
    }

    public static Triggers fromString(String strTrigger) {
        for (Triggers trigger : values()) {
            if (trigger.lowercase.equals(strTrigger)) {
                return trigger;
            }
        }

        CoreLogger.errorToConsole("Tried to get trigger with string: " + strTrigger, "but it did not exist.");
        return UNKNOWN;
    }
}
