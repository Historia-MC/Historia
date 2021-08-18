package dev.boooiil.historia;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@Deprecated
public class Config {

    //Get the current configuration file for the plugin.
    private static FileConfiguration miscConfig = Bukkit.getPluginManager().getPlugin("Historia").getConfig();
    private static FileConfiguration armorConfig = getConfig("armor.yml");
    private static FileConfiguration classConfig = getConfig("classes.yml");
    private static FileConfiguration expiryConfig = getConfig("expiry.yml");
    private static FileConfiguration ingotConfig = getConfig("ingots.yml");
    private static FileConfiguration oreConfig = getConfig("ores.yml");
    private static FileConfiguration weaponConfig = getConfig("weapons.yml");

    //Load lists.
    static final Set<String> armorSet = armorConfig.getKeys(false);
    static final Set<String> oreSet = oreConfig.getKeys(false);
    static final Set<String> classSet = classConfig.getKeys(false);
    static final Set<String> foodSet  = expiryConfig.getKeys(false);
    static final Set<String> ingotSet  = ingotConfig.getKeys(false);
    static final Set<String> weaponSet  = weaponConfig.getKeys(false);

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
    @Deprecated //
    public static Set<String> getArmorSet() {

        return armorSet;

    }

    /**
     * Get a set (unordered list) of all blocks described in ingots.yml.
     * 
     * 
     * @return Set of all ingots described in ingots.yml.
     * 
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/util/Set.html">Set</a>
     */
    @Deprecated // IngotConfig.getIngotSet()
    public static Set<String> getIngot() {

        return ingotSet;
        
    }

    /**
     * Get a set (unordered list) of all classes described in classes.yml.
     * 
     * 
     * @return Set of all classes described in classes.yml.
     * 
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/util/Set.html">Set</a>
     */
    @Deprecated //
    public static Set<String> getClassSet() {

        return classSet;
        
    }

    /**
     * Get a set (unordered list) of all foods described in expiry.yml.
     * 
     * 
     * @return Set of all foods described in expiry.yml.
     * 
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/util/Set.html">Set</a>
     */
    @Deprecated //
    public static Set<String> getFoodSet() {

        return foodSet;
        
    }

    /**
     * Get a set (unordered list) of all ores described in ores.yml.
     * 
     * 
     * @return Set of all ores described in ores.yml.
     * 
     * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/util/Set.html">Set</a>
     */
    @Deprecated // OreConfig.getOreSet()
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
    @Deprecated // WeaponConfig.getWeaponSet()
    public static Set<String> getWeaponSet() {

        return weaponSet;
        
    }

    /**
     * Whether or not the class provided is in the config.yml.
     * 
     * @param className - Name of the class to check.
     * @return Whether or not the class provided is in the config.yml.
     */
    @Deprecated // 
    public static boolean validClass(String className) {

        return classSet.contains(className);

    }

    /**
     * Whether or not the food provided is in the config.yml.
     * 
     * @param foodName - Name of the food to check.
     * @return Whether or not the food provided is in the config.yml.
     */
    @Deprecated // 
    public static boolean validFood(String foodName) {

        return foodSet.contains(foodName);

    }

    /**
     * Whether or not the weapon provided is in the config.yml.
     * 
     * @param weaponName - Name of the weapon to check.
     * @return Whether or not the weapon provided is in the config.yml.
     */
    @Deprecated // WeaponConfig.validWeapon()
    public static boolean validWeapon(String weaponName) {

        return weaponSet.contains(weaponName);

    }

    /**
     * Whether or not the armor provided is in the config.yml.
     * 
     * @param armorName - Name of the armor to check.
     * @return Whether or not the armor  provided is in the config.yml.
     */
    @Deprecated //
    public static boolean validArmor(String armorName) {

        return armorSet.contains(armorName);

    }

    /**
     * Whether or not the ore provided is in the config.yml.
     * 
     * @param oreName - Name of the ore to check.
     * @return Whether or not the ore provided is in the config.yml.
     */
    @Deprecated // OreConfig.validOre()
    public static boolean validOre(String oreName) {

        return oreSet.contains(oreName);

    }

