package dev.boooiil.historia.core;

import dev.boooiil.historia.core.commands.*;
import dev.boooiil.historia.core.configuration.ConfigurationLoader;
import dev.boooiil.historia.core.database.mysql.MySQLConnection;
import dev.boooiil.historia.core.database.mysql.MySQLHandler;
import dev.boooiil.historia.core.events.blockInteraction.BlockBreakListener;
import dev.boooiil.historia.core.events.blockInteraction.BlockFromToListener;
import dev.boooiil.historia.core.events.blockInteraction.BlockPlaceListener;
import dev.boooiil.historia.core.events.connection.PlayeQuitListener;
import dev.boooiil.historia.core.events.connection.PlayerJoinListener;
import dev.boooiil.historia.core.events.experience.PlayerExpChangeListener;
import dev.boooiil.historia.core.events.food.FoodLevelChangeListener;
import dev.boooiil.historia.core.events.inventory.InventoryClickListener;
import dev.boooiil.historia.core.events.mobs.EntityBreedListener;
import dev.boooiil.historia.core.events.mobs.EntityTameListener;
import dev.boooiil.historia.core.events.playerInteraction.PlayerInteractEntityListener;
import dev.boooiil.historia.core.events.playerInteraction.PlayerInteractListener;
import dev.boooiil.historia.core.runnable.ClassEnchantsRunnable;
import dev.boooiil.historia.core.runnable.SavePlayerRunnable;
import dev.boooiil.historia.core.runnable.UpdateScoreboardRunnable;
import dev.boooiil.historia.core.util.FileIO;
import dev.boooiil.historia.core.util.Logging;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.Map;

/**
 * It's a plugin that loads, enables, and disables.
 */
public class Main extends JavaPlugin {

    public static boolean isTesting = false;
    private static Plugin instance = null;

    public Main() {
        super();
    }

