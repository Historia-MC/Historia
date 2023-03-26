package dev.boooiil.historia.configuration;

public class Config {
    private static ArmorConfig armourConfig;
    private static IngotConfig ingotConfig;
    private static OreConfig oreConfig;
    private static WeaponConfig weaponConfig;
    private static GeneralConfig generalConfig;

    public static ArmorConfig getArmorConfig() {
        return armourConfig;
    }

    public static IngotConfig getIngotConfig() {
        return ingotConfig;
    }

    public static OreConfig getOreConfig() {
        return oreConfig;
    }

    public static WeaponConfig getWeaponConfig() {
        return weaponConfig;
    }

    public static GeneralConfig getGeneralConfig() {
        return generalConfig;
    }

    public static void init() {

        armourConfig = new ArmorConfig();
        ingotConfig = new IngotConfig();
        oreConfig = new OreConfig();
        weaponConfig = new WeaponConfig();
        generalConfig = new GeneralConfig();

    }

    public static void reload() {

        armourConfig = new ArmorConfig();
        ingotConfig = new IngotConfig();
        oreConfig = new OreConfig();
        weaponConfig = new WeaponConfig();
        generalConfig = new GeneralConfig();

    }
    
}
