package dev.boooiil.historia;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    String name;
    Integer health;
    Integer baseFood;
    Integer exhaustion;
    Integer saturation;
    Integer speed;
    List<?> skills;
    List<?> foods;
    List<?> effects;

    //Gets the plugins config file.
    FileConfiguration cfg = Bukkit.getPluginManager().getPlugin("Historia").getConfig();

    //Class constructor
    Config(String className) {

        //If a class name was provided.
        if (className != null) {

            //Assign variables based on that class name.
            name = className;
            health = cfg.getInt(className + ".stats.health");
            baseFood = cfg.getInt(className + ".stats.baseFood");
            exhaustion =  cfg.getInt(className + ".stats.exhaustion");
            saturation = cfg.getInt(className + ".stats.saturation");
            speed = cfg.getInt(className + ".stats.speed");
        }

        foods = cfg.getList("foods");

        effects = cfg.getList("effects");

    }

}