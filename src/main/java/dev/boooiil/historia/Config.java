package dev.boooiil.historia;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Config {

    //Get the current configuration file for the plugin.
    private FileConfiguration configuration = Bukkit.getPluginManager().getPlugin("Historia").getConfig();

    //Load lists.
    final List<String> armorList = configuration.getStringList("armors.list");
    final List<String> blockList = configuration.getStringList("blocks.list");
    final List<String> classList = configuration.getStringList("classes.list");
    final List<String> foodList = configuration.getStringList("foods.list");
    final List<String> oreList = configuration.getStringList("ores.list");
    final List<String> weaponList = configuration.getStringList("weapons.list");

    private Map<String, Object> armorMap = new HashMap<>();
    private Map<String, Double> classMap = new HashMap<>();
    private Map<String, Object> foodMap = new HashMap<>();
    private Map<String, String> mySQLMap = new HashMap<>();
    private Map<String, Object> oreMap = new HashMap<>();
    private Map<String, Object> weaponMap = new HashMap<>();

    public List<String> getArmorList() {

        return armorList;

    }

    public List<String> getBlockList() {

        return blockList;
        
    }

    public List<String> getClassList() {

        return classList;
        
    }

    public List<String> getFoodList() {

        return foodList;
        
    }

    public List<String> getOreList() {

        return oreList;
        
    }

    public List<String> getWeaponList() {

        return weaponList;
        
    }

    public boolean validClass(String className) {

        return classList.contains(className);

    }

    public boolean validFood(String foodName) {

        return foodList.contains(foodName);

    }

    public boolean validWeapon(String weaponName) {

        return weaponList.contains(weaponName);

    }

    public boolean validArmor(String armorName) {

        return armorList.contains(armorName);

    }

    public boolean validOre(String oreName) {

        return oreList.contains(oreName);

    }

    public boolean validBlock(String blockName) {

        return blockList.contains(blockName);

    }

    public Map<String, Double> getClassInfo(String className) {

        if (validClass(className)) {

            String root = "classes." + className + ".stats";

            classMap.put("HEALTH", configuration.getDouble(root + ".baseHealth"));
            classMap.put("SPEED", configuration.getDouble(root + ".baseSpeed"));
            classMap.put("EVASION", configuration.getDouble(root + ".baseEvasion"));
            classMap.put("SWORD_PROFICIENCY", configuration.getDouble(root + ".baseSwordProficiency"));
            classMap.put("BOW_PROFICIENCY", configuration.getDouble(root + ".baseBowProficiency"));
            classMap.put("CROSSBOW_PROFICIENCY", configuration.getDouble(root + ".baseCrossbowProficiency"));

        } else {

            classMap.put("HEALTH", configuration.getDouble("classes.None.stats.health"));
            classMap.put("SPEED", configuration.getDouble("classes.None.stats.speed"));
            classMap.put("EVASION", configuration.getDouble("classes.None.stats.baseEvasion"));
            classMap.put("SWORD_PROFICIENCY", configuration.getDouble("classes.None.stats.baseSwordProficiency"));
            classMap.put("BOW_PROFICIENCY", configuration.getDouble("classes.None.stats.baseBowProficiency"));
            classMap.put("CROSSBOW_PROFICIENCY", configuration.getDouble("classes.None.stats.baseCrossbowProficiency"));

        }

        return classMap;

    }

    public Map<String, Object> getFoodInfo(String foodName) {

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

    public Map<String, Object> getWeaponInfo(String weaponName) {

        if (validWeapon(weaponName)) {

            String root = "weapons." + weaponName;

            weaponMap.put("DAMAGE", configuration.getDouble(root + ".damage"));
            weaponMap.put("KNOCKBACK", configuration.getDouble(root + ".knockback"));
            weaponMap.put("SWEEPING", configuration.getDouble(root + ".sweeping"));
            weaponMap.put("ITEM", configuration.getItemStack(root + ".item"));
            weaponMap.put("SHAPE", configuration.getStringList(root + ".recipe-shape"));
            weaponMap.put("RECIPE", configuration.getStringList(root + ".recipe-items"));

        } else {

            weaponMap.put("DAMAGE", 1);
            weaponMap.put("KNOCKBACK", 0);
            weaponMap.put("SWEEPING", 0);
            weaponMap.put("ITEM", new ItemStack(Material.AIR));
            weaponMap.put("SHAPE", Arrays.asList(""));
            weaponMap.put("RECIPE", Arrays.asList(""));

        }
        
        return weaponMap;

    }

    public Map<String, Object> getArmorInfo(String armorName) {

        if (validArmor(armorName)) {

            String root = "armor." + armorName;

            armorMap.put("ITEM", configuration.getItemStack(root + ".item"));
            armorMap.put("ARMOR", configuration.getDouble(root + ".armor"));
            armorMap.put("SHAPE", configuration.getStringList(root + ".recipe-shape"));
            armorMap.put("RECIPE", configuration.getStringList(root + ".recipe-items"));

        } else {

            armorMap.put("ITEM", new ItemStack(Material.AIR));
            armorMap.put("ARMOR", 0);
            armorMap.put("SHAPE", Arrays.asList(""));
            armorMap.put("RECIPE", Arrays.asList(""));

        }

        return armorMap;

    }

    public Map<String, Object> getOreInfo(String oreName) {

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

    public Map<String, String> getMySQLInfo() {

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