package dev.boooiil.historia.classes;

import org.bukkit.entity.Player;

import dev.boooiil.historia.mysql.UserData;

public class ClassManager {
    
    public void initiate(Player player) {

        //do check for player stats
        UserData user = new UserData(player);

        player.setHealthScale(user.getHealth());
        player.setWalkSpeed(user.getSpeed());
            
    }

}