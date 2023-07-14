package dev.boooiil.historia.handlers.connection;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.database.internal.PlayerStorage;

public class InitialStatLoader {

    private HistoriaPlayer historiaPlayer;
    private Player player;

    public InitialStatLoader(Player player) {

        this.historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId(), true);
        this.player = player;

    }

    public void apply() {

        //TODO: Need to make sure that the player is not losing health or food each time they join if base health > 20
        //base health scale: getHealth() / getMaxHealth() * getHealthScale().

        AttributeInstance healthAttribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        
        if (healthAttribute.getBaseValue() != historiaPlayer.getProficiency().getStats().getBaseHealth()) {

            healthAttribute.setBaseValue(historiaPlayer.getProficiency().getStats().getBaseHealth());
            player.setHealth(historiaPlayer.getProficiency().getStats().getBaseHealth() * (player.getHealth()/20));

        }

        player.setWalkSpeed(0.2f * historiaPlayer.getProficiency().getStats().getBaseSpeed());

        player.setLevel(historiaPlayer.getLevel());
        
        //just dont bother with this, update food status on consume event
        //player.setFoodLevel(historiaPlayer.getProficiency().getStats().getBaseFood() * (player.getFoodLevel()/20));


    }
    
}
