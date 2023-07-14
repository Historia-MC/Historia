package dev.boooiil.historia.runnable;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Expiry extends BukkitRunnable {

    private final JavaPlugin plugin;

    public Expiry(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }
    
}
