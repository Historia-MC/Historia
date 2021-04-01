package dev.boooiil.historia;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import dev.boooiil.historia.commands.Ignite;
import dev.boooiil.historia.commands.Item;
import dev.boooiil.historia.commands.Stats;
import dev.boooiil.historia.crafting.RecipeLoader;
import dev.boooiil.historia.commands.DebugItems;
import dev.boooiil.historia.mysql.MySQL;
import dev.boooiil.historia.runnable.ClassRunnable;

public class Main extends JavaPlugin {

    /**
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
        getLogger().info("Plugin has loaded.");
    }

    @Override
    public void onEnable() {

        // Disabled due to being beginner commands and not having a use.
        this.getCommand("Ignite").setExecutor(new Ignite());
        this.getCommand("Class").setExecutor(new dev.boooiil.historia.commands.Class());
        this.getCommand("Item").setExecutor(new Item());
        this.getCommand("DebugItems").setExecutor(new DebugItems());
        this.getCommand("Stats").setExecutor(new Stats());

        this.getServer().getPluginManager().registerEvents(new HistoriaEvents(), this);

        getLogger().info("Checking configs...");

        if (isMissingConfig()) {

            saveConfig(missingConfig());

            getLogger().info("Config files saved.");

        } else getLogger().info("All config files exist.");

        getLogger().info("Starting class task...");

        ClassRunnable classRunnable = new ClassRunnable(this);

        classRunnable.runTaskTimer(this, 0L, 20L);

        getLogger().info("Task queued.");

        // Save / Load the config in the Historia plugins folder.
        this.saveDefaultConfig();

        getLogger().info("Plugin enabled.");

        getLogger().info("Loading MySQL...");

        MySQL.createTable();

        RecipeLoader.disableRecipes();
        RecipeLoader.loadRecipes();

    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled.");
    }

    public static Plugin plugin() {

        return Bukkit.getPluginManager().getPlugin("Historia");

    }
    public static void disable(Plugin plugin) {

        plugin.getServer().getPluginManager().disablePlugin(plugin);

    }

    private boolean isMissingConfig() {

        return this.getDataFolder().listFiles().length != 7;

    }

    private List<String> missingConfig() {

        List<String> check = new ArrayList<>();

        check.add("armor.yml");
        check.add("classes.yml");
        check.add("expiry.yml");
        check.add("ingots.yml");
        check.add("ores.yml");
        check.add("weapons.yml");

        for (File file : this.getDataFolder().listFiles()) {

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