    /**
     * It's a constructor that is called when the plugin is loaded.
     * 
     * @param loader      - The plugin loader responsible for this plugin, can be
     *                    null
     * @param description - The plugin.yml file for this plugin
     * @param dataFolder  - The folder that contains the plugin's data
     * @param file        - The plugin's file
     */
    protected Main(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    // It's a method that is called when the plugin is loaded.
    public void onLoad() {

        instance = this;

        Logging.infoToConsole("Plugin has loaded.");

        deregisterRecipes();

        // Check config files
        FileIO.checkFiles();

        System.out.println("RUNNING VERSION: " + Bukkit.getVersion());

        if (Bukkit.getVersion().contains("MockBukkit")) {
            System.out.println("RUNNING IN TEST MODE");
            isTesting = true;
        }

    }

    @Override
    // It's a method that is called when the plugin is enabled.
    // Test
    public void onEnable() {

        // Save / Load the config in the Historia plugins folder.
        this.saveDefaultConfig();

        ConfigurationLoader.init();

        registerEvent(new EntityBreedListener());
        registerEvent(new EntityTameListener());
        registerEvent(new PlayerExpChangeListener());
        registerEvent(new FoodLevelChangeListener());
        registerEvent(new InventoryClickListener());
        registerEvent(new BlockBreakListener());
        registerEvent(new BlockPlaceListener());
        registerEvent(new PlayerJoinListener());
        registerEvent(new PlayeQuitListener());
        // registerEvent(new PlayerRightClickAir());
        registerEvent(new PlayerInteractEntityListener());
        registerEvent(new BlockFromToListener());
        registerEvent(new PlayerInteractListener());

        registerCommand("checkplayers", new CommandPlayers());
        registerCommand("debug", new CommandDebug());
        registerCommand("stats", new CommandStats());
        registerCommand("set", new CommandSet());
        registerCommand("proficiency", new CommandProficiency());

        registerRunnable(new ClassEnchantsRunnable());
        registerRunnable(new UpdateScoreboardRunnable());
        registerRunnable(new SavePlayerRunnable(), 6000);

        if (isTesting) {
            MySQLConnection.customConnection("historia_test", "historia-test", "ThisIsForTesting#Historia!", "127.0.0.1",
                    "3306");
            MySQLConnection.connect();
            MySQLHandler.createTable();
        } else {
            MySQLConnection.connect();
            MySQLHandler.createTable();
        }

        Logging.infoToConsole("Plugin Enabled.");

    }

    @Override
    // It's a method that is called when the plugin is disabled.
    public void onDisable() {

        MySQLConnection.closeConnection();

        getLogger().info("Plugin disabled.");
    }

    /**
     * It returns the plugin instance
     * 
     * @return The plugin object.
     */
    public static Plugin plugin() {

        return instance;

    }

    /**
     * It returns the server instance
     * 
     * @return The server.
     */
    public static Server server() {

        return plugin().getServer();

    }

    /**
     * It disables the plugin
     * 
     *
     */
    public static void disable() {

        plugin().getPluginLoader().disablePlugin(plugin());

    }

    /**
     * It registers an event
     * 
     * @param event The event you want to register.
     */
    private void registerEvent(Listener event) {

        this.getServer().getPluginManager().registerEvents(event, this);

    }

    /**
     * It registers a command to the server
     * 
     * @param commandName The name of the command you want to register.
     * @param command     The command to register
     */
    private void registerCommand(String commandName, CommandExecutor command) {

        if (commandName == null || command == null)
            return;

        this.getCommand(commandName).setExecutor(command);

    }

    /**
     * It registers a runnable
     * 
     * @param runnable The runnable you want to register.
     */
    private void registerRunnable(BukkitRunnable runnable) {

        runnable.runTaskTimer(this, 0, 20);

    }

    /**
     * Registers a BukkitRunnable to be executed on a timer.
     * 
     * @param runnable The BukkitRunnable to be executed.
     * @param time     The time in ticks between each execution of the runnable.
     */
    private void registerRunnable(BukkitRunnable runnable, long time) {
        runnable.runTaskTimer(this, 0, time);
    }

    private void deregisterRecipes() {

        Bukkit.removeRecipe(NamespacedKey.minecraft("iron_nugget"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("gold_nugget"));

        // Bukkit.removeRecipe(NamespacedKey.minecraft("iron_sword"));
        // Bukkit.removeRecipe(NamespacedKey.minecraft("gold_sword"));
        // Bukkit.removeRecipe(NamespacedKey.minecraft("stone_sword"));
        // Bukkit.removeRecipe(NamespacedKey.minecraft("wood_sword"));
        // Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_sword"));

        // Bukkit.removeRecipe(NamespacedKey.minecraft("iron_axe"));
        // Bukkit.removeRecipe(NamespacedKey.minecraft("gold_axe"));
        // Bukkit.removeRecipe(NamespacedKey.minecraft("stone_axe"));
        // Bukkit.removeRecipe(NamespacedKey.minecraft("wood_axe"));
        // Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_axe"));

        // Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_pickaxe"));
        // Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_shovel"));
        // Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_hoe"));

        // Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_helmet"));
        // Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_chestplate"));
        // Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_leggings"));
        // Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_boots"));

        // Bukkit.removeRecipe(NamespacedKey.minecraft("iron_helmet"));
        // Bukkit.removeRecipe(NamespacedKey.minecraft("iron_chestplate"));
        // Bukkit.removeRecipe(NamespacedKey.minecraft("iron_leggings"));
        // Bukkit.removeRecipe(NamespacedKey.minecraft("iron_boots"));

        // Bukkit.removeRecipe(NamespacedKey.minecraft("gold_helmet"));
        // Bukkit.removeRecipe(NamespacedKey.minecraft("gold_chestplate"));
        // Bukkit.removeRecipe(NamespacedKey.minecraft("gold_leggings"));
        // Bukkit.removeRecipe(NamespacedKey.minecraft("gold_boots"));

        // Bukkit.removeRecipe(NamespacedKey.minecraft("netherrite_helmet"));
        // Bukkit.removeRecipe(NamespacedKey.minecraft("netherrite_chestplate"));
        // Bukkit.removeRecipe(NamespacedKey.minecraft("netherrite_leggings"));
        // Bukkit.removeRecipe(NamespacedKey.minecraft("netherrite_boots"));

    }

}
