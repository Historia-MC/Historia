package dev.boooiil.historia;

import java.io.File;
import java.sql.SQLException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import dev.boooiil.historia.commands.Ignite;
import dev.boooiil.historia.commands.Item;
import dev.boooiil.historia.commands.Message;
import dev.boooiil.historia.commands.DebugItems;
import dev.boooiil.historia.crafting.RecipeLoader;
import dev.boooiil.historia.mysql.MySQL;

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

    @Override
    public void onLoad() {
        getLogger().info("Plugin has loaded.");
    }

    @Override
    public void onEnable() {

        //Disabled due to being beginner commands and not having a use.
        this.getCommand("Ignite").setExecutor(new Ignite());
        this.getCommand("Message").setExecutor(new Message());
        this.getCommand("Item").setExecutor(new Item());
        this.getCommand("DebugItems").setExecutor(new DebugItems());

        this.getServer().getPluginManager().registerEvents(new HistoriaEvents(), this);

        //Disabled due to expiry not being finished.
        //this.getServer().getPluginManager().registerEvents(new PlayerItemHeld(), this);

        
        //Save / Load the config in the Historia plugins folder.
        setConfig();

        getLogger().info("Plugin enabled.");

        RecipeLoader.load(this);

        try {

            MySQL sql = new MySQL();
            sql.intitate();

        } 
        catch (SQLException e) { e.printStackTrace(); }
        finally { getLogger().info("MySQL Configured and Running."); }

    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled.");
    }

    private void setConfig() {

        //Create an association for the file, "config.yml"
        File configFile = new File(getDataFolder(), "config.yml");

        //If the file doesn't exist, save it to the current directory.
        if (!configFile.exists()) { this.saveConfig(); } 

        //Else, laod the config from file.
        else {

            try { this.getConfig().load(configFile); } 
            catch (Exception e) { e.printStackTrace(); }

        }
    }
}