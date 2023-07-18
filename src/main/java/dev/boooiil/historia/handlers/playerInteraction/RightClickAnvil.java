package dev.boooiil.historia.handlers.playerInteraction;

import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

import dev.boooiil.historia.util.Logging;

public class RightClickAnvil extends BaseInteractionEvent {

    public RightClickAnvil(PlayerInteractEvent event) {
        super(event);
    }

    public void doInteraction() {

        if (this.getBlock().getType() == Material.ANVIL) {

            if (!this.getHistoriaPlayer().getProficiency().getSkills().hasChanceNoAnvilDamage()) {

                event.setCancelled(true);
                Logging.infoToPlayer("Maybe there is someone more skilled that can do this...", this.getPlayer().getUniqueId());

            }

        }

    }
    
}
