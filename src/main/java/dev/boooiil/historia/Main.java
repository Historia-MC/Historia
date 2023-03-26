package dev.boooiil.historia;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import dev.boooiil.historia.commands.CommandDebug;
import dev.boooiil.historia.commands.CommandPlayers;
import dev.boooiil.historia.configuration.Config;
import dev.boooiil.historia.discord.HistoriaDiscord;
import dev.boooiil.historia.events.FurnaceSmeltFinish;
import dev.boooiil.historia.events.FurnaceSmeltStart;
import dev.boooiil.historia.events.PlayerBreakBlock;
import dev.boooiil.historia.events.PlayerJoin;
import dev.boooiil.historia.events.PlayerLeave;
import dev.boooiil.historia.mysql.MySQLHandler;
import dev.boooiil.historia.util.Logging;

/**
 * It's a plugin that loads, enables, and disables.
 */
public class Main extends JavaPlugin {

    @Override
    // It's a method that is called when the plugin is loaded.
    public void onLoad() {

        Logging.infoToConsole("Plugin has loaded.");

        HistoriaDiscord.init();

    }

    @Override
    // It's a method that is called when the plugin is enabled.
    // Test
    public void onEnable() {

        Logging.infoToConsole("Checking configs...");

        if (isMissingConfig()) {

            saveConfig(missingConfig());

            Logging.infoToConsole("Missing configuration files have been saved.");

        } else Logging.infoToConsole("All configuration files exist.");

        // Save / Load the config in the Historia plugins folder.
        this.saveDefaultConfig();

        Config.init();

        registerEvent(new PlayerJoin());
        registerEvent(new PlayerLeave());
        registerEvent(new PlayerBreakBlock());
        registerEvent(new FurnaceSmeltStart());
        registerEvent(new FurnaceSmeltFinish());

        registerCommand("checkplayers", new CommandPlayers());
        registerCommand("debug", new CommandDebug());

        Logging.infoToConsole("Loading MySQL...");
        MySQLHandler.createTable();
        Logging.infoToConsole("MySQL Loaded.");

        Logging.infoToConsole("Plugin Enabled.");

    }

    @Override
    // It's a method that is called when the plugin is disabled.
    public void onDisable() {

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
     * @param plugin The plugin you want to disable.
     */
    public static void disable(Plugin plugin) {

        plugin.getServer().getPluginManager().disablePlugin(plugin);

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
     * @param command The command to register
     */
    private void registerCommand(String commandName, CommandExecutor command) {

        this.getCommand(commandName).setExecutor(command);

    }

    /**
     * If the files array is not null, return the length of the array is not equal to 7. If the files
     * array is null, return true
     * 
     * @return The length of the files in the data folder.
     */
    private boolean isMissingConfig() {

        File[] files = this.getDataFolder().listFiles();

        if (files != null) return files.length != 7;
        else return true;

    }

    /**
     * It checks if the config files are missing, and if they are, it returns a list of the missing
     * config files
     * 
     * @return A list of strings.
     */
    private List<String> missingConfig() {

        List<String> check = new ArrayList<>();
        File[] files = this.getDataFolder().listFiles();

        check.add("armor.yml");
        check.add("classes.yml");
        check.add("expiry.yml");
        check.add("ingots.yml");
        check.add("ores.yml");
        check.add("weapons.yml");

        if (files == null) return check;

        for (File file : files) {

            if (check.contains(file.getName()))
                check.remove(file.getName());

        }

        return check;

    }

    /**
     * It takes a list of strings, and for each string, it reads the file from the jar, and saves it to
     * the plugin's data folder
     * 
     * @param missingConfig A list of the missing config files.
     * @return The method is returning a boolean value.
     */
    private boolean saveConfig(List<String> missingConfig) {

        try {

            for (String config : missingConfig) {

                ClassLoader classLoader = getClass().getClassLoader();
                InputStream is = classLoader.getResourceAsStream(config);

                byte[] buffer = new byte[is.available()];
                is.read(buffer);

                File internal = new File(getDataFolder(), config);
                OutputStream outputStream = new FileOutputStream(internal);
                outputStream.write(buffer);

                getLogger().info(config +" was saved successfully.");

                outputStream.close();

            }
        }

        catch (Exception e) {

            getLogger().severe(e.getMessage());
            getLogger().warning("Files could not be saved, " + missingConfig.toString());

            return false;
        }

        return true;
    }
}