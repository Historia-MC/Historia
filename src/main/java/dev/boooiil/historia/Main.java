package dev.boooiil.historia;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import dev.boooiil.historia.commands.Ignite;
import dev.boooiil.historia.commands.Item;
import dev.boooiil.historia.commands.Message;
import dev.boooiil.historia.commands.Stats;
import dev.boooiil.historia.commands.DebugItems;
import dev.boooiil.historia.crafting.RecipeLoader;
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
        this.getCommand("Message").setExecutor(new Message());
        this.getCommand("Item").setExecutor(new Item());
        this.getCommand("DebugItems").setExecutor(new DebugItems());
        this.getCommand("Stats").setExecutor(new Stats());

        this.getServer().getPluginManager().registerEvents(new HistoriaEvents(), this);
        
        ClassRunnable classRunnable = new ClassRunnable(this);

        classRunnable.runTaskTimer(this, 0L, 20L);

        //Disabled due to expiry not being finished.
        //this.getServer().getPluginManager().registerEvents(new PlayerItemHeld(), this);

        
        //Save / Load the config in the Historia plugins folder.
        this.saveDefaultConfig();

        getLogger().info("Plugin enabled.");

        RecipeLoader.load(this);

        try {

            MySQL sql = new MySQL();

            sql.createTable();

            getLogger().info("Loaded MySQL");

        } catch (Exception e) { getLogger().info("Failed to load MySQL"); e.printStackTrace(); }

    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled.");
    }
}