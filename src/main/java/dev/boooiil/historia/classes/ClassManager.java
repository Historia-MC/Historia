package dev.boooiil.historia.classes;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ClassManager {
    
    public void initiate(Player player) {
        //do check for player stats

        //example stats
        player.setExhaustion(30);
        //player.setFoodLevel(400);
        player.setHealthScale(10.5);
        player.setWalkSpeed((float) 0.2);
        //player.getScoreboard().registerNewTeam("test").setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
        player.getScoreboard().getTeam("test").addEntry(player.getDisplayName());
        player.setFoodLevel(3);

        for (Player online : Bukkit.getOnlinePlayers()) {
            player.showPlayer(Bukkit.getPluginManager().getPlugin("Historia"), online);
        }
    }

}