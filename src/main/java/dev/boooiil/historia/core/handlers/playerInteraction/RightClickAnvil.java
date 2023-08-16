package dev.boooiil.historia.core.handlers.playerInteraction;

import dev.boooiil.historia.core.util.Logging;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

public class RightClickAnvil extends BaseInteractionEventBlock {

    public RightClickAnvil(PlayerInteractEvent event) {
        super(event);
    }

    @Override
    public void doInteraction() {

        if (this.blockIsType(Material.ANVIL)) {

            if (!this.getHistoriaPlayer().getProficiency().getSkills().hasChanceNoAnvilDamage()) {

                event.setCancelled(true);
                Logging.infoToPlayer("Maybe there is someone more skilled that can do this...", this.getPlayer().getUniqueId());

            }

        }

    }
    
}
