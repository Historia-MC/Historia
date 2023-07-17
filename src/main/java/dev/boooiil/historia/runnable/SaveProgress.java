package dev.boooiil.historia.runnable;

import org.bukkit.scheduler.BukkitRunnable;

import dev.boooiil.historia.database.internal.PlayerStorage;

public class SaveProgress extends BukkitRunnable {

    @Override
    public void run() {
        
        PlayerStorage.saveStates();

    }
    
}
