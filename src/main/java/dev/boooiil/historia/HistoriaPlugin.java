package dev.boooiil.historia;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.scheduler.BukkitRunnable;

import dev.boooiil.historia.commands.CommandSet;
import dev.boooiil.historia.commands.CommandDebug;
import dev.boooiil.historia.commands.CommandPlayers;
import dev.boooiil.historia.commands.CommandStats;
import dev.boooiil.historia.configuration.Config;
import dev.boooiil.historia.database.mysql.MySQLConnection;
import dev.boooiil.historia.database.mysql.MySQLHandler;
import dev.boooiil.historia.discord.HistoriaDiscord;
import dev.boooiil.historia.events.blockInteraction.PlayerBreakBlock;
import dev.boooiil.historia.events.connection.PlayerJoin;
import dev.boooiil.historia.events.connection.PlayerLeave;
import dev.boooiil.historia.events.crafting.PlayerCraft;
import dev.boooiil.historia.events.food.FoodLevelChange;
import dev.boooiil.historia.events.furnace.FurnaceSmeltFinish;
import dev.boooiil.historia.events.furnace.FurnaceSmeltStart;
import dev.boooiil.historia.events.pvp.PlayerDeath;
import dev.boooiil.historia.events.pvp.PlayerHit;
import dev.boooiil.historia.runnable.PlayerIterator;
import dev.boooiil.historia.util.ConfigUtil;
import dev.boooiil.historia.util.Logging;

/**
 * It's a plugin that loads, enables, and disables.
 */
public class HistoriaPlugin extends JavaPlugin {

    public HistoriaPlugin() {
        super();
    }

    /**
     * It's a constructor that is called when the plugin is loaded.
     * 
     * @param loader        - The plugin loader responsible for this plugin, can be null
     * @param description   - The plugin.yml file for this plugin
     * @param dataFolder    - The folder that contains the plugin's data
     * @param file          - The plugin's file
     */
    protected HistoriaPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    // It's a method that is called when the plugin is loaded.
    public void onLoad() {

        Logging.infoToConsole("Plugin has loaded.");

        HistoriaDiscord.init();

        // Check config files
        ConfigUtil.checkFiles();

    }

    @Override
    // It's a method that is called when the plugin is enabled.
    // Test
    public void onEnable() {

        // Save / Load the config in the Historia plugins folder.
        this.saveDefaultConfig();

        Config.init();

        registerEvent(new FoodLevelChange());
        registerEvent(new FurnaceSmeltFinish());
        registerEvent(new FurnaceSmeltStart());
        registerEvent(new PlayerBreakBlock());
        registerEvent(new PlayerCraft());
        registerEvent(new PlayerCraft());
        registerEvent(new PlayerDeath());
        registerEvent(new PlayerHit());
        registerEvent(new PlayerJoin());
        registerEvent(new PlayerLeave());

        registerCommand("checkplayers", new CommandPlayers());
        registerCommand("debug", new CommandDebug());
        registerCommand("stats", new CommandStats());
        registerCommand("set", new CommandSet());

        registerRunnable(new PlayerIterator(this));

        MySQLConnection.connect();
        MySQLHandler.createTable();

        Logging.infoToConsole("Plugin Enabled.");

    }

    @Override
    // It's a method that is called when the plugin is disabled.
    public void onDisable() {

        MySQLConnection.closeConnection();
        HistoriaDiscord.destroy();

        getLogger().info("Plugin disabled.");
    }

    /**
     * It returns the plugin instance
     * 
     * @return The plugin object.
     */
    public static Plugin plugin() {

        return Bukkit.getPluginManager().getPlugin("Historia");
        // return getPlugin(HistoriaPlugin.class);

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
}