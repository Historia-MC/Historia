package dev.boooiil.historia.core.handlers.connection;

import dev.boooiil.historia.core.classes.user.HistoriaPlayer;
import dev.boooiil.historia.core.database.internal.PlayerStorage;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

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

        double previousHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue();
        AttributeInstance healthAttribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        
        if (healthAttribute.getBaseValue() != historiaPlayer.getProficiency().getStats().getBaseHealth()) {

            healthAttribute.setBaseValue(historiaPlayer.getProficiency().getStats().getBaseHealth());
            player.setHealth(historiaPlayer.getProficiency().getStats().getBaseHealth() * (player.getHealth() / previousHealth));

        }

        player.setWalkSpeed(0.2f * (float) historiaPlayer.getProficiency().getStats().getBaseSpeed());

        player.setLevel(historiaPlayer.getLevel());
        
        //just dont bother with this, update food status on consume event
        //player.setFoodLevel(historiaPlayer.getProficiency().getStats().getBaseFood() * (player.getFoodLevel()/20));


    }
    
}
