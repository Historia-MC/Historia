package dev.boooiil.historia;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    // Gets the plugins config file.
    private FileConfiguration cfg = Bukkit.getPluginManager().getPlugin("Historia").getConfig();
    private String root;

    public boolean empty = false;

    // Class information
    public String className = null;
    public Integer health = 0;
    public Integer baseFood = 0;
    public Integer exhaustion = 0;
    public Integer saturation = 0;
    public Integer speed = 0;

    // Weapon / Armor information
    public String weaponName = null;
    public String armorName = null;
    public Double weaponDamage = 0.0;
    public Double armorValue = 0.0;

    // Known Lists
    private List<String> classes = cfg.getStringList("classes.list");
    private List<String> armor = cfg.getStringList("armor.list");
    private List<String> weapons = cfg.getStringList("weapons.list");

    // Unknown Lists
    private List<?> skills;
    private List<?> foods;
    private List<?> effects;

    // Class constructor
    public Config(String query) {

        // If a class name was provided.
        if (classes.contains(query)) {

            root = "classes.";

            // Assign variables based on that class name.
            className = query;
            health = cfg.getInt(root + className + ".stats.health");
            baseFood = cfg.getInt(root + className + ".stats.baseFood");
            exhaustion = cfg.getInt(root + className + ".stats.exhaustion");
            saturation = cfg.getInt(root + className + ".stats.saturation");
            speed = cfg.getInt(root + className + ".stats.speed");
        }

        else if (query.equals("foods")) {

            foods = cfg.getList("foods.list");

            effects = cfg.getList("foods.effects");

        }

        else if (weapons.contains(query)) {

            root = "weapons.";

            weaponName = query;
            weaponDamage = cfg.getDouble(root + query + ".damage");

        }

        else if (armor.contains(query)) {

            root = "armor.";

            armorName = query;
            armorValue = cfg.getDouble(root + query + ".armor");

        } else empty = true;

        

    }

}