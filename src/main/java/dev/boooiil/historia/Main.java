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

import dev.boooiil.historia.commands.CommandPlayers;
import dev.boooiil.historia.configuration.IngotConfig;
import dev.boooiil.historia.configuration.OreConfig;
import dev.boooiil.historia.discord.HistoriaDiscord;
import dev.boooiil.historia.events.PlayerBreakBlock;
import dev.boooiil.historia.events.PlayerHit;
import dev.boooiil.historia.events.PlayerJoin;
import dev.boooiil.historia.events.PlayerKilled;
import dev.boooiil.historia.events.PlayerLeave;
import dev.boooiil.historia.mysql.MySQLHandler;
import dev.boooiil.historia.timers.SpawnKillTimer;
import dev.boooiil.historia.util.Logging;

public class Main extends JavaPlugin {

    /**
     * 
     * 
     * Server starts => Config loaded => passed to respective handler => other
     * classes can access the object
     * 
     * Config section (class, expiry, etc) passed to respective handler that parses
     * that infromation into an accessable object.
     * 
     * Player logs in and is applied stats based on the configuration stored.
     * 
     * Player respawns and is applied stats based on the configuration stored.
     * 
     */

    @Override
    public void onLoad() {

        Logging.infoToConsole("Plugin has loaded.");

        HistoriaDiscord.init();

    }

    @Override
    public void onEnable() {

        Logging.infoToConsole("Checking configs...");

        if (isMissingConfig()) {

            saveConfig(missingConfig());

            Logging.infoToConsole("Missing configuration files have been saved.");

        } else Logging.infoToConsole("All configuration files exist.");

        // Save / Load the config in the Historia plugins folder.
        this.saveDefaultConfig();
        
        OreConfig.init();
        IngotConfig.init();
        
        registerEvent(new PlayerKilled());
        registerEvent(new PlayerHit());
        registerEvent(new PlayerJoin());
        registerEvent(new PlayerLeave());
        registerEvent(new PlayerBreakBlock());

        registerCommand("CheckPlayers", new CommandPlayers());

        Logging.infoToConsole("Loading MySQL...");
        MySQLHandler.createTable();
        Logging.infoToConsole("MySQL Loaded.");

        SpawnKillTimer.timer();

        Logging.infoToConsole("Plugin Enabled.");

    }

    @Override
    public void onDisable() {

        HistoriaDiscord.destroy();

        getLogger().info("Plugin disabled.");
    }

    public static Plugin plugin() {

        return Bukkit.getPluginManager().getPlugin("Historia");

    }

    public static Server server() {

        return plugin().getServer();

    }

    public static void disable(Plugin plugin) {

        plugin.getServer().getPluginManager().disablePlugin(plugin);

    }

    private void registerEvent(Listener event) {

        this.getServer().getPluginManager().registerEvents(event, this);

    }

    private void registerCommand(String commandName, CommandExecutor command) {

        this.getCommand(commandName).setExecutor(command);

    }

    private boolean isMissingConfig() {

        File[] files = this.getDataFolder().listFiles();

        if (files != null) return files.length != 7;
        else return true;

    }

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