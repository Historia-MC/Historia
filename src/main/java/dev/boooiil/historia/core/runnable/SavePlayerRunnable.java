package dev.boooiil.historia.core.runnable;

import dev.boooiil.historia.core.database.internal.PlayerStorage;
import org.bukkit.scheduler.BukkitRunnable;

public class SavePlayerRunnable extends BukkitRunnable {

    @Override
    public void run() {
        
        PlayerStorage.saveStates();

    }
    
}
