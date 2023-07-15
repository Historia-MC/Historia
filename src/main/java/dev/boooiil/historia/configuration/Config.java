package dev.boooiil.historia.configuration;

import dev.boooiil.historia.configuration.specific.ArmorConfig;
import dev.boooiil.historia.configuration.specific.CropConfig;
import dev.boooiil.historia.configuration.specific.CustomItemConfig;
import dev.boooiil.historia.configuration.specific.GeneralConfig;
import dev.boooiil.historia.configuration.specific.IngotConfig;
import dev.boooiil.historia.configuration.specific.OreConfig;
import dev.boooiil.historia.configuration.specific.WeaponConfig;

/**
 * It loads the configuration files.
 */
public class Config {
    
    private static ArmorConfig armorConfig = new ArmorConfig();
    private static IngotConfig ingotConfig = new IngotConfig();
    private static OreConfig oreConfig = new OreConfig();
    private static WeaponConfig weaponConfig = new WeaponConfig();
    private static GeneralConfig generalConfig = new GeneralConfig();
    private static CustomItemConfig customItemConfig = new CustomItemConfig();
    private static CropConfig cropConfig = new CropConfig();

    /**
     * It returns the armorConfig variable
     * 
     * @return The armorConfig variable.
     */
    public static ArmorConfig getArmorConfig() {
        return armorConfig;
    }

    /**
     * It returns the ingotConfig variable
     * 
     * @return The ingotConfig variable.
     */
    public static IngotConfig getIngotConfig() {
        return ingotConfig;
    }

    /**
     * It returns the oreConfig variable
     * 
     * @return The oreConfig variable.
     */
    public static OreConfig getOreConfig() {
        return oreConfig;
    }

    /**
     * It returns the weaponConfig variable
     * 
     * @return The weaponConfig object.
     */
    public static WeaponConfig getWeaponConfig() {
        return weaponConfig;
    }

    /**
     * This function returns the generalConfig object
     * 
     * @return The generalConfig object.
     */
    public static GeneralConfig getGeneralConfig() {
        return generalConfig;
    }

    /**
     * It returns the customItemConfig variable
     * 
     * @return The customItemConfig variable.
     */
    public static CustomItemConfig getCustomItemConfig() {
        return customItemConfig;
    }

    public static CropConfig getCropConfig() {

        return cropConfig;
    }

    /**
     * It loads the configuration files
     */
    public static void init() {

        armorConfig.loadConfiguration("armor.yml");
        ingotConfig.loadConfiguration("ingots.yml");
        oreConfig.loadConfiguration("ores.yml");
        weaponConfig.loadConfiguration("weapons.yml");
        customItemConfig.loadConfiguration("customitems.yml");
        cropConfig.loadConfiguration("crops.yml");

    }

    /**
     * It's a function that reloads the config file
     */
    public static void reload() {

        init();

    }
    
}
