package dev.boooiil.historia;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public class Config {

    // Gets the plugins config file.
    private FileConfiguration cfg = Bukkit.getPluginManager().getPlugin("Historia").getConfig();
    private String root;

    public boolean empty = false;

    // Class information
    public String className;
    public Integer health;
    public Integer baseFood;
    public Integer exhaustion;
    public Integer saturation;
    public Integer speed;

    // Weapon / Armor information
    public ItemStack armorItem;
    public ItemStack weaponItem;
    
    public String weaponName;
    public String armorName;
    public Double weaponDamage;
    public Double armorValue;

    public String database;
    public String username;
    public String password;
    public String ip;
    public Integer port;

    public ItemStack oreItem;
    public String smeltInto;
    public Integer oreTime;
    public Integer oreLoss;
    public Integer smeltTimes;

    public ItemStack blockItem;

    // Known Lists
    private List<String> classes = cfg.getStringList("classes.list");
    public List<String> armor = cfg.getStringList("armor.list");
    public List<String> weapons = cfg.getStringList("weapons.list");
    private List<String> ores = cfg.getStringList("items.ores.list");
    private List<String> blocks = cfg.getStringList("items.blocks.list");

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

        else if (query.equals("MySQL")) {
            database = cfg.getString("MySQL.database");
            username = cfg.getString("MySQL.user");
            password = cfg.getString("MySQL.password");
            ip = cfg.getString("MySQL.ip");
            port = cfg.getInt("MySQL.port");
        }

        else if (query.equals("foods")) {

            foods = cfg.getList("foods.list");

            effects = cfg.getList("foods.effects");

        }

        else if (weapons.contains(query)) {

            root = "weapons.";

            weaponName = query;
            weaponDamage = cfg.getDouble(root + query + ".damage");
            weaponItem = cfg.getItemStack(root + query + ".item");

        }

        else if (armor.contains(query)) {

            root = "armor.";

            armorName = query;
            armorValue = cfg.getDouble(root + query + ".armor");
            armorItem = cfg.getItemStack(root + query + ".item");

        } 

        else if (ores.contains(query)) {

            root = "items.ores";

            oreItem = cfg.getItemStack(root + query + ".item");

            smeltInto = cfg.getString(root + query + ".smelt_into");
            oreTime = cfg.getInt(root + query + ".time");
            oreLoss = cfg.getInt(root + query + ".loss");
            smeltTimes = cfg.getInt(root + query + ".smelt_times");

        }
        
        else if (blocks.contains(query)) {

            root = "items.blocks";

            blockItem = cfg.getItemStack(root + query + ".item");

        }

        else empty = true;
        
        //CONFIG DEBUGGING

        System.out.println("Query: " + query);

        System.out.println("Class Name: " + className);
        System.out.println("Class Health: " + health);
        System.out.println("Class Base Food: " + baseFood);
        System.out.println("Class Exhaustion: " + exhaustion);
        System.out.println("Class Saturation: " + saturation);
        System.out.println("Class Speed: " + speed);
        
        System.out.println("Weapon Name: " + weaponName);
        System.out.println("Weapon Damage: " + weaponDamage);
        
        System.out.println("Armor Name: " + armorName);
        System.out.println("Armor Value: " + armorValue);

        System.out.println("SQL Username: " + username);
        System.out.println("SQL Database: " + database);
        System.out.println("SQL IP: " + ip);
        System.out.println("SQL Port: " + port);

        System.out.println("Classes List: " + classes);
        System.out.println("Weapons List: " + weapons);
        System.out.println("Armors List: " + armor);

        System.out.println("Blocks List: " + blocks);
        System.out.println("Ores List: " + ores);

    }

}