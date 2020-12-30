package dev.boooiil.historia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Config {

    //Get the current configuration file for the plugin.
    private static FileConfiguration configuration = Bukkit.getPluginManager().getPlugin("Historia").getConfig();

    //Load lists.
    static final Set<String> armorSet = configuration.getConfigurationSection("armor").getKeys(false);
    static final Set<String> blockSet = configuration.getConfigurationSection("items.blocks").getKeys(false);
    static final Set<String> classSet = configuration.getConfigurationSection("classes").getKeys(false);
    static final Set<String> foodSet = configuration.getConfigurationSection("foods").getKeys(false);
    static final Set<String> oreSet = configuration.getConfigurationSection("items.ores").getKeys(false);
    static final Set<String> weaponSet = configuration.getConfigurationSection("weapons").getKeys(false);

    private static Map<String, Object> armorMap = new HashMap<>();
    private static Map<String, Double> classMap = new HashMap<>();
    private static Map<String, Object> foodMap = new HashMap<>();
    private static Map<String, String> mySQLMap = new HashMap<>();
    private static Map<String, Object> oreMap = new HashMap<>();
    private static Map<String, Object> weaponMap = new HashMap<>();


    /**
     * Get a set (unordered list) of all armors described in the config.yml.
     * 
     * 
     * @return Set of all armors described in the config.yml.
     * 
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/util/Set.html">Set</a>
     */
    public static Set<String> getArmorSet() {

        return armorSet;

    }

    /**
     * Get a set (unordered list) of all blocks described in the config.yml.
     * 
     * 
     * @return Set of all blocks described in the config.yml.
     * 
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/util/Set.html">Set</a>
     */

    public static Set<String> getBlockSet() {

        return blockSet;
        
    }

    /**
     * Get a set (unordered list) of all classes described in the config.yml.
     * 
     * 
     * @return Set of all classes described in the config.yml.
     * 
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/util/Set.html">Set</a>
     */

    public static Set<String> getClassSet() {

        return classSet;
        
    }

    /**
     * Get a set (unordered list) of all foods described in the config.yml.
     * 
     * 
     * @return Set of all foods described in the config.yml.
     * 
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/util/Set.html">Set</a>
     */

    public static Set<String> getFoodSet() {

        return foodSet;
        
    }

    /**
     * Get a set (unordered list) of all ores described in the config.yml.
     * 
     * 
     * @return Set of all ores described in the config.yml.
     * 
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/util/Set.html">Set</a>
     */

    public static Set<String> getOreSet() {

        return oreSet;
        
    }

    /**
     * Get a set (unordered list) of all weapons described in the config.yml.
     * 
     * 
     * @return Set of all weapons described in the config.yml.
     * 
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/util/Set.html">Set</a>
     */

    public static Set<String> getWeaponSet() {

        return weaponSet;
        
    }

    /**
     * Whether or not the class provided is in the config.yml.
     * 
     * @param className - Name of the class to check.
     * @return Whether or not the class provided is in the config.yml.
     */

    public static boolean validClass(String className) {

        return classSet.contains(className);

    }

    /**
     * Whether or not the food provided is in the config.yml.
     * 
     * @param foodName - Name of the food to check.
     * @return Whether or not the food provided is in the config.yml.
     */

    public static boolean validFood(String foodName) {

        return foodSet.contains(foodName);

    }

    /**
     * Whether or not the weapon provided is in the config.yml.
     * 
     * @param weaponName - Name of the weapon to check.
     * @return Whether or not the weapon provided is in the config.yml.
     */

    public static boolean validWeapon(String weaponName) {

        return weaponSet.contains(weaponName);

    }

    /**
     * Whether or not the armor provided is in the config.yml.
     * 
     * @param armorName - Name of the armor to check.
     * @return Whether or not the armor  provided is in the config.yml.
     */

    public static boolean validArmor(String armorName) {

        return armorSet.contains(armorName);

    }

    /**
     * Whether or not the ore provided is in the config.yml.
     * 
     * @param oreName - Name of the ore to check.
     * @return Whether or not the ore provided is in the config.yml.
     */

    public static boolean validOre(String oreName) {

        return oreSet.contains(oreName);

    }

    /**
     * Whether or not the block provided is in the config.yml.
     * 
     * @param blockName - Name of the block to check.
     * @return Whether or not the block provided is in the config.yml.
     */

    public static boolean validBlock(String blockName) {

        return blockSet.contains(blockName);

    }

    /**
     * Class information provided in a Map.
     *  
     * @param className - Name of the class to get.
     * @return 
     * <p> <"HEALTH", {@link java.lang.Double Double}> 
     * <p> <"MAX_HEALTH", {@link java.lang.Double Double}> 
     * <p> <"SPEED", {@link java.lang.Double Double}> 
     * <p> <"EVASION", {@link java.lang.Double Double}> 
     * <p> <"WEAPON_PROFICIENCY", {@link java.lang.Double Double}> 
     * <p> <"BOW_PROFICIENCY", {@link java.lang.Double Double}> 
     * <p> <"CROSSBOW_PROFICIENCY", {@link java.lang.Double Double}> 
     * 
     * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/Map.html">Map</a>
     */

    public static Map<String, Double> getClassInfo(String className) {

        if (validClass(className)) {

            String root = "classes." + className + ".stats";

            classMap.put("HEALTH", configuration.getDouble(root + ".baseHealth"));
            classMap.put("MAX_HEALTH", configuration.getDouble(root + ".maxHealth"));
            classMap.put("SPEED", configuration.getDouble(root + ".baseSpeed"));
            classMap.put("EVASION", configuration.getDouble(root + ".baseEvasion"));
            classMap.put("WEAPON_PROFICIENCY", configuration.getDouble(root + ".baseWeaponProficiency"));
            classMap.put("BOW_PROFICIENCY", configuration.getDouble(root + ".baseBowProficiency"));
            classMap.put("CROSSBOW_PROFICIENCY", configuration.getDouble(root + ".baseCrossbowProficiency"));

        } else {

            classMap.put("HEALTH", configuration.getDouble("classes.None.stats.health"));
            classMap.put("MAX_HEALTH", configuration.getDouble("classes.None.stats.health"));
            classMap.put("SPEED", configuration.getDouble("classes.None.stats.speed"));
            classMap.put("EVASION", configuration.getDouble("classes.None.stats.baseEvasion"));
            classMap.put("WEAPON_PROFICIENCY", configuration.getDouble("classes.None.stats.baseWeaponProficiency"));
            classMap.put("BOW_PROFICIENCY", configuration.getDouble("classes.None.stats.baseBowProficiency"));
            classMap.put("CROSSBOW_PROFICIENCY", configuration.getDouble("classes.None.stats.baseCrossbowProficiency"));

        }

        return classMap;

    }

    /**
     * Food information provided in a Map.
     *  
     * @param foodName - Name of the class to get.
     * @return 
     * <p> <"EXPIRY", {@link java.lang.Integer Integer}> 
     * <p> <"POISON", {@link org.bukkit.potion.PotionEffect PotionEffect}> 
     * <p> <"HUNGER", {@link org.bukkit.potion.PotionEffect PotionEffect}>
     * 
     * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/Map.html">Map</a>
     */

    public static Map<String, Object> getFoodInfo(String foodName) {

        if (validFood(foodName)) {

            String root = "foods." + foodName;

            foodMap.put("EXPIRY", configuration.getInt(root));
            foodMap.put("POISON", new PotionEffect(PotionEffectType.POISON, 100, 1));
            foodMap.put("HUNGER", new PotionEffect(PotionEffectType.HUNGER, 300, 1));

        } else {

            foodMap.put("EXPIRY", configuration.getInt("foods.not-listed"));
            foodMap.put("POISON", new PotionEffect(PotionEffectType.POISON, 100, 1));
            foodMap.put("HUNGER", new PotionEffect(PotionEffectType.HUNGER, 300, 1));

        }

        return foodMap;

    }

    /**
     * Weapon information provided in a Map.
     *  
     * @param weaponName - Name of the weapon to get.
     * @return 
     * <p> <"DAMAGE", {@link java.lang.Double Double}> 
     * <p> <"KNOCKBACK", {@link java.lang.Double Double}> 
     * <p> <"SWEEPING", {@link java.lang.Double Double}> 
     * <p> <"ITEM", {@link org.bukkit.entity.Item Item}> 
     * <p> <"SHAPE", {@link java.util.List List}> 
     * <p> <"RECIPE", {@link java.util.List List}>
     * 
     * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/Map.html">Map</a>
     */

    public static Map<String, Object> getWeaponInfo(String weaponName) {

        if (validWeapon(weaponName)) {

            String root = "weapons." + weaponName;

            weaponMap.put("DAMAGE", configuration.getDoubleList(root + ".damage"));
            weaponMap.put("KNOCKBACK", configuration.getDoubleList(root + ".knockback"));
            weaponMap.put("SWEEPING", configuration.getDoubleList(root + ".sweeping"));
            weaponMap.put("DURABILITY", configuration.getIntegerList(root + ".durability"));
            weaponMap.put("ITEM", configuration.getItemStack(root + ".item"));
            weaponMap.put("TYPE", configuration.getString(root + ".type"));
            weaponMap.put("SHAPE", configuration.getStringList(root + ".recipe-shape"));
            weaponMap.put("RECIPE", configuration.getStringList(root + ".recipe-items"));

        } else {

            Double[] blank_double = {0.0, 0.0};
            Integer[] blank_integer = {0, 0};

            weaponMap.put("DAMAGE", blank_double);
            weaponMap.put("KNOCKBACK", blank_double);
            weaponMap.put("SWEEPING", blank_double);
            weaponMap.put("DURABILITY", blank_integer);
            weaponMap.put("ITEM", new ItemStack(Material.AIR));
            weaponMap.put("TYPE", "Light");
            weaponMap.put("SHAPE", Arrays.asList(""));
            weaponMap.put("RECIPE", Arrays.asList(""));

        }
        
        return weaponMap;

    }

    /**
     * Armor information provided in a Map.
     *  
     * @param armorName - Name of the armor to get.
     * @return 
     * <p> <"ITEM", {@link org.bukkit.entity.Item Item}> 
     * <p> <"ARMOR", {@link java.lang.Double Double}> 
     * <p> <"SHAPE", {@link java.util.List List}> 
     * <p> <"RECIPE", {@link java.util.List List}>
     * 
     * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/Map.html">Map</a>
     */

    public static Map<String, Object> getArmorInfo(String armorName) {

        if (validArmor(armorName)) {

            String root = "armor." + armorName;

            armorMap.put("ITEM", configuration.getItemStack(root + ".item"));
            armorMap.put("ARMOR", configuration.getDouble(root + ".armor"));
            armorMap.put("TYPE", configuration.getString(root + ".type"));
            armorMap.put("SHAPE", configuration.getStringList(root + ".recipe-shape"));
            armorMap.put("RECIPE", configuration.getStringList(root + ".recipe-items"));

        } else {

            armorMap.put("ITEM", new ItemStack(Material.AIR));
            armorMap.put("ARMOR", 0);
            armorMap.put("TYPE", "Heavy");
            armorMap.put("SHAPE", Arrays.asList(""));
            armorMap.put("RECIPE", Arrays.asList(""));

        }

        return armorMap;

    }
    
    /**
     * Ore information provided in a Map.
     *  
     * @param oreName - Name of the ore to get.
     * @return 
     * <p> <"PROGRESSION", {@link java.lang.String String}> 
     * <p> <"ITEM", {@link org.bukkit.entity.Item Item}> 
     * <p> <"SMELT_TIME", {@link java.lang.Integer Integer}> 
     * <p> <"SMELT_AMOUNT", {@link java.lang.Integer Integer}> 
     * <p> <"LOSS", {@link java.lang.Integer Integer}>
     * 
     * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/Map.html">Map</a>
     */

    public static Map<String, Object> getOreInfo(String oreName) {

        if (validOre(oreName)) {

            String root = "items.ores." + oreName;

            oreMap.put("PROGRESSION", configuration.getString(root + ".smelt_into"));
            oreMap.put("ITEM", configuration.getItemStack(root + ".item"));
            oreMap.put("SMELT_TIME", configuration.getInt(root + ".time"));
            oreMap.put("SMELT_AMOUNT", configuration.getInt(root + ".smelt_times"));
            oreMap.put("LOSS", configuration.getInt(root + ".loss"));

        } else {

            oreMap.put("PROGRESSION", null);
            oreMap.put("ITEM", new ItemStack(Material.AIR));
            oreMap.put("SMELT_TIME", 0);
            oreMap.put("SMELT_AMOUNT", 0);
            oreMap.put("LOSS", 1);

        }

        return oreMap;

    }

    /**
     * List of all weapon types the class can use.
     *  
     * @param className - Name of the class.
     * @return List of weapon types the class can use.
     * 
     * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/List.html">List</a>
     */

    public static List<String> getClassWeaponTypes(String className) {

        if (validClass(className)) {

            return configuration.getStringList("classes." + className + ".weaponProficiency");

        } else return Arrays.asList("NOT A VALID CLASS");

    }

    /**
     * List of all weapons the class can use based on the allowed type.
     *  
     * @param className - Name of the class.
     * @return List of weapons the class can use.
     * 
     * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/List.html">List</a>
     */

    public static List<String> getUsableWeapons(String className) {

        if (validClass(className)) {

            List<String> classProficient = getClassWeaponTypes(className);
        
            List<String> found = new ArrayList<>();

            for (String weapon : weaponSet) {

                String type = configuration.getString("weapons." + weapon + ".type");

                if (classProficient.contains(type)) found.add(weapon);

            }

            return found;

        } else return Arrays.asList("NOT A VALID CLASS");

    }

    /**
     * List of all armor types the class can use.
     *  
     * @param className - Name of the class.
     * @return List of armor types the class can use.
     * 
     * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/List.html">List</a>
     */

    public static List<String> getClassArmorTypes(String className) {

        if (validClass(className)) {

            return configuration.getStringList("classes." + className + ".armorProficiency");

        } else return Arrays.asList("NOT A VALID CLASS");

    }

    /**
     * List of all armor  the class can use based on the allowed type.
     *  
     * @param className - Name of the class.
     * @return List of armor the class can use.
     * 
     * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/List.html">List</a>
     */

    public static List<Material> getAllAxes() {

        List<Material> found = new ArrayList<>();

        for (Material material : Material.values()) {

            if (material.toString().contains("AXE") && !material.toString().contains("PICKAXE")) {

                found.add(material);

            }

        }
        
        return found;

    }

    public static List<Material> getAllPickaxes() {

        List<Material> found = new ArrayList<>();

        for (Material material : Material.values()) {

            if (material.toString().contains("PICKAXE")) {

                found.add(material);

            }

        }
        
        return found;

    }

    public static List<Material> getAllShovels() {

        List<Material> found = new ArrayList<>();

        for (Material material : Material.values()) {

            if (material.toString().contains("SHOVEL")) {

                found.add(material);

            }

        }
        
        return found;

    }

    public static List<Material> getAllHoes() {

        List<Material> found = new ArrayList<>();

        for (Material material : Material.values()) {

            if (material.toString().contains("HOE")) {

                found.add(material);

            }

        }
        
        return found;

    }

    public static List<Material> getAllBoots() {

        List<Material> found = new ArrayList<>();

        for (Material material : Material.values()) {

            if (material.toString().contains("BOOTS")) {

                found.add(material);

            }

        }
        
        return found;

    }

    public static List<String> getUsableArmor(String className) {

        if (validClass(className)) {

            List<String> classProficient = getClassArmorTypes(className);
        
            List<String> found = new ArrayList<>();

            for (String armor : armorSet) {

                String type = configuration.getString("armor." + armor + ".type");

                if (classProficient.contains(type)) found.add(armor);

            }

            return found;

        } else return Arrays.asList("NOT A VALID CLASS");

    }

    public static Map<String, String> getMySQLInfo() {

        String root = "MySQL";

        mySQLMap.put("USER", configuration.getString(root + ".user"));
        mySQLMap.put("PASSWORD", configuration.getString(root + ".password"));
        mySQLMap.put("DATABASE", configuration.getString(root + ".database"));
        mySQLMap.put("IP", configuration.getString(root + ".ip"));
        mySQLMap.put("PORT", configuration.getString(root + ".port"));

        return mySQLMap;

    }

    public ItemStack getBlockInfo(String blockName) {

        if (validBlock(blockName)) {

            String root = "items.blocks." + blockName;

            return configuration.getItemStack(root + ".item");

        } else {

            return new ItemStack(Material.AIR);

        }
    }
}