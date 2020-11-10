package dev.boooiil.historia;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import dev.boooiil.historia.commands.Ignite;
import dev.boooiil.historia.commands.Message;
import dev.boooiil.historia.crafting.RecipeLoader;

public class Main extends JavaPlugin {

    /**
     * 
     * Server starts => Config loaded => passed to respective handler => other classes can access the object
     * 
     * Config section (class, expiry, etc) passed to respective handler that parses that infromation into an accessable object.
     * 
     * Player logs in and is applied stats based on the configuration stored.
     * 
     * Player respawns and is applied stats based on the configuration stored.
     * 
     */

    private FileConfiguration config = this.getConfig();

    @Override
    public void onLoad() {
        getLogger().info("Plugin has loaded.");
    }

    @Override
    public void onEnable() {

        //Disabled due to being beginner commands and not having a use.
        this.getCommand("Ignite").setExecutor(new Ignite());
        this.getCommand("Message").setExecutor(new Message());

        this.getServer().getPluginManager().registerEvents(new HistoriaEvents(), this);

        //Disabled due to expiry not being finished.
        //this.getServer().getPluginManager().registerEvents(new PlayerItemHeld(), this);

        if (!created()) {
            this.saveDefaultConfig(); getLogger().info("Plugin enabled.");
        }

        //RecipeLoader.load(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled.");
    }

    private boolean created() {

        //Create an association for the file, "config.yml"
        File configFile = new File(getDataFolder(), "config.yml");

        //Return if the file exists.
        return configFile.exists();

    }



}