package dev.boooiil.historia;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import dev.boooiil.historia.commands.Ignite;
import dev.boooiil.historia.commands.Item;
import dev.boooiil.historia.commands.Stats;
import dev.boooiil.historia.commands.DebugItems;
import dev.boooiil.historia.mysql.MySQL;
import dev.boooiil.historia.runnable.ClassRunnable;

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
        this.getCommand("Class").setExecutor(new dev.boooiil.historia.commands.Class());
        this.getCommand("Item").setExecutor(new Item());
        this.getCommand("DebugItems").setExecutor(new DebugItems());
        this.getCommand("Stats").setExecutor(new Stats());

        this.getServer().getPluginManager().registerEvents(new HistoriaEvents(), this);
        
        getLogger().info("Starting class task...");

        ClassRunnable classRunnable = new ClassRunnable(this);

        classRunnable.runTaskTimer(this, 0L, 20L);

        getLogger().info("Task queued.");
        
        //Save / Load the config in the Historia plugins folder.
        this.saveDefaultConfig();

        getLogger().info("Plugin enabled.");

        getLogger().info("Loading MySQL...");

        MySQL.createTable();

    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled.");
    }

    public static void disable(Plugin plugin) {

        plugin.getServer().getPluginManager().disablePlugin(plugin);

    }
}