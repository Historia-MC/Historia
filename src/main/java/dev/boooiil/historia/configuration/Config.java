package dev.boooiil.historia.configuration;

public class Config {
    private static ArmorConfig armourConfig = new ArmorConfig();
    private static IngotConfig ingotConfig = new IngotConfig();
    private static OreConfig oreConfig = new OreConfig();
    private static WeaponConfig weaponConfig = new WeaponConfig();
    private static GeneralConfig generalConfig = new GeneralConfig();

    public static ArmorConfig getArmourConfig() {
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
}
