package dev.boooiil.historia.configuration.specific;

import java.io.File;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import dev.boooiil.historia.HistoriaPlugin;
import dev.boooiil.historia.util.ConfigUtil;

public class CropConfig {

    private YamlConfiguration configuration;
    private List<Material> standard;
    private List<Material> tall;

    /**
     * It loads a YAML file from the plugin's data folder, and then populates a
     * HashMap with the keys
     * and values from the YAML file
     * 
     * @param fileName The name of the file you want to load.
     */
    public void loadConfiguration(String fileName) {

        // @sonatype-lift ignore
        this.configuration = ConfigUtil.yamlFromSource(new File(HistoriaPlugin.plugin().getDataFolder(), fileName));

        List<String> standardList = configuration.getStringList("standard");
        List<String> tallList = configuration.getStringList("tall");

        for (String standard : standardList) {
            this.standard.add(Material.getMaterial(standard));
        }

        for (String tall : tallList) {
            this.tall.add(Material.getMaterial(tall));
        }

    }

    /**
     * It returns the configuration file
     * 
     * @return The configuration.
     */
    public YamlConfiguration getConfiguration() {

        return configuration;

    }

    /**
     * It returns the standard crops.
     * 
     * @return The standard crops.
     */
    public List<Material> getStandard() {

        return standard;

    }

    /**
     * It returns the tall crops.
     * 
     * @return The tall crops.
     */
    public List<Material> getTall() {

        return tall;

    }

    /**
     * It returns a list containing all the standard and tall crops.
     * 
     * @return A list containing all the standard and tall crops.
     */
    public List<Material> getAll() {

        List<Material> all = standard;
        all.addAll(tall);

        return all;
    }

}
