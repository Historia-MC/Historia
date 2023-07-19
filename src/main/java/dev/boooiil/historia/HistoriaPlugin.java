package dev.boooiil.historia;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.scheduler.BukkitRunnable;

import dev.boooiil.historia.commands.CommandSet;
import dev.boooiil.historia.classes.items.generic.Ingot;
import dev.boooiil.historia.commands.CommandDebug;
import dev.boooiil.historia.commands.CommandGive;
import dev.boooiil.historia.commands.CommandPlayers;
import dev.boooiil.historia.commands.CommandProficiency;
import dev.boooiil.historia.commands.CommandStats;
import dev.boooiil.historia.configuration.ConfigurationLoader;
import dev.boooiil.historia.configuration.specific.IngotConfig;
import dev.boooiil.historia.database.mysql.MySQLConnection;
import dev.boooiil.historia.database.mysql.MySQLHandler;
import dev.boooiil.historia.discord.HistoriaDiscord;
import dev.boooiil.historia.events.blockInteraction.PlayerBreakBlock;
import dev.boooiil.historia.events.blockInteraction.WaterBreakBlock;
import dev.boooiil.historia.events.connection.PlayerJoin;
import dev.boooiil.historia.events.connection.PlayerLeave;
import dev.boooiil.historia.events.crafting.PlayerCraftingPrepare;
import dev.boooiil.historia.events.crafting.PlayerCraftingResult;
import dev.boooiil.historia.events.experience.ExperienceGain;
import dev.boooiil.historia.events.food.FoodLevelChange;
import dev.boooiil.historia.events.furnace.FurnaceSmeltFinish;
import dev.boooiil.historia.events.furnace.FurnaceSmeltStart;
import dev.boooiil.historia.events.inventory.PlayerClickInventory;
import dev.boooiil.historia.events.mobs.EntityBreed;
import dev.boooiil.historia.events.mobs.EntityKill;
import dev.boooiil.historia.events.mobs.EntityTame;
import dev.boooiil.historia.events.playerInteraction.PlayerRightClickBlock;
import dev.boooiil.historia.events.playerInteraction.PlayerRightClickEntity;
import dev.boooiil.historia.events.pvp.PlayerDeath;
import dev.boooiil.historia.events.pvp.PlayerHit;
import dev.boooiil.historia.events.pvp.PlayerRespawn;
import dev.boooiil.historia.events.pvp.ShootBow;
import dev.boooiil.historia.runnable.PlayerIterator;
import dev.boooiil.historia.runnable.SaveProgress;
import dev.boooiil.historia.runnable.UpdateScoreboard;
import dev.boooiil.historia.scoreboard.ScoreboardHandler;
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
     * @param loader      - The plugin loader responsible for this plugin, can be
     *                    null
     * @param description - The plugin.yml file for this plugin
     * @param dataFolder  - The folder that contains the plugin's data
     * @param file        - The plugin's file
     */
    protected HistoriaPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    // It's a method that is called when the plugin is loaded.
    public void onLoad() {

        Logging.infoToConsole("Plugin has loaded.");

        //deregisterRecipes();

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

        ConfigurationLoader.init();

        registerEvent(new EntityBreed());
        registerEvent(new EntityKill());
        registerEvent(new EntityTame());
        registerEvent(new ExperienceGain());
        registerEvent(new FoodLevelChange());
        registerEvent(new FurnaceSmeltFinish());
        registerEvent(new FurnaceSmeltStart());
        registerEvent(new PlayerClickInventory());
        registerEvent(new PlayerBreakBlock());
        registerEvent(new PlayerCraftingPrepare());
        registerEvent(new PlayerCraftingResult());
        registerEvent(new PlayerDeath());
        registerEvent(new PlayerHit());
        registerEvent(new PlayerJoin());
        registerEvent(new PlayerLeave());
        registerEvent(new PlayerRespawn());
        //registerEvent(new PlayerRightClickAir());
        registerEvent(new PlayerRightClickBlock());
        registerEvent(new PlayerRightClickEntity());
        registerEvent(new ShootBow());
        registerEvent(new WaterBreakBlock());

        registerCommand("checkplayers", new CommandPlayers());
        registerCommand("debug", new CommandDebug());
        registerCommand("stats", new CommandStats());
        registerCommand("set", new CommandSet());
        registerCommand("give", new CommandGive());
        registerCommand("proficiency", new CommandProficiency());

        registerRunnable(new PlayerIterator());
        registerRunnable(new UpdateScoreboard());
        registerRunnable(new SaveProgress(), 6000l);

        registerFurnaceRecipes();

        MySQLConnection.connect();
        MySQLHandler.createTable();

        ScoreboardHandler.init();

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

        Bukkit.removeRecipe(NamespacedKey.minecraft("iron_sword"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("gold_sword"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("stone_sword"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("wood_sword"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_sword"));

        Bukkit.removeRecipe(NamespacedKey.minecraft("iron_axe"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("gold_axe"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("stone_axe"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("wood_axe"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_axe"));

        Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_pickaxe"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_shovel"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_hoe"));

        Bukkit.removeRecipe(NamespacedKey.minecraft("iron_nugget"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("gold_nugget"));

        Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_helmet"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_chestplate"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_leggings"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("diamond_boots"));

        Bukkit.removeRecipe(NamespacedKey.minecraft("iron_helmet"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("iron_chestplate"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("iron_leggings"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("iron_boots"));

        Bukkit.removeRecipe(NamespacedKey.minecraft("gold_helmet"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("gold_chestplate"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("gold_leggings"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("gold_boots"));

        Bukkit.removeRecipe(NamespacedKey.minecraft("netherrite_helmet"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("netherrite_chestplate"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("netherrite_leggings"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("netherrite_boots"));

    }

    public void registerFurnaceRecipes() {

        IngotConfig ig = ConfigurationLoader.getIngotConfig();
        Ingot base_tin_ingot = ig.getObject("LOW_LIGHT_TIN_INGOT");


        FurnaceRecipe base_tin_safety_raw = new FurnaceRecipe(
            new NamespacedKey(this, "base_tin_safety_raw"),
            base_tin_ingot.getItemStack(), 
            Material.RAW_IRON,
            0f,
            base_tin_ingot.getSmeltTime()
        );

        FurnaceRecipe base_tin_safety_ingot = new FurnaceRecipe(
            new NamespacedKey(this, "base_tin_safety_ingot"),
            base_tin_ingot.getItemStack(), 
            Material.IRON_INGOT,
            0f,
            base_tin_ingot.getSmeltTime()
        );

        FurnaceRecipe base_bronze_safety_raw = new FurnaceRecipe(
            new NamespacedKey(this, "base_bronze_safety_raw"),
            base_tin_ingot.getItemStack(), 
            Material.RAW_GOLD,
            0f,
            base_tin_ingot.getSmeltTime()
        );

        FurnaceRecipe base_bronze_safety_ingot = new FurnaceRecipe(
            new NamespacedKey(this, "base_bronze_safety_ingot"),
            base_tin_ingot.getItemStack(), 
            Material.GOLD_INGOT,
            0f,
            base_tin_ingot.getSmeltTime()
        );

        Bukkit.addRecipe(base_tin_safety_raw);
        Bukkit.addRecipe(base_tin_safety_ingot);
        Bukkit.addRecipe(base_bronze_safety_raw);
        Bukkit.addRecipe(base_bronze_safety_ingot);

    }
}