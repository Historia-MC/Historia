package dev.boooiil.historia;

import org.bukkit.plugin.java.JavaPlugin;

import dev.boooiil.historia.commands.Ignite;

public class Main extends JavaPlugin {

    @Override
    public void onLoad() {
        getLogger().info("Plugin has loaded.");
    }

    @Override
    public void onEnable() {
        //this.getCommand("Launch").setExecutor(new Launch());
        //this.getCommand("Help").setExecutor(new Help());
        //this.getCommand("Heal").setExecutor(new Heal());
        this.getCommand("Ignite").setExecutor(new Ignite());

        //Disabled due to complication.
        //this.getServer().getPluginManager().registerEvents(new InventoryClick(), this);

        this.getServer().getPluginManager().registerEvents(new PlayerBucketInteract(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerConsumeItem(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerEntityInteract(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
        //this.getServer().getPluginManager().registerEvents(new PlayerItemHeld(), this);
        this.getServer().getPluginManager().registerEvents(new ProjectileLaunch(), this);
        this.getServer().getPluginManager().registerEvents(new ProjectileHit(), this);
        this.saveConfig(); getLogger().info("Plugin enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled.");
    }

}