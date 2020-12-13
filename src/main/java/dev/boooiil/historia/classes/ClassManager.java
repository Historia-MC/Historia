package dev.boooiil.historia.classes;

import org.bukkit.entity.Player;

import dev.boooiil.historia.mysql.UserData;

public class ClassManager {
    
    public void initiate(Player player) {

        //do check for player stats
        UserData user = new UserData(player);

        if (!user.getClassName().equals("None")) {

            player.setHealthScale(user.getHealth());
            player.setWalkSpeed(user.getSpeed());
            
        } else {

            player.setHealthScale(11.0);
            player.setWalkSpeed((float) 0.1);

        }
    }

}