    /**
     * Whether or not the ingot provided is in ingots.yml.
     * 
     * @param blockName - Name of the ingot to check.
     * @return Whether or not the ingot provided is in ingots.yml.
     */
    @Deprecated // IngotConfig.validIngot()
    public static boolean validIngot(String ingotName) {

        return ingotSet.contains(ingotName);

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
    @Deprecated //
    public static Map<String, Double> getClassInfo(String className) {

        if (validClass(className)) {

            String root = className + ".stats";

            classMap.put("HEALTH", classConfig.getDouble(root + ".baseHealth"));
            classMap.put("MAX_HEALTH", classConfig.getDouble(root + ".maxHealth"));
            classMap.put("SPEED", classConfig.getDouble(root + ".baseSpeed"));
            classMap.put("EXPERIENCE_GAIN", classConfig.getDouble(root + "baseExperienceGain"));
            classMap.put("EVASION", classConfig.getDouble(root + ".baseEvasion"));
            classMap.put("WEAPON_PROFICIENCY", classConfig.getDouble(root + ".baseWeaponProficiency"));
            classMap.put("BOW_PROFICIENCY", classConfig.getDouble(root + ".baseBowProficiency"));
            classMap.put("CROSSBOW_PROFICIENCY", classConfig.getDouble(root + ".baseCrossbowProficiency"));
            classMap.put("HARVEST_CHANCE", classConfig.getDouble(root + ".extra.harvestChance"));
            classMap.put("DOUBLE_HARVEST_CHANCE", classConfig.getDouble(root + ".extra.doubleHarvestChance"));

        } else {

            classMap.put("HEALTH", classConfig.getDouble("classes.None.stats.health"));
            classMap.put("MAX_HEALTH", classConfig.getDouble("classes.None.stats.health"));
            classMap.put("SPEED", classConfig.getDouble("classes.None.stats.speed"));
            classMap.put("EXPERIENCE_GAIN", classConfig.getDouble("classes.none.stats.baseExperienceGain"));
            classMap.put("EVASION", classConfig.getDouble("classes.None.stats.baseEvasion"));
            classMap.put("WEAPON_PROFICIENCY", classConfig.getDouble("classes.None.stats.baseWeaponProficiency"));
            classMap.put("BOW_PROFICIENCY", classConfig.getDouble("classes.None.stats.baseBowProficiency"));
            classMap.put("CROSSBOW_PROFICIENCY", classConfig.getDouble("classes.None.stats.baseCrossbowProficiency"));
            classMap.put("HARVEST_CHANCE", 0.2);
            classMap.put("DOUBLE_HARVEST_CHANCE", 0.0);

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
    @Deprecated //
    public static Map<String, Object> getFoodInfo(String foodName) {

        if (validFood(foodName)) {

            String root = foodName;

            foodMap.put("EXPIRY", expiryConfig.getInt(root));
            foodMap.put("POISON", new PotionEffect(PotionEffectType.POISON, 100, 1));
            foodMap.put("HUNGER", new PotionEffect(PotionEffectType.HUNGER, 300, 1));

        } else {

            foodMap.put("EXPIRY", expiryConfig.getInt("foods.not-listed"));
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
    @Deprecated // WeaponConfig.Weapon() constructor
    public static Map<String, Object> getWeaponInfo(String weaponName) {

        if (validWeapon(weaponName)) {

            String root = weaponName;
            String itemRoot = root + ".item";

            String type = weaponConfig.getString(itemRoot + ".type");
            String localizedName = weaponConfig.getString(itemRoot + ".loc-name");
            String displayName = weaponConfig.getString(itemRoot + ".display-name");
            List<String> lore = weaponConfig.getStringList(itemRoot + ".lore");
            int amount = weaponConfig.getInt(itemRoot + ".amount");

            ItemStack item = constructItemStack(type, amount, displayName, localizedName, lore);

            weaponMap.put("DAMAGE", weaponConfig.getDoubleList(root + ".damage"));
            weaponMap.put("KNOCKBACK", weaponConfig.getDoubleList(root + ".knockback"));
            weaponMap.put("SWEEPING", weaponConfig.getDoubleList(root + ".sweeping"));
            weaponMap.put("DURABILITY", weaponConfig.getIntegerList(root + ".durability"));
            weaponMap.put("ITEM", item);
            weaponMap.put("TYPE", weaponConfig.getString(root + ".type"));
            weaponMap.put("SHAPE", weaponConfig.getStringList(root + ".recipe-shape"));
            weaponMap.put("RECIPE", weaponConfig.getStringList(root + ".recipe-items"));

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

    @Deprecated // WeaponConfig.Weapon() constructor
    public Map<String, Object> iWeaponInfo(String weaponName) {

        Map<String, Object> iWeaponMap = new HashMap<>();

        if (validWeapon(weaponName)) {

            String root = weaponName;
            String itemRoot = root + ".item";

            String type = weaponConfig.getString(itemRoot + ".type");
            String localizedName = weaponConfig.getString(itemRoot + ".loc-name");
            String displayName = weaponConfig.getString(itemRoot + ".display-name");
            List<String> lore = weaponConfig.getStringList(itemRoot + ".lore");
            int amount = weaponConfig.getInt(itemRoot + ".amount");

            ItemStack item = constructItemStack(type, amount, displayName, localizedName, lore);

            iWeaponMap.put("DAMAGE", weaponConfig.getDoubleList(root + ".damage"));
            iWeaponMap.put("KNOCKBACK", weaponConfig.getDoubleList(root + ".knockback"));
            iWeaponMap.put("SWEEPING", weaponConfig.getDoubleList(root + ".sweeping"));
            iWeaponMap.put("DURABILITY", weaponConfig.getIntegerList(root + ".durability"));
            iWeaponMap.put("ITEM", item);
            iWeaponMap.put("TYPE", weaponConfig.getString(root + ".type"));
            iWeaponMap.put("SHAPE", weaponConfig.getStringList(root + ".recipe-shape"));
            iWeaponMap.put("RECIPE", weaponConfig.getStringList(root + ".recipe-items"));

        } else {

            Double[] blank_double = {0.0, 0.0};
            Integer[] blank_integer = {0, 0};

            iWeaponMap.put("DAMAGE", blank_double);
            iWeaponMap.put("KNOCKBACK", blank_double);
            iWeaponMap.put("SWEEPING", blank_double);
            iWeaponMap.put("DURABILITY", blank_integer);
            iWeaponMap.put("ITEM", new ItemStack(Material.AIR));
            iWeaponMap.put("TYPE", "Light");
            iWeaponMap.put("SHAPE", Arrays.asList(""));
            iWeaponMap.put("RECIPE", Arrays.asList(""));

        }
        
        return iWeaponMap;
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
    @Deprecated // ArmorConfig.Armor() constructor
    public static Map<String, Object> getArmorInfo(String armorName) {

        if (validArmor(armorName)) {

            String root = armorName;

            String itemRoot = root + ".item";

            String type = armorConfig.getString(itemRoot + ".type");
            String localizedName = armorConfig.getString(itemRoot + ".loc-name");
            String displayName = armorConfig.getString(itemRoot + ".display-name");
            List<String> lore = armorConfig.getStringList(itemRoot + ".lore");
            int amount = armorConfig.getInt(itemRoot + ".amount");

            ItemStack item = constructItemStack(type, amount, displayName, localizedName, lore);

            armorMap.put("ITEM", item);
            armorMap.put("ARMOR", armorConfig.getDouble(root + ".armor"));
            armorMap.put("TYPE", armorConfig.getString(root + ".type"));
            armorMap.put("SHAPE", armorConfig.getStringList(root + ".recipe-shape"));
            armorMap.put("RECIPE", armorConfig.getStringList(root + ".recipe-items"));

        } else {

            armorMap.put("ITEM", new ItemStack(Material.AIR));
            armorMap.put("ARMOR", 0);
            armorMap.put("TYPE", "Heavy");
            armorMap.put("SHAPE", Arrays.asList(""));
            armorMap.put("RECIPE", Arrays.asList(""));

        }

        return armorMap;

    }

    @Deprecated // Provided in ArmorConfig.Armor() constructor
    public Map<String, Object> iArmorInfo(String armorName) {

        Map<String, Object> iArmorMap = new HashMap<>();

        if (validArmor(armorName)) {

            String root = armorName;

            String itemRoot = root + ".item";

            String type = armorConfig.getString(itemRoot + ".type");
            String localizedName = armorConfig.getString(itemRoot + ".loc-name");
            String displayName = armorConfig.getString(itemRoot + ".display-name");
            List<String> lore = armorConfig.getStringList(itemRoot + ".lore");
            int amount = armorConfig.getInt(itemRoot + ".amount");

            ItemStack item = constructItemStack(type, amount, displayName, localizedName, lore);

            iArmorMap.put("ITEM", item);
            iArmorMap.put("ARMOR", armorConfig.getDouble(root + ".armor"));
            iArmorMap.put("TYPE", armorConfig.getString(root + ".type"));
            iArmorMap.put("SHAPE", armorConfig.getStringList(root + ".recipe-shape"));
            iArmorMap.put("RECIPE", armorConfig.getStringList(root + ".recipe-items"));

        } else {

            iArmorMap.put("ITEM", new ItemStack(Material.AIR));
            iArmorMap.put("ARMOR", 0);
            iArmorMap.put("TYPE", "Heavy");
            iArmorMap.put("SHAPE", Arrays.asList(""));
            iArmorMap.put("RECIPE", Arrays.asList(""));

        }

        return iArmorMap;

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
    @Deprecated // IngotConfig.Ingot() constructor
    public static Map<String, Object> getIngotInfo(String ingotName) {

        if (validIngot(ingotName)) {

            String root = ingotName;

            String itemRoot = root + ".item";

            String type = ingotConfig.getString(itemRoot + ".type");
            String localizedName = ingotConfig.getString(itemRoot + ".loc-name");
            String displayName = ingotConfig.getString(itemRoot + ".display-name");
            List<String> lore = ingotConfig.getStringList(itemRoot + ".lore");
            int amount = ingotConfig.getInt(itemRoot + ".amount");

            Bukkit.getLogger().info(ingotName);
            Bukkit.getLogger().info(type);
            Bukkit.getLogger().info(localizedName);
            Bukkit.getLogger().info(displayName);
            Bukkit.getLogger().info(lore.toString());
            Bukkit.getLogger().info("" + amount);

            ItemStack item = constructItemStack(type, amount, displayName, localizedName, lore);

            oreMap.put("PROGRESSION", ingotConfig.getString(root + ".smelt_into"));
            oreMap.put("ITEM", item);
            oreMap.put("SMELT_TIME", ingotConfig.getInt(root + ".time"));
            oreMap.put("SMELT_AMOUNT", ingotConfig.getInt(root + ".smelt_times"));
            oreMap.put("LOSS", ingotConfig.getInt(root + ".loss"));

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
    @Deprecated //
    public static List<String> getClassWeaponTypes(String className) {

        if (validClass(className)) {

            return classConfig.getStringList(className + ".weaponProficiency");

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
    @Deprecated //
    public static List<String> getUsableWeapons(String className) {

        if (validClass(className)) {

            List<String> classProficient = getClassWeaponTypes(className);
        
            List<String> found = new ArrayList<>();

            for (String weapon : weaponSet) {

                String type = weaponConfig.getString(weapon + ".type");

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
    @Deprecated //
    public static List<String> getClassArmorTypes(String className) {

        if (validClass(className)) {

            return classConfig.getStringList(className + ".armorProficiency");

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
    @Deprecated //
    public static List<Material> getAllAxes() {

        List<Material> found = new ArrayList<>();

        found.add(Material.WOODEN_AXE);
        found.add(Material.STONE_AXE);
        found.add(Material.IRON_AXE);
        found.add(Material.GOLDEN_AXE);
        found.add(Material.NETHERITE_AXE);
        
        return found;

    }
    
    @Deprecated //
    public static List<Material> getAllPickaxes() {

        List<Material> found = new ArrayList<>();

        found.add(Material.WOODEN_PICKAXE);
        found.add(Material.STONE_PICKAXE);
        found.add(Material.IRON_PICKAXE);
        found.add(Material.GOLDEN_PICKAXE);
        found.add(Material.NETHERITE_PICKAXE);
        
        return found;

    }
    
    @Deprecated //
    public static List<Material> getAllShovels() {

        List<Material> found = new ArrayList<>();

        found.add(Material.WOODEN_SHOVEL);
        found.add(Material.STONE_SHOVEL);
        found.add(Material.IRON_SHOVEL);
        found.add(Material.GOLDEN_SHOVEL);
        found.add(Material.NETHERITE_SHOVEL);
        
        return found;

    }
    
    @Deprecated //
    public static List<Material> getAllHoes() {

        List<Material> found = new ArrayList<>();

        found.add(Material.WOODEN_HOE);
        found.add(Material.STONE_HOE);
        found.add(Material.IRON_HOE);
        found.add(Material.GOLDEN_HOE);
        found.add(Material.NETHERITE_HOE);
        
        return found;

    }
    
    @Deprecated //
    public static List<Material> getAllBoots() {

        List<Material> found = new ArrayList<>();

        found.add(Material.LEATHER_BOOTS);
        found.add(Material.IRON_BOOTS);
        found.add(Material.GOLDEN_BOOTS);
        found.add(Material.NETHERITE_BOOTS);
        
        return found;

    }
    
    @Deprecated //
    public static List<Material> getCropBlocks() {

        List<Material> found = new ArrayList<>();

        found.add(Material.WHEAT);
        found.add(Material.CARROTS);
        found.add(Material.POTATOES);
        found.add(Material.BEETROOT);
        found.add(Material.PUMPKIN);
        found.add(Material.MELON);
        found.add(Material.COCOA_BEANS);
        found.add(Material.SWEET_BERRIES);
        found.add(Material.SUGAR_CANE);

        return found;

    }
    
    @Deprecated //
    public static List<Material> getFarmerExclusiveCrops() {

        List<Material> found = new ArrayList<>();

        found.add(Material.PUMPKIN);
        found.add(Material.MELON);
        found.add(Material.COCOA_BEANS);
        found.add(Material.SWEET_BERRIES);
        found.add(Material.SUGAR_CANE);
        found.add(Material.BAMBOO);
        found.add(Material.BAMBOO_SAPLING);

        return found;

    }
    
    @Deprecated //
    public static List<Material> getOreBlocks() {

        Set<String> blocks = oreSet;
        List<Material> found = new ArrayList<>();

        for (String block : blocks) {

            found.add(Material.getMaterial(block));

        }

        return found;

    }
    
    @Deprecated //
    public static List<String> getUsableArmor(String className) {

        if (validClass(className)) {

            List<String> classProficient = getClassArmorTypes(className);
        
            List<String> found = new ArrayList<>();

            for (String armor : armorSet) {

                String type = armorConfig.getString(armor + ".type");

                if (classProficient.contains(type)) found.add(armor);

            }

            return found;

        } else return Arrays.asList("NOT A VALID CLASS");

    }
    
    @Deprecated //
    public static List<Material> getFish() {

        List<Material> found = new ArrayList<>();

        found.add(Material.COD);
        found.add(Material.SALMON);

        return found;
    
    }
    
    @Deprecated // Provided in WeaponConfig.Weapon() constructor and ArmorConfig.Armor() constructor
    public static Map<String, List<String>> getPatterns() {

        Map<String, List<String>> map = new HashMap<>();

        for (String weapon : getWeaponSet()) {

            map.put(weapon, weaponConfig.getStringList(weapon + ".recipe-shape"));

        }

        for (String armor : getArmorSet()) {

            map.put(armor, armorConfig.getStringList(armor + ".recipe-shape"));

        }

        return map;

    }
    
    @Deprecated // Provided in WeaponConfig.Weapon() constructor and ArmorConfig.Armor() constructor
    public static Map<String, List<String>> getRecipeItems() {

        Map<String, List<String>> map = new HashMap<>();

        for (String weapon : getWeaponSet()) {

            map.put(weapon, weaponConfig.getStringList(weapon + ".recipe-items"));

        }

        for (String armor : getArmorSet()) {

            map.put(armor, armorConfig.getStringList(armor + ".recipe-items"));

        }

        return map;

    }
    
    @Deprecated // GeneralConfig.MySQL static class
    public static Map<String, String> getMySQLInfo() {

        String root = "MySQL";

        mySQLMap.put("USER", miscConfig.getString(root + ".user"));
        mySQLMap.put("PASSWORD", miscConfig.getString(root + ".password"));
        mySQLMap.put("DATABASE", miscConfig.getString(root + ".database"));
        mySQLMap.put("IP", miscConfig.getString(root + ".ip"));
        mySQLMap.put("PORT", miscConfig.getString(root + ".port"));

        return mySQLMap;

    }
    
    @Deprecated // 
    public static Map<String, Integer> getOreChance(String oreName) {
        
        Map<String, Integer> chance = new HashMap<>();

            String root = oreName;
            Set<String> keys = oreConfig.getConfigurationSection(root).getKeys(false);
    
            for (String key : keys) {
    
                if (!key.equals("chance")) {

                    String newRoot = root + "." + key;
    
                    chance.put(key, oreConfig.getInt(newRoot + ".chance"));
                }
    
            }

        return chance;
    }
    
    @Deprecated //
    public static Map<String, Integer> getIngotChance(String oreName, String ingotName, String className) {

        String root = oreName + "." + ingotName;

        Set<String> keys = oreConfig.getConfigurationSection(root).getKeys(false);
        Map<String, Integer> map = new HashMap<>();

        boolean miner = className.equals("Miner");

        for (String key : keys) {

            if (!key.equals("chance")) {

                String newRoot = root + "." + key;

                if (!miner && oreConfig.getString(newRoot + ".class").equals("Any")) {
    
                    map.put(key, oreConfig.getInt(newRoot + ".chance"));

                    return map;
    
                } else {
    
                    map.put(key, oreConfig.getInt(newRoot + ".chance"));
    
                }
            }
        }


        return map;

    }
    
    @Deprecated // 
    public static ItemStack getIngot(String oreName, String ingotName, String ingotType) {

        Bukkit.getLogger().info("oreName: " + oreName + " ingotName: " + ingotName + " ingotType: " + ingotType);

        String root = oreName + "." + ingotName + "." + ingotType;

        String type = oreConfig.getString(root + ".item.type");
        int amount = oreConfig.getInt(root + ".item.amount");
        String displayName = oreConfig.getString(root + ".item.display-name");
        String localizedName = oreConfig.getString(root + ".item.loc-name");
        List<String> lore = oreConfig.getStringList(root + ".item.lore");

        return constructItemStack(type, amount, displayName, localizedName, lore);

    }

    @Deprecated // ConstructItemStack.construct()
    private static ItemStack constructItemStack(String type, int amount, String displayName, String localizedName, List<String> lore) {

        Bukkit.getLogger().info("type: " + type + " amount: " + amount + " display-name: " + displayName + " loc-name: " + localizedName + " lore: " + lore);

        if (type == null) throw new NullPointerException("Type can not be null!");

        ItemStack item = new ItemStack(Material.getMaterial(type), amount);

        ItemMeta meta = item.getItemMeta();

        if (displayName != null) meta.setDisplayName(displayName);
        if (localizedName != null) meta.setLocalizedName(localizedName);
        if (lore != null && !lore.isEmpty()) meta.setLore(lore);

        item.setItemMeta(meta);

        return item;

    }

    @Deprecated // FileGetter.find()
    private static boolean findFile(File[] files, String check) {

        for (File file : files) {

            if (file.getName().equals(check)) return true;

        }

        return false;

    }

    @Deprecated // FileGetter.get()
    private static FileConfiguration getConfig(String check) {

        FileConfiguration config;

        if (findFile(Main.plugin().getDataFolder().listFiles(), check)) {

            File file = new File(Main.plugin().getDataFolder().getPath(), check);

            config = YamlConfiguration.loadConfiguration(file);
        }

        else {

            InputStream is = Main.plugin().getClass().getClassLoader().getResourceAsStream(check);

            Reader reader = new InputStreamReader(is);

            config = YamlConfiguration.loadConfiguration(reader);

        }

        return config;

    }

}